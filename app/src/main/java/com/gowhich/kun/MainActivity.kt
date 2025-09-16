package com.gowhich.kun

import android.graphics.Paint.Style
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gowhich.kun.ui.theme.KunTheme
import com.gowhich.kun.ui.theme.md_theme_light_background

//import com.gowhich.kun.ui.theme.KunTheme


class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MainContainer()
        }
    }
}


@Preview
@Composable
fun MainContainer() {
    val fontOffset = Offset(5f, 10f)
    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.Gray),
        horizontalAlignment = Alignment.Start) {

        Spacer(modifier = Modifier
            .height(88.dp)
            .fillMaxWidth()
            .background(Color.Red))

        Column(
            modifier = Modifier
                .height(88.dp)
                .background(Color.Blue)
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(88.dp)
                    .background(Color.Yellow),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Button(onClick = { /*TODO*/ }) {
                    Text("返回1s")
                }

                Text(
                    text = "Hello",
                    style = TextStyle(
                        fontSize = 30.sp,
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
fun MessageList() {
    val messages: List<String> = listOf("1", "2", "3", "4", "5", "6", "7")
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .background(color = Color.Gray)
    ) {

        messages.forEach { message ->
            Row(
                modifier = Modifier
                    .height(100.dp)
                    .padding(start = 32.dp, end = 32.dp)
                    .fillMaxWidth()
                    .background(color = Color.Blue)
            ) {
                Text(text = message, style = TextStyle(color = Color.White))
            }

            Spacer(modifier = Modifier
                .height(10.dp)
                .fillMaxWidth()
                .background(Color.Red))
        }
    }
}

//
//@Composable
//@Preview
//fun Item() {
//    Column (
//        modifier = Modifier.fillMaxSize(),
//            ) {
//        Column {
//            Column(modifier = Modifier.fillMaxSize()) {
//                Text("hi")
//            }
//        }
//    }
//}
//
//@Preview
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun KunApp() {
//    Scaffold(
//        topBar = {
//            TopBar()
//        }
//    ) {
//        LazyColumn(contentPadding = it) {
//
//        }
//    }
//}
//
//@Composable
//fun TopBar(modifier: Modifier = Modifier) {
//    CenterAlignedTopAppBar(
//        title = {
//            Text("roboto")
//        },
//        modifier = modifier
//    )
//}
