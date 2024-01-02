package com.wizard.xiver

import kotlinx.serialization.Serializable
import nl.adaptivity.xmlutil.serialization.XmlCData
import nl.adaptivity.xmlutil.serialization.XmlSerialName
import nl.adaptivity.xmlutil.serialization.XmlValue

@Serializable
@XmlSerialName(value="feed", namespace = "http://www.w3.org/2005/Atom")
data class Feed(
    @XmlSerialName(value = "link")
    val link: Link2,
    @XmlCData
    val id: String,
    @XmlCData
    val title: String,
    @XmlCData
    val updated: String,
    @XmlSerialName(value = "totalResults", prefix = "opensearch", namespace = "http://a9.com/-/spec/opensearch/1.1/")
    @XmlCData
    val totalResults: ResultInt,
    @XmlSerialName(value = "startIndex", prefix = "opensearch", namespace = "http://a9.com/-/spec/opensearch/1.1/")
    @XmlCData
    val startIndex: Int,
    @XmlSerialName(value = "itemsPerPage", prefix = "opensearch", namespace = "http://a9.com/-/spec/opensearch/1.1/")
    @XmlCData
    val itemsPerPage: Int,
    @XmlSerialName("entry")
    val entry: List<Entry>
)

@Serializable
data class ResultInt (
    @XmlValue
    val totalResults: Int
)

@Serializable
data class Entry (
    @XmlCData
    val id: String,
    @XmlCData
    val updated: String,
    @XmlCData
    val published: String,
    @XmlCData
    val title: String,
    @XmlCData
    val summary: String,
    @XmlSerialName(value = "author")
    val author: List<Author>,
    @XmlSerialName(value = "comment", prefix = "arxiv")
    val comment: String?,
    @XmlSerialName(value = "doi", prefix = "arxiv", namespace = "http://arxiv.org/schemas/atom")
    val doi: String,
    @XmlSerialName(value = "journal_ref", prefix = "arxiv")
    val journalRef: String?,
    val link: Link,
    @XmlSerialName(value = "primary_category", prefix = "arxiv")
    val primaryCategory: Category?,
    val category: Category?
)

@Serializable
data class Author(
    @XmlSerialName(value = "name")
    val name: Name,
    @XmlSerialName(value = "affiliation", namespace = "http://arxiv.org/schemas/atom", prefix = "arxiv")
    val affiliation: String?
)


@Serializable
data class Name(
    @XmlValue
    val authorName: String
)

@Serializable
data class Category(
    val term: String
)

@Serializable
data class Link(
    val href: String,
    val title: String,
    val type: String,
    val rel: String
)

@Serializable
data class Link2(
    val href: String,
    val type: String,
    val rel: String
)
