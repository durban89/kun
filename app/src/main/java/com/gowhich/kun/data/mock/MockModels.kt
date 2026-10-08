package com.gowhich.kun.data.mock

import kotlinx.serialization.Serializable

/** 发现页 Banner — 静态展示内容 */
@Serializable
data class MockBanner(
    val id: Int,
    val title: String,
    val subtitle: String,
    val startColor: String = "FF2A54",
    val endColor: String = "930026"
)

/** 发现页推荐单曲（静态内置内容，UI 展示用） */
@Serializable
data class MockTrack(
    val id: Int,
    val title: String,
    val artist: String,
    val genre: String,
    val coverColor: String = "FF2A54",
    val uri: String? = null,
    val coverUrl: String? = null
)

/** 搜索页流派 */
@Serializable
data class MockGenre(
    val id: Int,
    val name: String,
    val startColor: String = "FF2A54",
    val endColor: String = "930026"
)

@Serializable
data class MockBundle(
    val banners: List<MockBanner> = emptyList(),
    val tracks: List<MockTrack> = emptyList(),
    val genres: List<MockGenre> = emptyList()
)
