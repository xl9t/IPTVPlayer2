package com.example.iptvplayer2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.iptvplayer2.data.EpgParser
import com.example.iptvplayer2.data.M3uParser
import com.example.iptvplayer2.data.model.Channel
import com.example.iptvplayer2.data.model.EpgProgram
import com.example.iptvplayer2.data.network.RetrofitInstance
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _channels = MutableStateFlow<List<Channel>>(emptyList())
    val channels = _channels.asStateFlow()

    private val _epgPrograms = MutableStateFlow<Map<String, List<EpgProgram>>>(emptyMap())
    val epgPrograms = _epgPrograms.asStateFlow()

    val channelGroups = channels.map { channelList ->
        channelList.groupBy { it.group ?: "Other" }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyMap())

    private val _selectedGroup = MutableStateFlow("All")
    val selectedGroup = _selectedGroup.asStateFlow()

    fun getFilteredChannelsFlow(favorites: StateFlow<Set<String>>) = combine(searchQuery, channels, selectedGroup, favorites) { query, channelList, group, favs ->
        val channelsInGroup = when (group) {
            "All" -> channelList
            "Favorites" -> channelList.filter { it.url in favs }
            else -> channelList.filter { it.group == group }
        }
        if (query.isBlank()) {
            channelsInGroup
        } else {
            channelsInGroup.filter {
                it.name.contains(query, ignoreCase = true)
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    sealed class ChannelListState {
        object Idle : ChannelListState()
        object Loading : ChannelListState()
        object Success : ChannelListState()
        data class Error(val message: String) : ChannelListState()
    }

    private val _channelListState = MutableStateFlow<ChannelListState>(ChannelListState.Idle)
    val channelListState = _channelListState.asStateFlow()

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun onGroupSelected(group: String) {
        _selectedGroup.value = group
    }

    fun loadM3u(url: String, epgUrl: String? = null) {
        viewModelScope.launch {
            _channelListState.value = ChannelListState.Loading
            try {
                val m3uResponse = RetrofitInstance.api.getM3u(url)
                if (m3uResponse.isSuccessful && m3uResponse.body() != null) {
                    val m3uString = m3uResponse.body()!!
                    val parsedChannels = M3uParser.parse(m3uString)
                    _channels.value = parsedChannels
                    val groups = parsedChannels.mapNotNull { it.group }.distinct()
                    _selectedGroup.value = if (groups.isNotEmpty()) "All" else "Other"

                    epgUrl?.let { fetchEpg(it) }

                    _channelListState.value = ChannelListState.Success
                } else {
                    _channelListState.value = ChannelListState.Error("Failed to load M3U file")
                }
            } catch (e: Exception) {
                _channelListState.value = ChannelListState.Error("Error loading playlist: ${e.message}")
            }
        }
    }

    private fun fetchEpg(url: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getM3u(url)
                if (response.isSuccessful && response.body() != null) {
                    val epgInputStream = response.body()!!.byteInputStream()
                    val programs = EpgParser.parse(epgInputStream)
                    _epgPrograms.value = programs.groupBy { it.channelId }
                } else {
                    // EPG loading failure is not critical
                }
            } catch (e: Exception) {
                // EPG loading failure is not critical
                e.printStackTrace()
            }
        }
    }
}

