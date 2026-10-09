package com.gowhich.kun.ui.page

import android.annotation.SuppressLint
import androidx.annotation.OptIn
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.KeyboardDoubleArrowLeft
import androidx.compose.material.icons.filled.KeyboardDoubleArrowRight
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.PauseCircle
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.RepeatOne
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.gowhich.kun.player.PlayerController
import com.gowhich.kun.ui.theme.DarkColorScheme
import com.gowhich.kun.ui.theme.LightColorScheme
import kotlin.math.abs
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/** 封面暂未提供真实图片：按歌名确定性选一组霓虹渐变色，构建占位封面 */
private val CoverPalettes = listOf(
    listOf(Color(0xFFFF2A54), Color(0xFF00F5D4)),
    listOf(Color(0xFF7C4DFF), Color(0xFF00E5FF)),
    listOf(Color(0xFFFF6F00), Color(0xFFFF4081)),
    listOf(Color(0xFF00C853), Color(0xFFFFD740)),
    listOf(Color(0xFF651FFF), Color(0xFF26C6DA)),
    listOf(Color(0xFFAB47BC), Color(0xFFFFD54F))
)

private fun coverColors(seed: String?): List<Color> {
    val index = abs((seed ?: "kun").hashCode()) % CoverPalettes.size
    return CoverPalettes[index]
}

// 工具方法：格式化时间
@SuppressLint("DefaultLocale")
fun formatTime(millis: Long): String {
    val seconds = (millis / 1000) % 60
    val minutes = (millis / 1000 / 60) % 60
    return String.format("%02d:%02d", minutes, seconds)
}


@OptIn(UnstableApi::class)
@Composable
fun MusicScreen(navController: NavController) {
    val context = LocalContext.current

    val currentSong = PlayerController.currentSong

    // 确保播放器已初始化
    LaunchedEffect(Unit) {
        PlayerController.ensurePlayer(context)
    }

    // 每秒更新一次进度
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            PlayerController.exoPlayer?.let { player ->
                PlayerController.updateProgress(player.currentPosition, player.duration)
            }
        }
    }

    val darkTheme = isSystemInDarkTheme()
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = MaterialTheme.typography
    ) {
        Box() {
            // 背景：封面（暂用颜色构建的占位渐变封面）
            MusicBackground(
                title = currentSong?.title,
                isPlaying = PlayerController.isPlaying
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column {
                    if (currentSong != null) {
                        Text(
                            text = currentSong!!.title,
                            color = colorScheme.onBackground,
                            fontSize = 22.sp,
                            fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 120.dp)
                        )
                        Text(
                            text = currentSong!!.artist.ifBlank { "Unknown Artist" },
                            color = colorScheme.onSurfaceVariant,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                Column {
                    // 进度条slider
                    MusicSlider(
                        currentPosition = PlayerController.currentPosition,
                        totalDuration = PlayerController.duration,
                        onPositionChange = {
                            PlayerController.seekTo(it)
                        },
                        enabled = PlayerController.exoPlayer?.playbackState == Player.STATE_READY
                    )

                    // 操作按钮
                    MusicPlayAction(
                        isPlaying = PlayerController.isPlaying,
                        repeatMode = PlayerController.repeatMode,
                        onRepeatClick = { PlayerController.cycleRepeatMode() },
                        onPlayOrPauseClick = {
                            PlayerController.togglePlayPause()
                        },
                        onPreviewClick = {
                            PlayerController.playPrevious()
                        },
                        onNextClick = {
                            PlayerController.playNext()
                        }
                    )
                }
            }

            MusicNavigator(navController)
        }
    }
}


@Composable
fun MusicNavigator(
    navController: NavController
) {
    // 从主题获取配色（自动适配浅/深色模式）
    val colorScheme = MaterialTheme.colorScheme

    // 统一按钮样式配置（与播放控件视觉风格一致）
    val navButtonColors = ButtonDefaults.buttonColors(
        containerColor = Color.Transparent, // 保留透明背景
        contentColor = colorScheme.primary, // 图标用主题主色（绿系）
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(top = 32.dp)
            .background(color = Color.Transparent)
            .padding(start = 10.dp, end = 10.dp),

        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier
                .width(48.dp)
                .height(48.dp)
        ) {
            Button(
                modifier = Modifier
                    .width(48.dp)
                    .height(48.dp),
                contentPadding = PaddingValues(10.dp),
                colors = navButtonColors,
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Image(
                    painter = rememberVectorPainter(Icons.Default.ArrowBackIosNew),
                    contentDescription = "Back",
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(colorScheme.primary),
                    modifier = Modifier
                        .width(48.dp)
                        .height(48.dp),
                )
            }

        }

        Row(
            modifier = Modifier
                .wrapContentWidth()
                .height(48.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Now Playing",
                color = colorScheme.onSurface, // 文字色适配背景（浅/深色自动切换）
                style = MaterialTheme.typography.titleMedium, // 用主题字体样式，提升视觉层级
            )
        }

        Row(
            modifier = Modifier
                .width(48.dp)
                .height(48.dp)
        ) {
            Button(
                modifier = Modifier
                    .width(48.dp)
                    .height(48.dp),
                contentPadding = PaddingValues(10.dp),
                colors = navButtonColors,
                onClick = {

                }
            ) {
                Image(
                    painter = rememberVectorPainter(Icons.Default.MoreHoriz),
                    contentDescription = "More",
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(colorScheme.primary),
                    modifier = Modifier
                        .width(48.dp)
                        .height(48.dp),
                )
            }
        }
    }
}

@Composable
fun MusicPlayAction(
    isPlaying: Boolean,
    repeatMode: Int = Player.REPEAT_MODE_OFF,
    onRepeatClick: () -> Unit = {},
    onPlayOrPauseClick: () -> Unit = {},
    onPreviewClick: () -> Unit = {},
    onNextClick: () -> Unit = {}
) {

    val colorScheme = MaterialTheme.colorScheme


    val playButtonModifier = Modifier.size(83.dp)
    val buttonColors = ButtonDefaults.buttonColors(
        containerColor = Color.Transparent, // 透明背景，继承父布局的surfaceVariant
        contentColor = colorScheme.primary, // 图标用主题主色
        disabledContentColor = colorScheme.onSurface.copy(alpha = 0.5f), // 禁用态半透明
    )

    // 通用按钮配置（提取复用，减少冗余）
    val buttonModifier = Modifier.size(48.dp)

    Row(
        modifier = Modifier
            .height(83.dp)
            .fillMaxWidth()
            .background(color = colorScheme.surfaceVariant)
            .padding(start = 10.dp, end = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 左侧：循环模式切换（关闭 / 列表循环 / 单曲循环）
        MusicRepeatButton(
            repeatMode = repeatMode,
            onToggle = onRepeatClick
        )

        // 中间：上一首 / 播放暂停 / 下一首
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                modifier = buttonModifier,
                contentPadding = PaddingValues(10.dp),
                colors = buttonColors,
                onClick = {
                    onPreviewClick()
                }
            ) {
                Image(
                    rememberVectorPainter(Icons.Default.KeyboardDoubleArrowLeft),
                    contentDescription = "Previous",
                    modifier = Modifier
                        .width(48.dp)
                        .height(48.dp),
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(colorScheme.primary)
                )
            }

            Button(
                modifier = playButtonModifier,
                contentPadding = PaddingValues(10.dp),
                colors = buttonColors,
                onClick = {
                    // 暂停 或者 开始
                    onPlayOrPauseClick()
                }
            ) {
                Image(
                    rememberVectorPainter(if (isPlaying) Icons.Default.PauseCircle else Icons.Default.PlayCircle),
                    contentDescription = "Play or Pause",
                    modifier = Modifier
                        .width(83.dp)
                        .height(83.dp),
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(colorScheme.primary)
                )
            }

            Button(
                modifier = buttonModifier,
                contentPadding = PaddingValues(10.dp),
                colors = buttonColors,
                onClick = {
                    onNextClick()
                }
            ) {
                Image(
                    rememberVectorPainter(Icons.Default.KeyboardDoubleArrowRight),
                    contentDescription = "Next",
                    modifier = Modifier
                        .width(48.dp)
                        .height(48.dp),
                    contentScale = ContentScale.Fit,
                    colorFilter = ColorFilter.tint(colorScheme.primary)
                )
            }
        }

        // 右侧占位，保持中间按钮组居中
        Spacer(modifier = Modifier.width(48.dp))
    }
}

/** 循环模式切换按钮：OFF 置灰，列表循环用 Repeat 图标，单曲循环用 RepeatOne 图标 */
@Composable
fun MusicRepeatButton(
    repeatMode: Int,
    onToggle: () -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme
    val isOne = repeatMode == Player.REPEAT_MODE_ONE
    val isActive = repeatMode != Player.REPEAT_MODE_OFF
    val icon = if (isOne) Icons.Filled.RepeatOne else Icons.Filled.Repeat
    val tint = if (isActive) colorScheme.primary else colorScheme.onSurface.copy(alpha = 0.4f)

    Button(
        modifier = Modifier.size(48.dp),
        contentPadding = PaddingValues(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = tint,
        ),
        onClick = onToggle
    ) {
        Image(
            painter = rememberVectorPainter(icon),
            contentDescription = when {
                isOne -> "单曲循环"
                isActive -> "列表循环"
                else -> "关闭循环"
            },
            modifier = Modifier
                .width(48.dp)
                .height(48.dp),
            contentScale = ContentScale.Fit,
            colorFilter = ColorFilter.tint(tint)
        )
    }
}

@Composable
fun MusicSlider(
    modifier: Modifier = Modifier,
    currentPosition: Long = 0L,
    totalDuration: Long = 0L,
    onPositionChange: (Long) -> Unit = {},
    enabled: Boolean = true
) {

    // 计算滑块当前值（转换为Float，适配Slider）
    // 处理总时长为0的情况（避免除以0/滑块范围错误）
    val sliderMaxValue = if (totalDuration <= 0) 1f else totalDuration.toFloat()

    // 拖动中的本地状态：拖动时拇指跟前指走，不被每秒刷新一次的进度拉回去；松手后再回源
    var isDragging by remember { mutableStateOf(false) }
    // 注意：不能用 remember(currentPosition)，否则每秒刷新会重建该状态导致拖动被打断
    var dragPosition by remember { mutableStateOf(currentPosition) }
    // 松手后"待播放器确认"的目标位置：期间保持显示目标，避免先跳回起点再跳到目标
    var pendingSeek by remember { mutableStateOf<Long?>(null) }

    // 播放器进度追平目标后，结束待确认状态
    LaunchedEffect(currentPosition, pendingSeek) {
        val target = pendingSeek
        if (target != null && kotlin.math.abs(currentPosition - target) < 500L) {
            pendingSeek = null
        }
    }
    // 兜底：最多保持 1.5s，避免 seek 失败导致拇指一直停在目标位
    LaunchedEffect(pendingSeek) {
        if (pendingSeek != null) {
            delay(1500)
            if (pendingSeek != null) pendingSeek = null
        }
    }

    // 拖动时显示本地值，释放后到播放器追平前显示目标值，之后跟随实时进度
    val effectivePosition = when {
        isDragging -> dragPosition
        pendingSeek != null -> pendingSeek!!
        else -> currentPosition
    }
    val sliderPosition = if (totalDuration <= 0) 0f else effectivePosition.toFloat()

    val colorScheme = MaterialTheme.colorScheme

    // Slider配色配置（适配主题）
    val sliderColors = SliderDefaults.colors(
        // 激活轨道颜色（主题主色）
        activeTrackColor = colorScheme.primary,
        // 未激活轨道颜色（主色半透明）
        inactiveTrackColor = colorScheme.primary.copy(alpha = 0.3f),
        // 滑块颜色（主题主色）
        thumbColor = colorScheme.primary,
        // 禁用状态配色（主题onSurfaceVariant半透明）
        disabledActiveTrackColor = colorScheme.onSurfaceVariant.copy(alpha = 0.3f),
        disabledInactiveTrackColor = colorScheme.onSurfaceVariant.copy(alpha = 0.1f),
        disabledThumbColor = colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp)
            .background(color = colorScheme.surfaceVariant),
    ) {

        Slider(
            value = sliderPosition,
            onValueChange = { newPosition ->
                // 拖动中只更新本地拇指位置，不实时 seek（避免频繁 seekTo 卡顿掉帧）
                isDragging = true
                dragPosition = newPosition.toLong()
            },
            onValueChangeFinished = {
                // 松手：标记待确认目标并一次性 seek；播放器进度追平后回源（避免先回起点再跳目标）
                val target = dragPosition
                pendingSeek = target
                isDragging = false
                onPositionChange(target)
            },
            valueRange = 0f..sliderMaxValue,
            enabled = enabled,
            modifier = Modifier.padding(start = 50.dp, end = 50.dp),
            colors = sliderColors,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.width(50.dp),
                text = formatTime(effectivePosition),
                color = colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                fontSize = 12.sp
            )

            Text(
                modifier = Modifier.width(50.dp),
                text = formatTime(totalDuration),
                color = colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                fontSize = 12.sp
            )
        }
    }

}

@Composable
fun MusicBackground(
    title: String?,
    isPlaying: Boolean,
    modifier: Modifier = Modifier
) {
    // 从主题获取配色（自动适配浅/深色模式）
    val colorScheme = MaterialTheme.colorScheme

    val shape = CircleShape

    val rotation = remember { Animatable(0f) }

    val animatedWidth = remember { Animatable(0.dp, Dp.VectorConverter) }

    // 监听开关状态
    LaunchedEffect(isPlaying) {
        // 边框宽度：从无到有、再从有到无的显式双向循环（0dp -> 22dp -> 0dp）
        launch {
            while (true) {
                animatedWidth.animateTo(22.dp, tween(2000, easing = FastOutSlowInEasing))
                animatedWidth.animateTo(0.dp, tween(2000, easing = FastOutSlowInEasing))
            }
        }

        if (isPlaying) {
            // 无限循环旋转
            rotation.animateTo(
                targetValue = rotation.value + 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(20000, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                )
            )
        } else {
            // 停止动画
            rotation.stop()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorScheme.surface),
            contentAlignment = Alignment.TopCenter, // 子组件（图片）左右居中、垂直偏上
    ) {
        Column (
            // 高度需 ≥ 封面 top padding + 边长，否则固定高度会把圆压扁成椭圆
            modifier = Modifier.fillMaxWidth().height(560.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val gradientColors = remember(title) { coverColors(title) }

            Box(
                modifier = modifier
                    .padding(top = 200.dp) // 与上方文字留更大间隔
                    .size(327.dp) // 颜色封面与旧图片同尺寸
                    .clip(shape) // 背景色也裁剪为对应圆角/圆形
                    .background(colorScheme.surfaceVariant) // 默认底色
                    .drawWithContent {
                        // 先画内容（渐变封面），再叠加渐变描边环（随转动扫色）
                        drawContent()
                        val strokeWidth = animatedWidth.value.toPx()
                        if (strokeWidth > 0f) {
                            rotate(degrees = rotation.value, pivot = center) {
                                drawCircle(
                                    brush = Brush.sweepGradient(
                                        listOf(
                                            colorScheme.primary,
                                            colorScheme.tertiary,
                                            colorScheme.secondaryContainer,
                                            colorScheme.primary
                                        )
                                    ),
                                    radius = size.minDimension / 2f - strokeWidth / 2f,
                                    center = center,
                                    style = Stroke(width = strokeWidth)
                                )
                            }
                        }
                    }
            ) {
                // 占位封面：按歌名选定的渐变，随播放旋转（同旧图片的旋转动画）
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .rotate(rotation.value)
                        .background(brush = Brush.linearGradient(gradientColors))
                )
            }

        }

    }
}


// ========== Preview（覆盖不同场景+明暗主题） ==========
/** 浅色主题 */
@Preview(
    name = "MusicBackground - 浅色主题（占位渐变封面）",
    showBackground = true,
    showSystemUi = true // 显示系统状态栏，更贴近实际效果
)
@Composable
fun MusicBackground_Light_Success_Preview() {
    CustomMusicTheme(darkTheme = false) {
        MusicBackground(
            title = "赛博迷幻夜未央",
            isPlaying = true
        )
    }
}

/** 深色主题 */
@Preview(
    name = "MusicBackground - 深色主题（占位渐变封面）",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun MusicBackground_Dark_Error_Preview() {
    CustomMusicTheme(darkTheme = true) {
        MusicBackground(
            title = "午夜霓虹",
            isPlaying = true
        )
    }
}

/** 通用预览 - 自定义尺寸（验证偏上对齐） */
@Preview(
    name = "MusicBackground - 自定义尺寸（偏上对齐）",
    showBackground = true,
    widthDp = 360,
    heightDp = 640,
    showSystemUi = false
)
@Composable
fun MusicBackground_CustomSize_Preview() {
    CustomMusicTheme(darkTheme = false) {
        MusicBackground(
            title = "电流涌动",
            isPlaying = true
        )
    }
}


// ========== Preview（验证明暗主题效果） ==========
@Preview(
    name = "MusicNavigator - 浅色主题",
    showBackground = true,
    backgroundColor = 0xFFFBFDF9, // 匹配light_background
    showSystemUi = false
)
@Composable
fun MusicNavigator_Light_Preview() {
    val navController = rememberNavController()

    CustomMusicTheme(darkTheme = false) {
        MusicNavigator(navController = navController)
    }
}

@Preview(
    name = "MusicNavigator - 深色主题",
    showBackground = true,
    backgroundColor = 0xFF191C1A, // 匹配dark_background
    showSystemUi = false
)
@Composable
fun MusicNavigator_Dark_Preview() {
    val navController = rememberNavController()

    CustomMusicTheme(darkTheme = true) {
        MusicNavigator(navController = navController)
    }
}

@Preview(
    name = "MusicPlayAction - 浅色主题",
    showBackground = true,
    backgroundColor = 0xFF888888,
    heightDp = 83  // 匹配组件高度
)
@Composable
fun MusicPlayActionLightPreview() {
    CustomMusicTheme (
        darkTheme = false
    ) {
        MusicPlayAction(
            isPlaying = false,
            onPreviewClick = {},
            onNextClick = {},
            onPlayOrPauseClick = {}
        )
    }
}

@Preview(
    name = "MusicPlayAction - 深色主题",
    showBackground = true,
    backgroundColor = 0xFF888888,
    heightDp = 83  // 匹配组件高度
)
@Composable
fun MusicPlayActionDarkPreview() {
    CustomMusicTheme (
        darkTheme = true
    ) {
        MusicPlayAction(
            isPlaying = false,
            onPreviewClick = {},
            onNextClick = {},
            onPlayOrPauseClick = {}
        )
    }
}

@Preview(
    name = "MusicSlider - 浅色主题",
    showBackground = true,
    backgroundColor = 0xFFDBE5DD, // 匹配light_surfaceVariant
    showSystemUi = false
)
@Composable
fun MusicSliderLightPreview() {
    CustomMusicTheme (
        darkTheme = false
    ) {
        MusicSlider(
            currentPosition = 10000, // 10秒
            totalDuration = 60000,   // 60秒
            enabled = true
        )
    }
}

@Preview(
    name = "MusicSlider - 深色主题",
    showBackground = true,
    backgroundColor = 0xFFDBE5DD, // 匹配light_surfaceVariant
    showSystemUi = false
)
@Composable
fun MusicSliderDarkPreview() {
    CustomMusicTheme (
        darkTheme = true
    ) {
        MusicSlider(
            currentPosition = 10000, // 10秒
            totalDuration = 60000,   // 60秒
            enabled = true
        )
    }
}


//@Preview
//@Composable
//fun MusicProgressPreview() {
//    MusicProgress()
//}

@Composable
fun CustomMusicTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = MaterialTheme.typography,
        content = content
    )
}