package com.gowhich.kun.ui.page

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.net.Uri
import android.util.Log
import androidx.annotation.OptIn
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.Size
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.RawResourceDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.gowhich.kun.R
import com.gowhich.kun.ui.theme.DarkColorScheme
import com.gowhich.kun.ui.theme.LightColorScheme
import kotlinx.coroutines.delay

private const val TAG: String = "MusicScreen"

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
    val mediaUrl = "https://storage.googleapis.com/exoplayer-test-media-0/play.mp3"
    val context = LocalContext.current

//    val rawUri = RawResourceDataSource.buildRawResourceUri(R.raw.test2)

    val rawUri = Uri.Builder()
        .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE) // 指定安卓资源scheme
        .path(R.raw.test2.toString()) // 资源ID转为字符串作为path
        .build()

    // 创建并缓存ExoPlayer实例（remember避免重组重建）
    val exoPlayer = remember(context) {
        ExoPlayer.Builder(context)
            .build().apply {
                // 设置播放源
                val mediaItem = MediaItem.fromUri(mediaUrl)
                setMediaItem(mediaItem)

                // 准备播放器（预加载）
                prepare()

                // 是否静音 0-1
                volume = 1f

                // 是否循环播放
                repeatMode = Player.REPEAT_MODE_ALL
            }
    }

    // 管理播放器生命周期（页面销毁时释放资源）
    DisposableEffect(Unit) {
        onDispose {
            exoPlayer.release()
        }
    }

    // 监听播放状态
    val isPlaying = remember { mutableStateOf(false) }
    exoPlayer.addListener(object : Player.Listener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            super.onPlaybackStateChanged(playbackState)

            isPlaying.value = playbackState == Player.STATE_READY && exoPlayer.isPlaying

            Log.d(TAG, "onPlaybackStateChanged: ${playbackState} ${exoPlayer.isPlaying}")
        }

        override fun onIsPlayingChanged(newIsPlaying: Boolean) {
            super.onIsPlayingChanged(newIsPlaying)
            Log.d(TAG, "onIsPlayingChanged: $isPlaying")
            isPlaying.value = newIsPlaying
        }
    })

    // 监听播放进度(每秒更新一次)
    val currentPosition = remember { mutableStateOf(0L) }
    val totalPosition = remember { mutableStateOf(0L) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(exoPlayer) {
        totalPosition.value = exoPlayer.duration

        // 循环更新进度
        while (true) {
            delay(1000) // 延迟1秒更新
            if (exoPlayer.isPlaying) {
                totalPosition.value = exoPlayer.duration
                currentPosition.value = exoPlayer.currentPosition
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
            // 背景图
            MusicBackground(
                imageUrl = "https://st-gdx.dancf.com/gaodingx/0/uxms/design/20200611-190838-9d6f.png"
            )

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column {  }

                Column {
                    // 进度条slider
                    MusicSlider(
                        currentPosition = currentPosition.value,
                        totalDuration = totalPosition.value,
                        onPositionChange = {
                            exoPlayer.seekTo(it)
                        },
                        enabled = exoPlayer.playbackState == Player.STATE_READY
                    )

                    // 操作按钮
                    // 上一曲 播放/暂停 下一曲
                    MusicPlayAction(
                        isPlaying = isPlaying.value,
                        onPlayOrPauseClick = {
                            if (isPlaying.value) exoPlayer.pause() else exoPlayer.play()
                        },
                        onPreviewClick = {

                        },
                        onNextClick = {

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
                    contentDescription = "返回",
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
                text = "详情",
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
                    contentDescription = "更多",
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
                contentDescription = "上一曲",
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
                contentDescription = "播放或暂停",
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
                contentDescription = "下一曲",
                modifier = Modifier
                    .width(48.dp)
                    .height(48.dp),
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(colorScheme.primary)
            )
        }
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
    val sliderPosition = if (totalDuration <= 0) 0f else currentPosition.toFloat()

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
                onPositionChange(newPosition.toLong())
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
                text = formatTime(currentPosition),
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
    imageUrl: String?,
    modifier: Modifier = Modifier
) {

    var rotateAngle by remember { mutableStateOf(0f) }

    // 从主题获取配色（自动适配浅/深色模式）
    val colorScheme = MaterialTheme.colorScheme

    rotateAngle = animateFloatAsState(
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween (
                durationMillis = 20000,
                easing = LinearEasing,
            ),
            repeatMode = RepeatMode.Restart
        ),
        finishedListener = { angle ->
            Log.d(TAG, "旋转动画结束，当前角度：$angle") // 调试日志，确认动画执行
        },
        label = "album rotation"
    ).value


//    val shape = if (cornerRadius == CircleShape.cornerSize) {
//        CircleShape
//    } else {
//        RoundedCornerShape(cornerRadius.dp)
//    }

    val shape = CircleShape

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorScheme.surface),
            contentAlignment = Alignment.TopCenter, // 子组件（图片）左右居中、垂直偏上
    ) {
        Column (
            modifier = Modifier.fillMaxWidth().height(493.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = modifier
                    .padding(top = 166.dp)
                    .size(327.dp) // 和图片同尺寸
                    .clip(shape) // 背景色也裁剪为对应圆角/圆形
                    .background(colorScheme.surfaceVariant) // 默认背景色（加载中/失败显示）
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "专辑图片",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .border(
                            width = 2.dp,
                            color = colorScheme.primary,
                            shape = shape
                        )
                        .rotate(rotateAngle),
                    onLoading = {
                        Log.d(TAG, "图片加载中：$imageUrl")
                    },
                    onError = { error ->
                        Log.e(TAG, "图片加载失败：$imageUrl，错误信息：${error.result.throwable.message}")
                    },
                    onSuccess = { state ->
                        Log.d(TAG, "图片加载成功：$imageUrl，尺寸：${state.result}")
                    }
                )
            }

        }

    }
}


// ========== Preview（覆盖不同场景+明暗主题） ==========
/** 浅色主题 - 图片加载成功场景 */
@Preview(
    name = "MusicBackground - 浅色主题（加载成功）",
    showBackground = true,
    showSystemUi = true // 显示系统状态栏，更贴近实际效果
)
@Composable
fun MusicBackground_Light_Success_Preview() {
    CustomMusicTheme(darkTheme = false) {
        MusicBackground(
            imageUrl = "https://st-gdx.dancf.com/gaodingx/0/uxms/design/20200611-190838-9d6f.png"
        )
    }
}

/** 深色主题 - 图片加载失败/空URL场景 */
@Preview(
    name = "MusicBackground - 深色主题（加载失败）",
    showBackground = true,
    showSystemUi = true
)
@Composable
fun MusicBackground_Dark_Error_Preview() {
    CustomMusicTheme(darkTheme = true) {
        MusicBackground(
            imageUrl = "https://st-gdx.dancf.com/gaodingx/0/uxms/design/20200611-190838-9d6f.png"
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
            imageUrl = "https://st-gdx.dancf.com/gaodingx/0/uxms/design/20200611-190838-9d6f.png"
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

@Composable
private fun previewExoPlayer(): ExoPlayer {
    val context = LocalContext.current
    return remember {
        ExoPlayer.Builder(context)
            .build() // 预览中仅创建实例，不加载音频，不影响预览效果
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