package com.gowhich.kun.ui.page.components.HomeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// 1. 搜索页流派卡片的数据结构
data class MusicGenre(val id: Int, val name: String, val startColor: Color, val endColor: Color)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchContentGrid(
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }

    // 2. 严格契合赛博迷幻色系演变的音乐流派模拟数据
    val genreList = remember {
        listOf(
            MusicGenre(1, "流行电音", Color(0xFFFF2A54), Color(0xFF930026)), // 霓虹红渐变
            MusicGenre(2, "赛博重低音", Color(0xFF00F5D4), Color(0xFF005144)), // 薄荷绿渐变
            MusicGenre(3, "蒸汽波 / 休闲", Color(0xFF8E9AA7), Color(0xFF26293A)), // 雾霾蓝灰渐变
            MusicGenre(4, "独立摇滚", Color(0xFF6CDBAC), Color(0xFF1F352A)),
            MusicGenre(5, "说唱 / 嘻哈", Color(0xFFFF5252), Color(0xFF7D0000)),
            MusicGenre(6, "国风民谣", Color(0xFF3D6373), Color(0xFF162A32)),
            MusicGenre(7, "经典黑胶", Color(0xFF4F5366), Color(0xFF1A1C29)),
            MusicGenre(8, "环境白噪音", Color(0xFF00F5D4).copy(alpha = 0.6f), Color(0xFF0D0E15))
        )
    }

    // 使用 M3 的网格布局，全屏撑满并绑定大背景色 CyberBgColor (0xFF0D0E15)
    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // 2列网格
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(
            horizontal = 16.dp, vertical = 16.dp
        ),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        // 模块一：顶部的搜索框 (跨满2列)
        item(span = { GridItemSpan(2) }) {
            Column(modifier = Modifier.padding(vertical = 12.dp)) {
                TextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "搜索图标",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant // 自动变为雾霾蓝灰
                        )
                    },
                    placeholder = {
                        Text(
                            text = "歌曲、歌手、舞台 Live",
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        // 强制输入框容器背景使用二级卡片暗色 CyberSurface (0xFF1A1C29)
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        // 干掉底部的 M3 默认横线
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )
            }
        }

        // 模块二：小标题：浏览所有流派 (跨满2列)
        item(span = { GridItemSpan(2) }) {
            Text(
                text = "热门音乐流派",
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground, // 自动适配纯白字
                modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
            )
        }

        // 模块三：流派色彩网格块列表 (自动排列为 2 列)
        items(items = genreList, key = { it.id }) { genre ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(96.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(
                        Brush.linearGradient(
                            colors = listOf(genre.startColor, genre.endColor)
                        )
                    )
                    .clickable {
//                        onGenreClick(genre)
                    }
                    .padding(16.dp)
            ) {
                // 流派文字，强制粗体纯白，在缤纷的渐变色块上具有极强的张力
                Text(
                    text = genre.name,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    modifier = Modifier.align(Alignment.TopStart)
                )
            }
        }
    }
}