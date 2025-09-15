package com.example.iptvplayer2.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.iptvplayer2.data.model.Profile
import com.example.iptvplayer2.viewmodel.ProfileViewModel

@Composable
fun ProfileSelectionScreen(navController: NavController, viewModel: ProfileViewModel) {
    val profiles by viewModel.profiles.collectAsState()
    var showPinDialog by remember { mutableStateOf<Profile?>(null) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Who's Watching?", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(64.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            items(profiles) { profile ->
                ProfileItem(profile = profile) {
                    if (profile.isLocked) {
                        showPinDialog = profile
                    } else {
                        viewModel.selectProfile(profile)
                        navController.navigate("main") {
                            popUpTo("profile_selection") { inclusive = true }
                        }
                    }
                }
            }
        }

        if (showPinDialog != null) {
            PinEntryDialog(
                onDismiss = { showPinDialog = null },
                onPinVerified = {
                    viewModel.selectProfile(showPinDialog!!)
                    showPinDialog = null
                    navController.navigate("url_input")
                },
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun ProfileItem(profile: Profile, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Box {
            Image(
                painter = painterResource(id = profile.avatar ?: com.example.iptvplayer2.R.drawable.ic_launcher_foreground),
            contentDescription = profile.name,
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
            )
            if (profile.isLocked) {
                Icon(
                    imageVector = Icons.Default.Lock,
                    contentDescription = "Locked",
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .background(MaterialTheme.colorScheme.surface, CircleShape)
                        .padding(4.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = profile.name, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
fun PinEntryDialog(
    onDismiss: () -> Unit,
    onPinVerified: () -> Unit,
    viewModel: ProfileViewModel
) {
    var pin by remember { mutableStateOf("") }
    var error by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Enter PIN") },
        text = {
            Column {
                OutlinedTextField(
                    value = pin,
                    onValueChange = { pin = it.take(4) }, // Limit PIN to 4 digits
                    label = { Text("PIN") },
                    isError = error,
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
                )
                if (error) {
                    Text("Incorrect PIN", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall)
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                if (viewModel.verifyPin(pin)) {
                    onPinVerified()
                } else {
                    error = true
                }
            }) {
                Text("Unlock")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
