package com.gowhich.kun.ui.page

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CompassCalibration
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.gowhich.kun.ui.page.components.HomeScreen.DiscoverContentList
import com.gowhich.kun.ui.page.components.HomeScreen.LibraryScreen
import com.gowhich.kun.ui.page.components.HomeScreen.SearchContentGrid


// 首页
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    var selectedTab by remember { mutableIntStateOf(0) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,

            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Kun Music",
                        )
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        titleContentColor = MaterialTheme.colorScheme.onBackground
                    )
                )
            },

            bottomBar = {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 0.dp
                ) {
                    NavigationBarItem(
                        selected = selectedTab == 0,
                        onClick = {
                            selectedTab = 0
                        },
                        icon = {
                            Icon(
                                Icons.Default.CompassCalibration,
                                contentDescription = "Discover"
                            )
                        },
                        label = {
                            Text("Discover")
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,     // 选中时为霓虹红
                            selectedTextColor = MaterialTheme.colorScheme.primary,     // 选中时文字为霓虹红
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant, // 未选中为雾霾蓝灰
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            indicatorColor = Color.Transparent // 去掉 M3 默认的椭圆背景，保持极简
                        ),
                    )

                    NavigationBarItem(
                        selected = selectedTab == 1,
                        onClick = {
                            selectedTab = 1
                        },
                        icon = {
                            Icon(
                                Icons.Default.Search,
                                contentDescription = "Search"
                            )
                        },
                        label = {
                            Text("Search")
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,     // 选中时为霓虹红
                            selectedTextColor = MaterialTheme.colorScheme.primary,     // 选中时文字为霓虹红
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant, // 未选中为雾霾蓝灰
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            indicatorColor = Color.Transparent // 去掉 M3 默认的椭圆背景，保持极简
                        ),
                    )

                    NavigationBarItem(
                        selected = selectedTab == 2,
                        onClick = {
                            selectedTab = 2
                        },
                        icon = {
                            Icon(
                                Icons.Default.LibraryMusic,
                                contentDescription = "Library"
                            )
                        },
                        label = {
                            Text("Library")
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,     // 选中时为霓虹红
                            selectedTextColor = MaterialTheme.colorScheme.primary,     // 选中时文字为霓虹红
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant, // 未选中为雾霾蓝灰
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            indicatorColor = Color.Transparent // 去掉 M3 默认的椭圆背景，保持极简
                        ),
                    )
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                when (selectedTab) {
                    0 -> DiscoverContentList(navController)
                    1 -> SearchContentGrid(navController)
                    2 -> LibraryScreen(navController)
                }
            }
        }
    }
}
