package com.gowhich.kun

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.view.WindowManager
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
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.serialization.Serializable

//import com.gowhich.kun.ui.theme.KunTheme


class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 关键设置：让内容延伸到系统栏区域
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        } else {
            @Suppress("DEPRECATION")
            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        }

        setContent {
            MaterialTheme {
                MainContainer()
            }

        }

        // 延迟设置全屏，确保视图已初始化
        window.decorView.post {
            setFullscreen()
        }
    }

    // 处理窗口焦点变化时重新设置全屏
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            setFullscreen()
        }
    }

    // 设置全屏的具体实现
    private fun setFullscreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            // Android 11 及以上版本
            window.insetsController?.apply {
                hide(WindowInsets.Type.statusBars() or WindowInsets.Type.navigationBars())
                systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        } else {
            // Android 10 及以下版本
            @Suppress("DEPRECATION")
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN)
        }
    }
}



@Preview
@Composable
fun MainContainer() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController)
        }
        composable("detail") {
            DetailScreen(navController)
        }
    }

}

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
//
//    Column(
//        modifier = Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Text("Home Screen")
//        Button(onClick = {
//            // 导航到详情页
//            navController.navigate("detail")
//        }) {
//            Text("Go to Detail")
//        }
//    }
}

// 详情页
@Composable
fun DetailScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Detail Screen")
        Button(onClick = {
            // 返回上一页
            navController.popBackStack()
        }) {
            Text("Go Back")
        }
    }
}

@Composable
fun MessageList() {
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
