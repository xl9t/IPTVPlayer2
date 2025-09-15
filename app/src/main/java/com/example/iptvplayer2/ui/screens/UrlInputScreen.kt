package com.example.iptvplayer2.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.iptvplayer2.viewmodel.MainViewModel

@Composable
fun UrlInputScreen(navController: NavController, viewModel: MainViewModel) {
    var m3uUrl by remember { mutableStateOf("") }
    var epgUrl by remember { mutableStateOf("") }
    val channelListState by viewModel.channelListState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Enter M3U/M3U8 URL", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(32.dp))

        TextField(
            value = m3uUrl,
            onValueChange = { m3uUrl = it },
            label = { Text("M3U URL") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        TextField(
            value = epgUrl,
            onValueChange = { epgUrl = it },
            label = { Text("EPG URL (Optional)") },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { viewModel.loadM3u(m3uUrl, epgUrl.ifBlank { null }) },
            modifier = Modifier.fillMaxWidth(),
            enabled = channelListState != MainViewModel.ChannelListState.Loading
        ) {
            if (channelListState == MainViewModel.ChannelListState.Loading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
            } else {
                Text("Load Channels")
            }
        }

        LaunchedEffect(channelListState) {
            if (channelListState is MainViewModel.ChannelListState.Success) {
                navController.navigate("main") {
                    popUpTo("url_input") { inclusive = true }
                }
            }
        }

        if (channelListState is MainViewModel.ChannelListState.Error) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = (channelListState as MainViewModel.ChannelListState.Error).message,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}
