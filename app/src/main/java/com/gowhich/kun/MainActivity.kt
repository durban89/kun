package com.gowhich.kun

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
                modifier = Modifier.fillMaxWidth()
                    .height(88.dp)
                    .background(Color.Yellow),
                horizontalArrangement = Arrangement.SpaceBetween

            ) {
                Text("Hello", color = Color.Blue)
                Text("World", color = Color.Gray)
            }
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
