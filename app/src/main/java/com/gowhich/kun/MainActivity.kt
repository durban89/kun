package com.gowhich.kun

import android.app.Activity
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
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.gowhich.kun.ui.page.DetailScreen
import com.gowhich.kun.ui.page.HomeScreen
import com.gowhich.kun.ui.page.MusicScreen
import com.gowhich.kun.ui.theme.CyberDarkColorScheme
import com.gowhich.kun.ui.theme.CyberLightColorScheme
import com.gowhich.kun.ui.theme.CyberMusicTheme
import com.gowhich.kun.ui.theme.DarkColorScheme
import com.gowhich.kun.ui.theme.LightColorScheme
import kotlinx.serialization.Serializable

//import com.gowhich.kun.ui.theme.KunTheme


class MainActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

//        // 关键设置：让内容延伸到系统栏区域
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            window.setDecorFitsSystemWindows(false)
//        } else {
//            @Suppress("DEPRECATION")
//            window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
//        }

        setContent {
            CyberMusicTheme() {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainContainer()
                }
            }
        }
    }
}


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

        composable("music") {
            MusicScreen(navController)
        }
    }

}
