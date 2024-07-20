package me.cheney.nowinnews.base

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val code: Int,
    val message: String,
    val data: T,
)
