package me.cheney.nowinnews.data.requests

import kotlinx.serialization.Serializable

@Serializable
data class NewsRequest(val subscriptions: List<String>)
