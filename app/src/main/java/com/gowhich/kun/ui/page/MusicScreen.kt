package com.gowhich.kun.ui.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun MusicScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 背景图
        MusicBackground()

        // 进度条

        // 操作按钮
//            上一曲 播放/暂停 下一曲
    }
}

@Composable
@Preview
fun MusicBackground() {
    Row(
        modifier = Modifier.fillMaxWidth().height(400.dp).background(Color.Red)
    ) {

    }
}

@Preview
@Composable
fun MusicProgress() {
    val progress = remember { mutableFloatStateOf(0.5f) }

    Row (
        modifier = Modifier.background(Color.Blue).height(45.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("11:00",
            style = TextStyle(
            fontSize = 15.sp,
            color = Color.White
        ), modifier = Modifier.padding(start = 15.dp, end = 15.dp))

        Column (
//            modifier = Modifier.layout(1)
        ) {
            LinearProgressIndicator(
            progress = { progress.floatValue },
            modifier = Modifier.height(10.dp).wrapContentWidth(),
            color = Color.Red,
            trackColor = Color.Gray,
            strokeCap = StrokeCap.Round,
            )
        }

        Text("12:00", style = TextStyle(
            fontSize = 15.sp,
            color = Color.White
        ), modifier = Modifier.padding(start = 15.dp, end = 15.dp))
    }
}