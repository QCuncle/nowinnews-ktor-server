package me.cheney.nowinnews.routes

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import me.cheney.nowinnews.base.respondSuccess
import me.cheney.nowinnews.base.respondThrowable
import me.cheney.nowinnews.data.model.SiteConfig
import me.cheney.nowinnews.data.repository.SiteService
import me.cheney.nowinnews.data.requests.SearchRequest
import org.koin.ktor.ext.inject

fun Route.registerSiteService() {
    val siteService by inject<SiteService>()

    // 订阅
    route("/subscription") {
        // 获取所有订阅网站
        get("/getAllSubscriptionSites") {
            try {
                val subscriptions = siteService.getAllSubscriptionSites()
                call.respondSuccess(subscriptions, "查询成功")
            } catch (e: Exception) {
                call.respondThrowable(e)
            }
        }

        // 通过关键词搜索订阅网站
        get("/searchSubscriptionSitesByKeyword") {
            try {
                val keyword = call.receive<SearchRequest>().keyword
                val subscriptions = siteService.searchSubscriptionSitesByKeyword(keyword)
                call.respondSuccess(subscriptions, "搜索成功")
            } catch (e: Exception) {
                call.respondThrowable(e)
            }
        }
    }

    // 网站配置
    route("/siteConfigs") {
        // 获取所有配置信息
        get("/getAllSiteConfigs") {
            try {
                val sites = siteService.getAllSiteConfigs()
                call.respondSuccess(sites, "查询成功")
            } catch (e: Exception) {
                call.respondThrowable(e)
            }
        }

        // 创建配置
        post("/createSiteConfigs") {
            try {
                val configs = call.receive<List<SiteConfig>>()
                siteService.createSiteConfigs(configs)
                call.respondSuccess(emptyList<String>(), "新建站点成功")
            } catch (e: Exception) {
                call.respondThrowable(e)
            }
        }
    }

}