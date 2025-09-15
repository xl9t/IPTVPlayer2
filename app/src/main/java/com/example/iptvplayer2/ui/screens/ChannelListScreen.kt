package com.example.iptvplayer2.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.iptvplayer2.data.model.Channel
import com.example.iptvplayer2.ui.navigation.encodeUrl
import com.example.iptvplayer2.viewmodel.MainViewModel
import com.example.iptvplayer2.viewmodel.ProfileViewModel

@Composable
fun ChannelListScreen(
    navController: NavController,
    mainViewModel: MainViewModel,
    profileViewModel: ProfileViewModel
) {
    val channelListState by mainViewModel.channelListState.collectAsState()
    val searchQuery by mainViewModel.searchQuery.collectAsState()
    val currentProfileFavorites by profileViewModel.currentProfileFavorites.collectAsState()
    val filteredChannels by mainViewModel.getFilteredChannelsFlow(profileViewModel.currentProfileFavorites).collectAsState()
    val channelGroups by mainViewModel.channelGroups.collectAsState()
    val selectedGroup by mainViewModel.selectedGroup.collectAsState()
    val epgPrograms by mainViewModel.epgPrograms.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        TextField(
            value = searchQuery,
            onValueChange = mainViewModel::onSearchQueryChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            label = { Text("Search Channels") },
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface,
            )
        )

        if (channelGroups.isNotEmpty()) {
            val groupTitles = listOf("All", "Favorites") + channelGroups.keys.sorted()
            ScrollableTabRow(
                selectedTabIndex = groupTitles.indexOf(selectedGroup),
                edgePadding = 16.dp
            ) {
                groupTitles.forEach { title ->
                    Tab(
                        selected = title == selectedGroup,
                        onClick = { mainViewModel.onGroupSelected(title) },
                        text = { Text(text = title) }
                    )
                }
            }
        }

        when (channelListState) {
            is MainViewModel.ChannelListState.Success -> {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 150.dp),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(filteredChannels) { channel ->
                        val currentProgram = channel.tvgId?.let { epgPrograms[it]?.firstOrNull() }
                        ChannelItem(
                            channel = channel,
                            isFavorite = channel.url in currentProfileFavorites,
                            onFavoriteClick = { profileViewModel.toggleFavorite(channel.url) },
                            onClick = { navController.navigate("player/${channel.url.encodeUrl()}") },
                            currentProgramTitle = currentProgram?.title
                        )
                    }
                }
            }
            is MainViewModel.ChannelListState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is MainViewModel.ChannelListState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = (channelListState as MainViewModel.ChannelListState.Error).message,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
            else -> {}
        }
    }
}

@Composable
fun ChannelItem(
    channel: Channel,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    onClick: () -> Unit,
    currentProgramTitle: String?
) {
    Card(
        modifier = Modifier
            .aspectRatio(0.75f)
            .clickable(onClick = onClick),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize()
        ) {
            AsyncImage(
                model = channel.logo,
                contentDescription = channel.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                contentScale = ContentScale.Crop,
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = channel.name,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = onFavoriteClick, modifier = Modifier.size(24.dp)) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = if (isFavorite) Color.Red else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
                currentProgramTitle?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colorScheme.secondary
                    )
                }
            }
        }
    }
}