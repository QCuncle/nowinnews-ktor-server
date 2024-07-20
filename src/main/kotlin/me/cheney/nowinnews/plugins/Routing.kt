package me.cheney.nowinnews.plugins

import io.ktor.server.application.*
import io.ktor.server.routing.*
import me.cheney.nowinnews.routes.registerSiteService

fun Application.configureRouting() {
    routing {
        registerSiteService()
    }
}
