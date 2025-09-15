package com.example.iptvplayer2.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.iptvplayer2.data.model.Channel
import com.example.iptvplayer2.data.model.EpgProgram
import com.example.iptvplayer2.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun EpgScreen(mainViewModel: MainViewModel) {
    val channels by mainViewModel.channels.collectAsState()
    val epgPrograms by mainViewModel.epgPrograms.collectAsState()

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(channels) { channel ->
            val programs = channel.tvgId?.let { epgPrograms[it] } ?: emptyList()
            EpgChannelRow(channel = channel, programs = programs)
        }
    }
}

@Composable
fun EpgChannelRow(channel: Channel, programs: List<EpgProgram>) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(120.dp)
                .fillMaxHeight()
                .padding(4.dp)
                .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = channel.name, modifier = Modifier.padding(8.dp), maxLines = 2)
        }

        LazyRow {
            items(programs) { program ->
                EpgProgramItem(program = program)
            }
        }
    }
}

@Composable
fun EpgProgramItem(program: EpgProgram) {
    val sdf = SimpleDateFormat("yyyyMMddHHmmss Z", Locale.getDefault())
    val start = sdf.parse(program.start)?.let { SimpleDateFormat("HH:mm", Locale.getDefault()).format(it) } ?: ""
    val stop = sdf.parse(program.stop)?.let { SimpleDateFormat("HH:mm", Locale.getDefault()).format(it) } ?: ""

    Box(
        modifier = Modifier
            .width(200.dp)
            .fillMaxHeight()
            .padding(4.dp)
            .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f))
            .border(1.dp, MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f))
            .padding(8.dp)
    ) {
        Column {
            Text(text = program.title, style = MaterialTheme.typography.bodyMedium, maxLines = 2)
            Text(text = "$start - $stop", style = MaterialTheme.typography.bodySmall)
        }
    }
}
