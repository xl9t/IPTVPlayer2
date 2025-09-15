package com.example.iptvplayer2.data

import android.util.Xml
import com.example.iptvplayer2.data.model.EpgProgram
import org.xmlpull.v1.XmlPullParser
import java.io.InputStream

object EpgParser {
    private val ns: String? = null

    fun parse(inputStream: InputStream): List<EpgProgram> {
        inputStream.use { stream ->
            val parser: XmlPullParser = Xml.newPullParser()
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(stream, null)
            parser.nextTag()
            return readFeed(parser)
        }
    }

    private fun readFeed(parser: XmlPullParser): List<EpgProgram> {
        val programs = mutableListOf<EpgProgram>()

        parser.require(XmlPullParser.START_TAG, ns, "tv")
        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            if (parser.name == "programme") {
                programs.add(readProgramme(parser))
            }
             else {
                skip(parser)
            }
        }
        return programs
    }

    private fun readProgramme(parser: XmlPullParser): EpgProgram {
        parser.require(XmlPullParser.START_TAG, ns, "programme")
        val channelId = parser.getAttributeValue(null, "channel")
        val start = parser.getAttributeValue(null, "start")
        val stop = parser.getAttributeValue(null, "stop")
        var title: String? = null
        var description: String? = null

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.eventType != XmlPullParser.START_TAG) {
                continue
            }
            when (parser.name) {
                "title" -> title = readText(parser, "title")
                "desc" -> description = readText(parser, "desc")
                else -> skip(parser)
            }
        }
        return EpgProgram(channelId, title ?: "", description, start, stop)
    }

    private fun readText(parser: XmlPullParser, tagName: String): String {
        parser.require(XmlPullParser.START_TAG, ns, tagName)
        var result = ""
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.text
            parser.nextTag()
        }
        parser.require(XmlPullParser.END_TAG, ns, tagName)
        return result
    }

    private fun skip(parser: XmlPullParser) {
        if (parser.eventType != XmlPullParser.START_TAG) {
            throw IllegalStateException()
        }
        var depth = 1
        while (depth != 0) {
            when (parser.next()) {
                XmlPullParser.END_TAG -> depth--
                XmlPullParser.START_TAG -> depth++
            }
        }
    }
}
