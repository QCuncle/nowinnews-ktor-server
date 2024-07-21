package me.cheney.nowinnews.data.model

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId

@Serializable
data class SiteNews(
    @BsonId
    val siteCode: String,
    val siteName: String,
    val siteIconUrl: String,
    val updateTimestamp: Long,
    val data: List<NewsItem>
)