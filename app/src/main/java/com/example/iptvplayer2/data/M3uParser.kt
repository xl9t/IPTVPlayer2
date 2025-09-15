package com.example.iptvplayer2.data

import com.example.iptvplayer2.data.model.Channel

object M3uParser {

    private val TVG_ID_REGEX = "tvg-id=\"([^\"]*)\"".toRegex()
    private val TVG_LOGO_REGEX = "tvg-logo=\"([^\"]*)\"".toRegex()
    private val GROUP_TITLE_REGEX = "group-title=\"([^\"]*)\"".toRegex()

    fun parse(m3uString: String): List<Channel> {
        val channels = mutableListOf<Channel>()
        val lines = m3uString.split("\r?\n".toRegex()).filter { it.isNotBlank() }

        var i = 0
        while (i < lines.size) {
            val line = lines[i].trim()
            if (line.startsWith("#EXTINF")) {
                // Ensure there is a next line for the URL
                if (i + 1 < lines.size) {
                    val url = lines[i + 1].trim()
                    if (url.startsWith("http")) { // Basic validation for URL
                        val name = line.substringAfterLast(",").trim()
                        val logo = TVG_LOGO_REGEX.find(line)?.groups?.get(1)?.value
                        val group = GROUP_TITLE_REGEX.find(line)?.groups?.get(1)?.value
                        val tvgId = TVG_ID_REGEX.find(line)?.groups?.get(1)?.value

                        channels.add(Channel(name = name, logo = logo, url = url, group = group, tvgId = tvgId))
                        i++ // Increment to skip the URL line in the next iteration
                    }
                }
            }
            i++
        }
        return channels
    }
}
