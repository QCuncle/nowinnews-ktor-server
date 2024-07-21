package me.cheney.nowinnews.data.repository

import me.cheney.nowinnews.data.model.SiteNews

interface NewsService {
    /**
     * 查询所有订阅的新闻
     * @param subscriptions 订阅的站点 [SiteNews.siteCode] 列表
     */
    suspend fun getSubscriptionNews(subscriptions: List<String>): List<SiteNews>

    /**
     * 查询指定的站点新闻
     * @param code 网站 code [SiteNews.siteCode]
     */
    suspend fun getNewsBySiteCode(code: String): SiteNews?

    /**
     * 插入/更新 热榜新闻数据
     */
    suspend fun upsertSiteNews(siteNewsList: List<SiteNews>)
}