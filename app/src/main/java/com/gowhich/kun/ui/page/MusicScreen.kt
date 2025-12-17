package com.gowhich.kun.ui.page

import android.R
import android.util.Log
import android.widget.StackView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import java.lang.invoke.MutableCallSite

private const val TAG: String = "MusicScreen"

@Composable
fun MusicScreen(navController: NavController) {
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

        // 操作按钮
//            上一曲 播放/暂停 下一曲
    }
}
@Preview
@Composable
fun MusicBackgroundPreview() {
    MusicBackground(imageUrl = null)
}



@Composable
fun MusicBackground(
    imageUrl: String?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = Modifier.fillMaxWidth().height(400.dp)
            .background(Color.LightGray)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = "专辑图片",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth().height(400.dp),
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

    Row (
        modifier = Modifier.background(Color.Blue).height(45.dp).fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("11:00",
            style = TextStyle(
            fontSize = 15.sp,
            color = Color.White
        ), modifier = Modifier.padding(start = 15.dp, end = 15.dp).wrapContentWidth())

        Column (
            modifier = Modifier.height(10.dp).wrapContentWidth(),
        ) {
            LinearProgressIndicator(
            progress = { progress.floatValue },
            modifier = Modifier.height(10.dp).
                width(200.dp),
            color = Color.Red,
            trackColor = Color.Gray,
            strokeCap = StrokeCap.Round,
            )
        }

        Text("12:00", style = TextStyle(
            fontSize = 15.sp,
            color = Color.White
        ), modifier = Modifier.padding(start = 15.dp, end = 15.dp).wrapContentWidth())
    }
}