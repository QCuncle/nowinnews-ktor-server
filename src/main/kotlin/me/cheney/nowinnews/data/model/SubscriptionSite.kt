package me.cheney.nowinnews.data.model

import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId

@Serializable
data class SubscriptionSite(
    @BsonId
    val code: String,
    val name: String,
    val icon: String,
    val parentCode: String?,
)