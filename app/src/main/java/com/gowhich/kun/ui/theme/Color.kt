package com.gowhich.kun.ui.theme

import androidx.compose.ui.graphics.Color

// =====================================================================
// 1. 基础调色盘 (核心颜色提取)
// =====================================================================
val CyberBg = Color(0xFF0D0E15)          // 极致暗蓝黑大背景
val CyberSurface = Color(0xFF1A1C29)     // 二级卡片暗蓝
val CyberPrimary = Color(0xFFFF2A54)     // 品牌主色：电音霓虹红
val CyberSecondary = Color(0xFF00F5D4)   // 品牌辅助：极光薄荷绿
val CyberTextPrimary = Color(0xFFFFFFFF) // 主文字：纯白
val CyberTextSecondary = Color(0xFF8E9AA7) // 次文字：雾霾蓝灰
val CyberTextWhite = Color(0xFFFFFFFF)   // 核心文字：纯白
val CyberTextGreyBlue = Color(0xFF8E9AA7)// 次要文字：雾霾蓝灰
val CyberOutline = Color(0xFF26293A)     // 细腻边框线

// 传统 M3 默认保留色（可根据需要微调，此处已根据主色调进行关联适配）
val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)
val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

// =====================================================================
// 2. Material Theme - 浅色模式 (Light Theme)
// 注：音乐App通常不推荐全白，此处做清爽的透白与灰蓝过渡适配
// =====================================================================
val md_theme_light_primary = Color(0xFFFF2A54)             // 使用霓虹红作为主色
val md_theme_light_onPrimary = Color(0xFFFFFFFF)
val md_theme_light_primaryContainer = Color(0xFFFFDADF)
val md_theme_light_onPrimaryContainer = Color(0xFF41000B)
val md_theme_light_secondary = Color(0xFF00F5D4)           // 薄荷绿辅助
val md_theme_light_onSecondary = Color(0xFF00382F)
val md_theme_light_secondaryContainer = Color(0xFF6EFFEB)
val md_theme_light_onSecondaryContainer = Color(0xFF00201A)
val md_theme_light_tertiary = Color(0xFF8E9AA7)
val md_theme_light_onTertiary = Color(0xFFFFFFFF)
val md_theme_light_tertiaryContainer = Color(0xFFDCE2E9)
val md_theme_light_onTertiaryContainer = Color(0xFF171C22)
val md_theme_light_error = Color(0xFFBA1A1A)
val md_theme_light_errorContainer = Color(0xFFFFDAD6)
val md_theme_light_onError = Color(0xFFFFFFFF)
val md_theme_light_onErrorContainer = Color(0xFF410002)
val md_theme_light_background = Color(0xFFF9FAFC)          // 清爽背景
val md_theme_light_onBackground = Color(0xFF0D0E15)
val md_theme_light_surface = Color(0xFFFFFFFF)
val md_theme_light_onSurface = Color(0xFF0D0E15)
val md_theme_light_surfaceVariant = Color(0xFFE2E2EC)
val md_theme_light_onSurfaceVariant = Color(0xFF45464F)
val md_theme_light_outline = Color(0xFF767680)
val md_theme_light_inverseOnSurface = Color(0xFFF1F0F4)
val md_theme_light_inverseSurface = Color(0xFF2F3033)
val md_theme_light_inversePrimary = Color(0xFFFFB2BC)
val md_theme_light_shadow = Color(0xFF000000)
val md_theme_light_surfaceTint = Color(0xFFFF2A54)
val md_theme_light_outlineVariant = Color(0xFFC6C6D0)
val md_theme_light_scrim = Color(0xFF000000)

// =====================================================================
// 3. Material Theme - 暗色模式 (Dark Theme)
// ★★★ 完全应用“赛博迷幻流派”核心推荐配色 ★★★
// =====================================================================
val md_theme_dark_primary = CyberPrimary                   // #FF2A54 霓虹红
val md_theme_dark_onPrimary = Color(0xFF650017)            // 暗红文字衬底
val md_theme_dark_primaryContainer = Color(0xFF930026)     // 深红容器
val md_theme_dark_onPrimaryContainer = Color(0xFFFFDADF)   // 浅红文字
val md_theme_dark_secondary = CyberSecondary               // #00F5D4 极光薄荷绿
val md_theme_dark_onSecondary = Color(0xFF00382F)
val md_theme_dark_secondaryContainer = Color(0xFF005144)   // 深绿容器
val md_theme_dark_onSecondaryContainer = Color(0xFF6EFFEB)
val md_theme_dark_tertiary = CyberTextSecondary            // #8E9AA7 雾霾蓝灰
val md_theme_dark_onTertiary = Color(0xFF1E252D)
val md_theme_dark_tertiaryContainer = Color(0xFF353C44)
val md_theme_dark_onTertiaryContainer = Color(0xFFDCE2E9)
val md_theme_dark_error = Color(0xFFFFB4AB)
val md_theme_dark_errorContainer = Color(0xFF93000A)
val md_theme_dark_onError = Color(0xFF690005)
val md_theme_dark_onErrorContainer = Color(0xFFFFDAD6)
val md_theme_dark_background = CyberBg                     // #0D0E15 极致暗黑蓝背景
val md_theme_dark_onBackground = CyberTextPrimary          // #FFFFFF 纯白文字
val md_theme_dark_surface = CyberSurface                   // #1A1C29 稍微亮一点的卡片背景
val md_theme_dark_onSurface = CyberTextPrimary             // #FFFFFF 纯白文字
val md_theme_dark_surfaceVariant = Color(0xFF26293A)        // 用于更明显的卡片悬浮层
val md_theme_dark_onSurfaceVariant = CyberTextSecondary    // #8E9AA7 辅助文字
val md_theme_dark_outline = Color(0xFF4F5366)              // 边界线：暗蓝灰
val md_theme_dark_inverseOnSurface = CyberBg
val md_theme_dark_inverseSurface = CyberTextPrimary
val md_theme_dark_inversePrimary = Color(0xFFB80036)
val md_theme_dark_shadow = Color(0xFF000000)
val md_theme_dark_surfaceTint = CyberPrimary
val md_theme_dark_outlineVariant = Color(0xFF3B3E51)
val md_theme_dark_scrim = Color(0xFF000000)