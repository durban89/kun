package com.gowhich.kun.ui.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// 首页
@Composable
fun HomeScreen(navController: NavController) {
    val fontOffset = Offset(5f, 10f)
    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.Gray),
        horizontalAlignment = Alignment.Start) {

        Spacer(modifier = Modifier
            .height(44.dp)
            .fillMaxWidth()
            .background(Color.Red))

        Column(
            modifier = Modifier
                .height(44.dp)
                .background(Color.Blue)
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .background(Color.Yellow),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Button(onClick = { navController.navigate("detail") }) {
                    Text("详情页")
                }

                Text(
                    text = "Hello",
                    style = TextStyle(
                        fontSize = 15.sp,
                        shadow = Shadow(
                            color = Color.Red,
                            offset = fontOffset,
                            blurRadius = 5f
                        )
                    )
                )
                Text("World", color = Color.Gray)
            }


        }

        MessageList()

    }
}

@Composable
private fun MessageList() {
    val messages: List<String> = listOf("1", "2", "3", "4", "5", "6", "7", "8",
        "1", "2", "3", "4", "5", "6", "7", "8")
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .background(color = Color.Gray)
    ) {

        messages.forEach { message ->
            Row(
                modifier = Modifier
                    .height(50.dp)
                    .padding(start = 16.dp, end = 16.dp)
                    .fillMaxWidth()
                    .background(color = Color.Blue)
            ) {
                Text(text = message, style = TextStyle(color = Color.White))
            }

            Spacer(modifier = Modifier
                .height(5.dp)
                .fillMaxWidth()
                .background(Color.Red))
        }
    }
}