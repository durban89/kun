package com.gowhich.kun.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


val DarkColorScheme = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    secondaryContainer = md_theme_dark_secondaryContainer,
    onSecondaryContainer = md_theme_dark_onSecondaryContainer,
    tertiary = md_theme_dark_tertiary,
    onTertiary = md_theme_dark_onTertiary,
    tertiaryContainer = md_theme_dark_tertiaryContainer,
    onTertiaryContainer = md_theme_dark_onTertiaryContainer,
    error = md_theme_dark_error,
    errorContainer = md_theme_dark_errorContainer,
    onError = md_theme_dark_onError,
    onErrorContainer = md_theme_dark_onErrorContainer,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    surfaceVariant = md_theme_dark_surfaceVariant,
    onSurfaceVariant = md_theme_dark_onSurfaceVariant,
    outline = md_theme_dark_outline,
    inverseOnSurface = md_theme_dark_inverseOnSurface,
    inverseSurface = md_theme_dark_inverseSurface,
    inversePrimary = md_theme_dark_inversePrimary,
    surfaceTint = md_theme_dark_surfaceTint
)

val LightColorScheme = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    secondaryContainer = md_theme_light_secondaryContainer,
    onSecondaryContainer = md_theme_light_onSecondaryContainer,
    tertiary = md_theme_light_tertiary,
    onTertiary = md_theme_light_onTertiary,
    tertiaryContainer = md_theme_light_tertiaryContainer,
    onTertiaryContainer = md_theme_light_onTertiaryContainer,
    error = md_theme_light_error,
    errorContainer = md_theme_light_errorContainer,
    onError = md_theme_light_onError,
    onErrorContainer = md_theme_light_onErrorContainer,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
    surfaceVariant = md_theme_light_surfaceVariant,
    onSurfaceVariant = md_theme_light_onSurfaceVariant,
    outline = md_theme_light_outline,
    inverseOnSurface = md_theme_light_inverseOnSurface,
    inverseSurface = md_theme_light_inverseSurface,
    inversePrimary = md_theme_light_inversePrimary,
    surfaceTint = md_theme_light_surfaceTint,
)



val CyberDarkColorScheme = darkColorScheme(
    primary = CyberPrimary,
    onPrimary = Color(0xFF650017), // 暗红衬底
    primaryContainer = Color(0xFF930026),
    onPrimaryContainer = Color(0xFFFFDADF),

    secondary = CyberSecondary,
    onSecondary = Color(0xFF00382F), // 深绿衬底
    secondaryContainer = Color(0xFF005144),
    onSecondaryContainer = Color(0xFF6EFFEB),

    background = CyberBg,
    onBackground = CyberTextWhite,

    surface = CyberSurface,
    onSurface = CyberTextWhite,
    surfaceVariant = Color(0xFF222435), // 稍微更亮一点的悬浮表面
    onSurfaceVariant = CyberTextGreyBlue,

    outline = CyberOutline,
    outlineVariant = Color(0xFF3B3E51),

    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005)
)

// =====================================================================
// 3. M3 标准浅色模式色彩集（为了保证音乐沉浸感，弱化对比度，使用透白灰蓝过渡）
// =====================================================================
val CyberLightColorScheme = lightColorScheme(
    primary = CyberPrimary,
    onPrimary = CyberTextWhite,
    primaryContainer = Color(0xFFFFDADF),
    onPrimaryContainer = Color(0xFF41000B),

    secondary = Color(0xFF00B39B), // 浅色模式下调低薄荷绿亮度保证白底可读性
    onSecondary = CyberTextWhite,
    secondaryContainer = Color(0xFF6EFFEB),
    onSecondaryContainer = Color(0xFF00201A),

    background = Color(0xFFF9FAFC), // 清爽微蓝白
    onBackground = CyberBg,

    surface = Color(0xFFFFFFFF),
    onSurface = CyberBg,
    surfaceVariant = Color(0xFFE2E2EC),
    onSurfaceVariant = Color(0xFF45464F),

    outline = Color(0xFF767680)
)


@Composable
fun CyberMusicTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) CyberDarkColorScheme else CyberLightColorScheme
    val view = LocalView.current

    // ★ 核心：在这里拦截并接管系统状态栏
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            val windowInsetsController = WindowCompat.getInsetsController(window, view)

            // ★ 核心优化：不再锁死 window.statusBarColor
            // 只根据当前是白天还是黑夜，来动态控制状态栏字体的“黑白翻转”
            // darkTheme = true (夜晚模式) -> isAppearanceLightStatusBars = false (不使用亮色背景的黑字 -> 自动变白字)
            // darkTheme = false (白天模式) -> isAppearanceLightStatusBars = true (使用亮色背景的黑字 -> 自动变黑字)
            windowInsetsController.isAppearanceLightStatusBars = !darkTheme
            windowInsetsController.isAppearanceLightNavigationBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}