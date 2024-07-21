package me.cheney.nowinnews.routes

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import me.cheney.nowinnews.base.respondSuccess
import me.cheney.nowinnews.base.respondThrowable
import me.cheney.nowinnews.data.repository.NewsService
import me.cheney.nowinnews.data.requests.NewsDetailRequest
import me.cheney.nowinnews.data.requests.NewsRequest
import org.koin.ktor.ext.inject

fun Route.registerNewsService() {
    val newsService by inject<NewsService>()

    route("/news") {
        get("/getAllSites") {
            try {
                val subscriptions = call.receive<NewsRequest>().subscriptions
                val news = newsService.getSubscriptionNews(subscriptions)
                call.respondSuccess(news)
            } catch (e: Exception) {
                call.respondThrowable(e)
            }
        }

        get("/getOneSite") {
            try {
                val siteCode = call.receive<NewsDetailRequest>().code
                val news = newsService.getNewsBySiteCode(siteCode)
                if (news == null) call.respondSuccess(emptyList<String>())
                else call.respondSuccess(news)
            } catch (e: Exception) {
                call.respondThrowable(e)
            }
        }
    }
}