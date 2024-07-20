package me.cheney.nowinnews.base

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

suspend inline fun <reified T> ApplicationCall.respondSuccess(
    data: T,
    message: String = "successful",
) {
    this.respond(
        HttpStatusCode.OK,
        BaseResponse(0, message, data)
    )
}

suspend inline fun ApplicationCall.respondThrowable(e: Throwable) {
    this.respond(
        HttpStatusCode.OK,
        BaseResponse<List<String>>(-1, e.messageNotNull, emptyList())
    )
}