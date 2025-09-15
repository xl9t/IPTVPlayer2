package com.example.iptvplayer2.ui.screens

import android.net.Uri
import android.util.Log
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AspectRatio
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaItem
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import java.net.URLDecoder
import java.nio.charset.StandardCharsets

@Composable
fun PlayerScreen(
    videoUrl: String,
    onBack: () -> Unit = {}
) {
    val context = LocalContext.current
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var showError by remember { mutableStateOf(false) }
    
    // Decode the URL
    val decodedUrl = remember(videoUrl) {
        try {
            URLDecoder.decode(videoUrl, StandardCharsets.UTF_8.toString())
        } catch (e: Exception) {
            errorMessage = "Geçersiz video URL'si"
            showError = true
            ""
        }
    }

    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            addListener(object : Player.Listener {
                override fun onPlayerError(error: PlaybackException) {
                    super.onPlayerError(error)
                    errorMessage = "Video oynatılırken hata oluştu: ${error.message}"
                    showError = true
                }
            })
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    if (decodedUrl.isNotBlank()) {
                        try {
                            val mediaItem = MediaItem.fromUri(Uri.parse(decodedUrl))
                            exoPlayer.setMediaItem(mediaItem)
                            exoPlayer.prepare()
                            exoPlayer.playWhenReady = true
                        } catch (e: Exception) {
                            errorMessage = "Video yüklenirken hata oluştu: ${e.message}"
                            showError = true
                        }
                    }
                }
                Lifecycle.Event.ON_STOP -> {
                    exoPlayer.stop()
                    exoPlayer.clearMediaItems()
                }
                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
            exoPlayer.release()
        }
    }

    var resizeMode by remember { mutableStateOf(AspectRatioFrameLayout.RESIZE_MODE_FIT) }

    // Show error dialog if there's an error
    if (showError && errorMessage != null) {
        AlertDialog(
            onDismissRequest = { showError = false },
            title = { Text("Hata") },
            text = { Text(errorMessage ?: "Bilinmeyen bir hata oluştu") },
            confirmButton = {
                TextButton(
                    onClick = { showError = false }
                ) {
                    Text("Tamam")
                }
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        if (decodedUrl.isBlank()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.Error,
                        contentDescription = "Hata",
                        tint = Color.Red,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Geçersiz video URL'si", color = Color.White)
                }
            }
        } else {
            AndroidView(
                factory = { context ->
                    PlayerView(context).apply {
                        player = exoPlayer
                        useController = true
                        resizeMode = resizeMode
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
        }

        // Back button
        IconButton(
            onClick = onBack,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Geri",
                tint = Color.White
            )
        }

        // Aspect ratio button
        IconButton(
            onClick = {
                resizeMode = when (resizeMode) {
                    AspectRatioFrameLayout.RESIZE_MODE_FIT -> AspectRatioFrameLayout.RESIZE_MODE_ZOOM
                    AspectRatioFrameLayout.RESIZE_MODE_ZOOM -> AspectRatioFrameLayout.RESIZE_MODE_FILL
                    else -> AspectRatioFrameLayout.RESIZE_MODE_FIT
                }
            },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.AspectRatio,
                contentDescription = "Görüntü Oranı",
                tint = Color.White
            )
        }
    }
}