package com.wizard.xiver

import android.content.Context
import android.util.Log
import kotlinx.serialization.SerializationException
import java.io.File
import java.io.IOException
import java.lang.IllegalStateException
import javax.xml.parsers.DocumentBuilderFactory

class SavedUtils {
    companion object{

        private fun getFile(context: Context): File{
            val file = File(context.filesDir, "saved_data.xml")
            createFileIfNotExists(file)
            return file
        }

        private fun createFileIfNotExists(file: File) {
            if (!file.exists()) {
                try {
                    file.createNewFile()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }

        private fun getFeed(context: Context) : Feed {
            val file = getFile(context)
            val xml = file.readText()
            val feed = try {
                if (isFileEmpty(file)) {
                    Feed(
                        id = "",
                        entry = emptyList<Entry>().toMutableList(),
                        itemsPerPage = 0,
                        link = Link(href = "", type = "", rel = ""),
                        startIndex = 0,
                        title = "",
                        totalResults = ResultInt(0),
                        updated = ""
                    )
                } else {
                    ArxivNetUtils.XmlUtil.xml().decodeFromString(Feed.serializer(), xml)
                }
            } catch (e: SerializationException) {
                Log.d("SavedUtils", e.toString())
                Feed(
                    id = "",
                    entry = emptyList<Entry>().toMutableList(),
                    itemsPerPage = 0,
                    link = Link(href = "", type = "", rel = ""),
                    startIndex = 0,
                    title = "",
                    totalResults = ResultInt(0),
                    updated = ""
                )
            } catch (e: IllegalStateException) {
                Log.e("SavedUtils", e.toString())
                Log.e("SavedUtils", xml)
                Feed(
                    id = "",
                    entry = emptyList<Entry>().toMutableList(),
                    itemsPerPage = 0,
                    link = Link(href = "", type = "", rel = ""),
                    startIndex = 0,
                    title = "",
                    totalResults = ResultInt(0),
                    updated = ""
                )
            }
            return feed
        }

        fun savePaper(paperEntity: Entry, context: Context){
            val feed = getSavedPapers(context)
            val updatedFeed = feed.copy(entry = feed.entry + paperEntity)
            val serializedFeed = ArxivNetUtils.XmlUtil.xml().encodeToString(Feed.serializer(), updatedFeed)
            getFile(context).writeText(serializedFeed)
        }

        fun deletePaper(paperEntity: Entry, context: Context){
            val feed = getSavedPapers(context)
            val updatedFeed = feed.copy(entry = feed.entry - paperEntity)
            val serializedFeed = ArxivNetUtils.XmlUtil.xml().encodeToString(Feed.serializer(), updatedFeed)
            getFile(context).writeText(serializedFeed)
        }

        private fun isFileEmpty(file: File): Boolean {
            return file.length() == 0L
        }

        fun getSavedPapers(context: Context): Feed {
            createFileIfNotExists(getFile(context))
            return getFeed(context)
        }

        fun isPaperSaved(paperEntity: Entry, context: Context): Boolean {
            val id = paperEntity.id
            if (!isFileEmpty(getFile(context))) {
                try {
                    val xmlDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                        .parse(getFile(context))
                    xmlDoc.documentElement.normalize()
                    val nodeList = xmlDoc.getElementsByTagName("id")

                    for (i in 0 until nodeList.length) {
                        val node = nodeList.item(i)
                        if (node.textContent == id) {
                            return true
                        }
                    }
                } catch (e: Exception) {
                    Log.e("SavedUtils", e.toString())
                }
            }
            return false
        }
    }
}