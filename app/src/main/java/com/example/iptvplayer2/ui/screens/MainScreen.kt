package com.example.iptvplayer2.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.iptvplayer2.utils.SessionManager
import com.example.iptvplayer2.viewmodel.MainViewModel
import com.example.iptvplayer2.viewmodel.ProfileViewModel

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Channels : Screen("channels", "Channels", Icons.Filled.LiveTv)
    object EPG : Screen("epg", "EPG", Icons.Filled.List)
}

private val items = listOf(
    Screen.Channels,
    Screen.EPG,
)

@Composable
fun MainScreen(mainNavController: NavController, mainViewModel: MainViewModel, profileViewModel: ProfileViewModel) {
    val navController = rememberNavController()
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("BumIPTV Player") },
                actions = {
                    IconButton(onClick = {
                        // Clear session and navigate to login
                        sessionManager.clearSession()
                        mainNavController.navigate("provider_login") {
                            popUpTo("main") { inclusive = true }
                        }
                    }) {
                        Icon(Icons.Default.Logout, contentDescription = "Logout")
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = null) },
                        label = { Text(screen.label) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Channels.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Channels.route) {
                ChannelListScreen(
                    navController = navController,
                    viewModel = mainViewModel,
                    onChannelClick = { channelUrl ->
                        mainNavController.navigate("player/${encodeUrl(channelUrl)}")
                    }
                )
            }
            composable(Screen.EPG.route) {
                EpgScreen(viewModel = mainViewModel)
            }
        }
    }
}

private fun encodeUrl(url: String): String {
    return java.net.URLEncoder.encode(url, "UTF-8")
}