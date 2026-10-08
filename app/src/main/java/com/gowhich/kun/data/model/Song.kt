package com.gowhich.kun.data.model

import kotlinx.serialization.Serializable

/**
 * 音频来源类型
 */
@Serializable
enum class SongSource {
    /** 网络地址 (手动输入 URL) */
    NETWORK,

    /** 本地导入的文件 */
    LOCAL
}

/**
 * 单曲模型，可序列化用于本地持久化。
 *
 * @param id 统一生成的唯一 ID
 * @param title 标题
 * @param artist 艺术家/作者
 * @param genre 分类 (流派/歌单)
 * @param source 音频来源类型
 * @param uri 播放地址：网络 URL 或本地 content:// uri
 * @param coverUrl 封面图片地址 (可选)
 * @param isFavorite 是否标记喜欢
 * @param addedAt 添加时间戳 (毫秒)
 */
@Serializable
data class Song(
    val id: String,
    val title: String,
    val artist: String = "",
    val genre: String = "",
    val source: SongSource,
    val uri: String,
    val coverUrl: String? = null,
    val isFavorite: Boolean = false,
    val addedAt: Long = 0L
)
