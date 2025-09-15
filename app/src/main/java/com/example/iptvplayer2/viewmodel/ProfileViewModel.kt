package com.example.iptvplayer2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iptvplayer2.R
import com.example.iptvplayer2.data.model.Profile
import kotlinx.coroutines.flow.*

class ProfileViewModel : ViewModel() {

    private val _profiles = MutableStateFlow(listOf(
        Profile("1", "Dad", R.drawable.ic_launcher_foreground),
        Profile("2", "Mom", R.drawable.ic_launcher_foreground),
        Profile("3", "Kids", R.drawable.ic_launcher_foreground, isLocked = true),
        Profile("4", "Guest", R.drawable.ic_launcher_foreground)
    ))
    val profiles = _profiles.asStateFlow()

    private val _selectedProfile = MutableStateFlow<Profile?>(null)
    val selectedProfile = _selectedProfile.asStateFlow()

    private val _profileFavorites = MutableStateFlow<Map<String, Set<String>>>(emptyMap())
    val profileFavorites = _profileFavorites.asStateFlow()

    fun selectProfile(profile: Profile) {
        _selectedProfile.value = profile
    }

    fun verifyPin(pin: String): Boolean {
        return pin == "1234"
    }

    fun toggleFavorite(channelUrl: String) {
        val profileId = _selectedProfile.value?.id ?: return
        val currentFavorites = _profileFavorites.value[profileId]?.toMutableSet() ?: mutableSetOf()

        if (channelUrl in currentFavorites) {
            currentFavorites.remove(channelUrl)
        } else {
            currentFavorites.add(channelUrl)
        }

        val updatedMap = _profileFavorites.value.toMutableMap()
        updatedMap[profileId] = currentFavorites
        _profileFavorites.value = updatedMap
    }

    val currentProfileFavorites: StateFlow<Set<String>> = combine(
        selectedProfile,
        _profileFavorites
    ) { profile, favoritesMap ->
        profile?.let { favoritesMap[it.id] } ?: emptySet()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())
}

