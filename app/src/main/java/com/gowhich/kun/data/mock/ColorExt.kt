package com.gowhich.kun.data.mock

import androidx.compose.ui.graphics.Color

/** 将 "#RRGGBB" 或 "RRGGBB" 十六进制字符串解析为 Compose Color (默认不透明) */
fun parseHexColor(hex: String, fallback: Color = Color(0xFFFF2A54)): Color {
    val clean = hex.removePrefix("#")
    val value = clean.toLongOrNull(16) ?: return fallback
    val argb: Long = when (clean.length) {
        6 -> (0xFFL shl 24) or (value and 0xFFFFFFL)
        8 -> value
        else -> (0xFFL shl 24) or (value and 0xFFFFFFL)
    }
    return Color(argb.toInt())
}
