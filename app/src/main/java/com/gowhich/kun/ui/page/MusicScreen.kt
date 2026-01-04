package com.gowhich.kun.ui.page

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
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
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.gowhich.kun.R

private const val TAG: String = "MusicScreen"

@Composable
fun MusicScreen(navController: NavController) {
    Box() {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 背景图
            MusicBackground(
                imageUrl = "https://st-gdx.dancf.com/gaodingx/0/uxms/design/20200611-190838-9d6f.png"
            )

            // 进度条
            MusicProgress()

            // 进度条slider
            MusicSlider()

            // 操作按钮
//            上一曲 播放/暂停 下一曲
            MusicPlayAction()
        }

        MusicNavigator(navController)

    }
}


@Composable
fun MusicNavigator(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .background(color = Color.Gray)
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
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                onClick = {
                    navController.popBackStack()
                }
            ) {
                Image(
                    painter = painterResource(R.drawable.icons_back),
                    contentDescription = "返回",
                    contentScale = ContentScale.Fit
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
                text = "详情"
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
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                onClick = {

                }
            ) {
                Image(
                    painter = painterResource(R.drawable.icons_more),
                    contentDescription = "更多",
                    contentScale = ContentScale.Fit
                )
            }
        }
    }
}

@Composable
fun MusicPlayAction() {
    Row(
        modifier = Modifier
            .height(48.dp)
            .fillMaxWidth()
            .background(color = Color.Gray)
            .padding(start = 10.dp, end = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween

    ) {
        Button(
            modifier = Modifier
                .width(48.dp)
                .height(48.dp),
            contentPadding = PaddingValues(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            onClick = {
                // 上一首歌
            }
        ) {
            Image(
                painterResource(R.drawable.icons_forward_50),
                contentDescription = "上一曲",
                contentScale = ContentScale.Fit,
                modifier = Modifier.rotate(180f)
            )
        }

        Button(
            modifier = Modifier
                .width(48.dp)
                .height(48.dp),
            contentPadding = PaddingValues(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            onClick = {
                // 暂停 或者 开始
            }
        ) {
            Image(
                painterResource(R.drawable.icons_play_48),
                contentDescription = "播放或暂停",
                contentScale = ContentScale.Fit
            )
        }

        Button(
            modifier = Modifier
                .width(48.dp)
                .height(48.dp),
            contentPadding = PaddingValues(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
            onClick = {
                // 下一首歌
            }
        ) {
            Image(
                painterResource(R.drawable.icons_forward_50),
                contentDescription = "下一曲",
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Composable
fun MusicSlider(
    modifier: Modifier = Modifier
) {

    var sliderPosition by remember { mutableFloatStateOf(0f) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp)
            .background(Color.LightGray),
    ) {

        Slider(
            value = sliderPosition,
            onValueChange = {
                sliderPosition = it
            },
            modifier = Modifier.padding(start = 50.dp, end = 50.dp)
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
                text = "11:00",
                color = Color.White,
                textAlign = TextAlign.Center,
                fontSize = 12.sp
            )

            Text(
                modifier = Modifier.width(50.dp),
                text = "12:00",
                color = Color.White,
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
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .background(Color.LightGray)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = "专辑图片",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
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


@Composable
fun MusicProgress() {
    val progress = remember { mutableFloatStateOf(0.5f) }

    Row(
        modifier = Modifier
            .background(Color.Blue)
            .height(45.dp)
            .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            "11:00",
            style = TextStyle(
                fontSize = 15.sp,
                color = Color.White
            ), modifier = Modifier
                .padding(start = 15.dp, end = 15.dp)
                .wrapContentWidth()
        )

        Column(
            modifier = Modifier
                .height(10.dp)
                .wrapContentWidth(),
        ) {
            LinearProgressIndicator(
                progress = { progress.floatValue },
                modifier = Modifier
                    .height(10.dp)
                    .width(200.dp),
                color = Color.Red,
                trackColor = Color.Gray,
                strokeCap = StrokeCap.Round,
            )
        }

        Text(
            "12:00", style = TextStyle(
                fontSize = 15.sp,
                color = Color.White
            ), modifier = Modifier
                .padding(start = 15.dp, end = 15.dp)
                .wrapContentWidth()
        )
    }
}

@Composable
fun MusicBackgroundPreview() {
    MusicBackground(imageUrl = null)
}

@Preview
@Composable
fun MusicPlayActionPreview() {
    MusicPlayAction()
}

//@Preview
//@Composable
//fun MusicSliderPreview() {
//    MusicSlider()
//}
//
//@Preview
//@Composable
//fun MusicProgressPreview() {
//    MusicProgress()
//}