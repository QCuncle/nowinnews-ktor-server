package me.cheney.nowinnews.data.requests

import kotlinx.serialization.Serializable

@Serializable
data class NewsDetailRequest(val code: String)