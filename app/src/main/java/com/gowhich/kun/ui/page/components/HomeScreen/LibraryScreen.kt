package com.gowhich.kun.ui.page.components.HomeScreen


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.DownloadForOffline
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

// 1. 歌单与功能入口的数据模型
data class UserPlaylistItem(val id: Int, val title: String, val count: Int, val isFavorite: Boolean = false)
data class QuickLibraryAction(val title: String, val icon: ImageVector, val count: String)

@Composable
fun LibraryScreen(navController: NavController) {

    // 模拟快捷入口数据
    val quickActions = remember {
        listOf(
            QuickLibraryAction("最近播放", Icons.Default.History, "120首"),
            QuickLibraryAction("本地下载", Icons.Default.DownloadForOffline, "34首")
        )
    }

    // 模拟用户创建/收藏的歌单列表
    val userPlaylists = remember {
        listOf(
            UserPlaylistItem(1, "我喜欢的音乐", 128, isFavorite = true), // 特殊红心歌单
            UserPlaylistItem(2, "深夜社畜疗伤私房歌", 45),
            UserPlaylistItem(3, "流光霓虹 赛博朋克蹦迪指南", 89),
            UserPlaylistItem(4, "Lo-Fi 编程沉浸式BGM", 60),
            UserPlaylistItem(5, "硬核重低音 Bass 炸街", 23)
        )
    }

    // 最外层继承 M3 全局大背景（0xFF0D0E15），并留出状态栏的安全边距
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding(),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {

        // ==========================================
        // 模块一：用户个人信息头部（纯白高亮字）
        // ==========================================
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 模拟用户头像：使用薄荷绿到深绿的渐变色块
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(MaterialTheme.colorScheme.secondary, Color(0xFF005144))
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text("DJ", color = Color.White, fontWeight = FontWeight.Black, fontSize = 20.sp)
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = "赛博音乐旅人",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "LV.8 · 听歌 4,321 小时",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant // 雾霾蓝灰
                    )
                }
            }
        }

        // ==========================================
        // 模块二：功能快捷入口（最近播放/本地下载）
        // ==========================================
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                quickActions.forEach { action ->
                    Row(
                        modifier = Modifier
                            .weight(1f)
                            .height(60.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(MaterialTheme.colorScheme.surface) // 卡片暗蓝 #1A1C29
                            .clickable { /* 跳转到对应列表 */ }
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = action.icon,
                            contentDescription = action.title,
                            tint = MaterialTheme.colorScheme.secondary, // 薄荷绿图标
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(action.title, color = MaterialTheme.colorScheme.onSurface, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                            Text(action.count, color = MaterialTheme.colorScheme.onSurfaceVariant, fontSize = 11.sp)
                        }
                    }
                }
            }
        }

        // ==========================================
        // 模块三：小标题：创建的歌单
        // ==========================================
        item {
            Text(
                text = "创建的歌单 (${userPlaylists.size}个)",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 28.dp, bottom = 12.dp)
            )
        }

        // ==========================================
        // 模块四：歌单列表（使用 M3 Surface 层级隔离）
        // ==========================================
        items(items = userPlaylists, key = { it.id }) { playlist ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 6.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.surface) // 卡片暗蓝 #1A1C29
                    .clickable { navController.navigate("detail") }
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 歌单封面占位图
                Box(
                    modifier = Modifier
                        .size(52.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(
                            if (playlist.isFavorite) {
                                // 如果是我喜欢的音乐，封面使用电音霓虹红的渐变，突出核心红心
                                Brush.verticalGradient(
                                    colors = listOf(MaterialTheme.colorScheme.primary, Color(0xFF930026))
                                )
                            } else {
                                // 普通歌单使用极简的高级深色块
                                Brush.verticalGradient(
                                    colors = listOf(Color(0xFF26293A), Color(0xFF0D0E15))
                                )
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    if (playlist.isFavorite) {
                        // 红心图标
                        Icon(Icons.Default.Favorite, contentDescription = "红心", tint = Color.White, modifier = Modifier.size(24.dp))
                    } else {
                        // 音符图标
                        Icon(Icons.Default.MusicNote, contentDescription = "歌单", tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(20.dp))
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                // 歌单文本信息
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = playlist.title,
                        color = MaterialTheme.colorScheme.onSurface, // 纯白高亮字
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${playlist.count} 首歌曲",
                        color = MaterialTheme.colorScheme.onSurfaceVariant, // 雾霾蓝灰
                        fontSize = 12.sp
                    )
                }

                // 右侧向右箭头导航标识
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "进入歌单",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}