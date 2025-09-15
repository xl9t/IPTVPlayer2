package com.example.iptvplayer2.ui.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.iptvplayer2.ui.screens.MainScreen
import com.example.iptvplayer2.ui.screens.ProviderLoginScreen
import com.example.iptvplayer2.ui.screens.PlayerScreen
import com.example.iptvplayer2.ui.screens.ProfileSelectionScreen
import com.example.iptvplayer2.ui.screens.UrlInputScreen
import com.example.iptvplayer2.utils.SessionManager
import com.example.iptvplayer2.viewmodel.MainViewModel
import com.example.iptvplayer2.viewmodel.ProfileViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val mainViewModel: MainViewModel = viewModel()
    val profileViewModel: ProfileViewModel = viewModel()
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    
    // Check if user is already logged in
    val startDestination = if (sessionManager.isLoggedIn()) {
        "main"
    } else {
        "provider_login"
    }

    NavHost(navController = navController, startDestination = startDestination) {
        composable("provider_login") {
            ProviderLoginScreen(navController = navController, viewModel = mainViewModel)
        }
        composable("profile_selection") {
            ProfileSelectionScreen(navController = navController, viewModel = profileViewModel)
        }
        composable("url_input") {
            UrlInputScreen(navController = navController, viewModel = mainViewModel)
        }
        composable("main") {
            MainScreen(
                mainNavController = navController,
                mainViewModel = mainViewModel,
                profileViewModel = profileViewModel
            )
        }
        composable(
            "player/{channelUrl}",
            arguments = listOf(navArgument("channelUrl") { type = NavType.StringType })
        ) { backStackEntry ->
            val channelUrl = backStackEntry.arguments?.getString("channelUrl") ?: ""
            PlayerScreen(
                videoUrl = channelUrl,
                onBack = { navController.navigateUp() }
            )
        }
    }
}

fun String.encodeUrl(): String {
    return URLEncoder.encode(this, StandardCharsets.UTF_8.toString())
}
