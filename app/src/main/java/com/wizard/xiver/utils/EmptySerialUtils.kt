package com.wizard.xiver.utils

import com.wizard.xiver.Author
import com.wizard.xiver.Category
import com.wizard.xiver.Entry
import com.wizard.xiver.Feed
import com.wizard.xiver.Link
import com.wizard.xiver.Name
import com.wizard.xiver.ResultInt

class EmptySerialUtils {
    companion object{
        val EmptyFeed = Feed(
            id = "",
            entry = emptyList<Entry>().toMutableList(),
            itemsPerPage = 0,
            link = Link(href = "", type = "", rel = ""),
            startIndex = 0,
            title = "",
            totalResults = ResultInt(0),
            updated = ""
        )


        private val EmptyLinkList = List(size = 3, init = {(Link("", it.toString(), ""))})
        private val EmptyAuthorList = List(size = 3, init = { Author(name = Name(it.toString())) })

        val EmptyEntry = Entry(
            id = "",
            title = "Title",
            summary = "A Large Summary",
            author = EmptyAuthorList,
            link = EmptyLinkList,
            published = "00/00/0000",
            updated = "11/11/1111",
            comment = "A super looong comment",
            category = Category(term = ""),
            journalRef = "",
            primaryCategory = Category(term = "")
        )
    }
}