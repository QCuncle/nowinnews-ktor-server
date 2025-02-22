package me.cheney.nowinnews.data.repository.impl

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import me.cheney.nowinnews.base.BaseResponse
import me.cheney.nowinnews.data.model.SiteConfig
import me.cheney.nowinnews.data.model.SiteNews
import me.cheney.nowinnews.data.repository.XpathService

class XpathServiceImpl(
    private val client: HttpClient
) : XpathService {
    override suspend fun executeXpathProcess(siteConfig: SiteConfig): SiteNews? {
        try {
            val response = client.post {
                url("http://127.0.0.1:5000/xpath/process")
                contentType(ContentType.Application.Json)
                setBody(Json.encodeToString(siteConfig))
            }.body<BaseResponse<SiteNews?>>()
            return if (response.code == 0) response.data else null
        } catch (e: Throwable) {
            return null
        }
    }
}