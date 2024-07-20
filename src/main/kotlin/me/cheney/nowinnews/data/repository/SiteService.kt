package me.cheney.nowinnews.data.repository

import me.cheney.nowinnews.data.model.SiteConfig
import me.cheney.nowinnews.data.model.SubscriptionSite

interface SiteService {
    /**
     * 获取订阅列表
     */
    suspend fun getAllSubscriptionSites(): List<SubscriptionSite>

    /**
     * 通过关键词搜索订阅列表
     * @param keyword 模糊匹配-[SubscriptionSite.name] 精确匹配-[SubscriptionSite.parentCode]
     */
    suspend fun searchSubscriptionSitesByKeyword(keyword: String): List<SubscriptionSite>

    /**
     * 获取站点配置
     */
    suspend fun getAllSiteConfigs(): List<SiteConfig>

    /**
     * 创建站点配置
     */
    suspend fun createSiteConfigs(requestBean: List<SiteConfig>)

}