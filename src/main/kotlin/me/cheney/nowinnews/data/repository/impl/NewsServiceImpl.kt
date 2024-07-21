package me.cheney.nowinnews.data.repository.impl

import me.cheney.nowinnews.data.model.SiteNews
import me.cheney.nowinnews.data.repository.NewsService
import org.litote.kmongo.*
import org.litote.kmongo.coroutine.CoroutineDatabase

class NewsServiceImpl(
    private val database: CoroutineDatabase
) : NewsService {

    private val siteNews = database.getCollection<SiteNews>()

    override suspend fun getSubscriptionNews(subscriptions: List<String>): List<SiteNews> {
        return siteNews.find(SiteNews::siteCode `in` subscriptions).toList()
    }

    override suspend fun getNewsBySiteCode(code: String): SiteNews? {
        return siteNews.find(SiteNews::siteCode eq code).first()
    }

    override suspend fun upsertSiteNews(siteNewsList: List<SiteNews>) {
        siteNewsList.forEach { siteNewsItem ->
            // Define the filter to match the document based on siteCode
            val filter = SiteNews::siteCode eq siteNewsItem.siteCode

            // Define the update operation
            val updateOperation = set(
                SiteNews::siteName setTo siteNewsItem.siteName,
                SiteNews::siteIconUrl setTo siteNewsItem.siteIconUrl,
                SiteNews::updateTimestamp setTo siteNewsItem.updateTimestamp,
                SiteNews::data setTo siteNewsItem.data
            )

            // Perform the upsert operation
            siteNews.updateOne(
                filter,
                updateOperation,
                upsert()
            )
        }
    }
}