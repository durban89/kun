package com.gowhich.kun.data.mock

import android.content.Context
import kotlinx.serialization.json.Json

/**
 * 从 assets 读取打包的静态内容。
 * 无需服务端，纯本地资源驱动发现/搜索页。
 */
object BundledContentLoader {

    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    fun load(context: Context): MockBundle {
        return runCatching {
            val raw = context.assets.open("mock/content.json").bufferedReader().use { it.readText() }
            json.decodeFromString(MockBundle.serializer(), raw)
        }.getOrDefault(MockBundle())
    }
}
