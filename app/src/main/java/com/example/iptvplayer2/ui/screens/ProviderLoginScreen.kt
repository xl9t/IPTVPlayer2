package com.example.iptvplayer2.ui.screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.iptvplayer2.utils.SessionManager
import com.example.iptvplayer2.viewmodel.MainViewModel

@Composable
fun ProviderLoginScreen(navController: NavController, viewModel: MainViewModel) {
    val context = LocalContext.current
    val sessionManager = remember { SessionManager(context) }
    
    // Check if user is already logged in
    LaunchedEffect(Unit) {
        if (sessionManager.isLoggedIn()) {
            val savedServerUrl = sessionManager.getServerUrl()
            val savedUsername = sessionManager.getUsername()
            val savedPassword = sessionManager.getPassword()
            
            if (!savedServerUrl.isNullOrEmpty() && !savedUsername.isNullOrEmpty() && !savedPassword.isNullOrEmpty()) {
                val m3uUrl = "${savedServerUrl}/get.php?username=${savedUsername}&password=${savedPassword}&type=m3u_plus&output=ts"
                val epgUrl = "${savedServerUrl}/xmltv.php?username=${savedUsername}&password=${savedPassword}"
                
                viewModel.loadM3u(m3uUrl, epgUrl)
            }
        }
    }
    
    // Navigate to main screen when channels are loaded successfully
    LaunchedEffect(channelListState) {
        if (channelListState is MainViewModel.ChannelListState.Success) {
            navController.navigate("main") {
                popUpTo("login") { inclusive = true }
            }
        }
    }
    
    var serverUrl by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val channelListState by viewModel.channelListState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Login with your Provider", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(32.dp))

        TextField(
            value = serverUrl,
            onValueChange = { serverUrl = it },
            label = { Text("Server URL (e.g., http://provider.com:80)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                val baseUrl = if (serverUrl.endsWith("/")) serverUrl.dropLast(1) else serverUrl
                val m3uUrl = "${baseUrl}/get.php?username=${username}&password=${password}&type=m3u_plus&output=ts"
                val epgUrl = "${baseUrl}/xmltv.php?username=${username}&password=${password}"
                // Save login info before attempting to load channels
                sessionManager.saveLogin(baseUrl, username, password)
                viewModel.loadM3u(m3uUrl, epgUrl)
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = serverUrl.isNotBlank() && username.isNotBlank() && password.isNotBlank() && channelListState != MainViewModel.ChannelListState.Loading
        ) {
            if (channelListState == MainViewModel.ChannelListState.Loading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
            } else {
                Text("Login")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LaunchedEffect(channelListState) {
            if (channelListState is MainViewModel.ChannelListState.Success) {
                navController.navigate("profile_selection") {
                    popUpTo("provider_login") { inclusive = true }
                }
            }
        }

        if (channelListState is MainViewModel.ChannelListState.Error) {
            Text(
                text = (channelListState as MainViewModel.ChannelListState.Error).message,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        ClickableText(
            text = AnnotatedString("Or login with M3U URL"),
            onClick = { navController.navigate("url_input") },
            style = TextStyle(
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline
            )
        )
    }
}
