package me.cheney.nowinnews.data.model

import kotlinx.serialization.Serializable

@Serializable
data class NewsItem(
    val siteCode: String,
    val siteName: String,
    val position: Int,
    val title: String,
    val url: String,
    val imageUrl: String?,
    val popularity: String,
)