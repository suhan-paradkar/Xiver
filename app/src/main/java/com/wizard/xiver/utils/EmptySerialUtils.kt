package com.wizard.xiver.utils

import com.wizard.xiver.Author
import com.wizard.xiver.Category
import com.wizard.xiver.Entry
import com.wizard.xiver.Feed
import com.wizard.xiver.Link
import com.wizard.xiver.Link2
import com.wizard.xiver.Name
import com.wizard.xiver.ResultInt

class EmptySerialUtils {
    companion object{
        val EmptyFeed = Feed(
            id = "",
            entry = emptyList<Entry>().toMutableList(),
            itemsPerPage = 0,
            link = Link2(type = "", rel = "", href = ""),
            startIndex = 0,
            title = "",
            totalResults = ResultInt(0),
            updated = ""
        )

        private val EmptyAuthorList = List(size = 3, init = { Author(name = Name(it.toString()), affiliation = "") })

        val EmptyEntry = Entry(
            id = "",
            title = "Title",
            summary = "A Large Summary",
            author = EmptyAuthorList,
            link = Link(type = "", rel = "", title = "", href = ""),
            published = "00/00/0000",
            updated = "11/11/1111",
            comment = "A super looong comment",
            doi = "",
            category = Category(term = ""),
            journalRef = "",
            primaryCategory = Category(term = "")
        )
    }
}