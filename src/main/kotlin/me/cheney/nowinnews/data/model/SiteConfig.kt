package me.cheney.nowinnews.data.model

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId

@Serializable
data class SiteConfig(
    val articleXpath: ArticleXpath,
    val host: String,
    @BsonId
    val code: String,
    val name: String,
    val parentCode: String?,
    val siteIconUrl: String,
    val siteUrl: String,
    val sort: Int,
)

@Serializable
data class ArticleXpath(
    val imageUrl: String,
    val parameter: String,
    val popularity: String,
    val position: String,
    val title: String,
    val url: String,
)