package me.cheney.nowinnews.base

val Throwable.messageNotNull get() = this.message ?: "InternalServerError"