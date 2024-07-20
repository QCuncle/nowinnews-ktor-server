package me.cheney.nowinnews.data.repository.impl

import com.mongodb.BasicDBObject
import com.mongodb.client.model.Aggregates
import com.mongodb.client.model.Filters
import me.cheney.nowinnews.data.model.SiteConfig
import me.cheney.nowinnews.data.model.SubscriptionSite
import me.cheney.nowinnews.data.repository.SiteService
import org.litote.kmongo.coroutine.CoroutineDatabase

class SiteServiceImpl(
    private val database: CoroutineDatabase,
) : SiteService {

    private val siteConfigs = database.getCollection<SiteConfig>()

    override suspend fun getAllSubscriptionSites(): List<SubscriptionSite> {
        val pipeline = listOf(
            // Match stage if you have any filtering criteria, otherwise omit it
            // Aggregation stages to transform SiteConfig into Site
            Aggregates.project(
                BasicDBObject(
                    mapOf(
                        "code" to "\$code",
                        "name" to "\$name",
                        "icon" to "\$siteIconUrl",
                        "parentCode" to "\$parentCode"
                    )
                )
            ),
            Aggregates.sort(BasicDBObject("sort", 1)) // Sort by the 'sort' field
        )
        return siteConfigs.aggregate<SubscriptionSite>(pipeline).toList()
    }

    override suspend fun searchSubscriptionSitesByKeyword(keyword: String): List<SubscriptionSite> {
        val pipeline = listOf(
            // Match stage to filter documents where name contains the keyword (ignoring case) or parentCode equals the keyword
            Aggregates.match(
                Filters.or(
                    Filters.regex("name", "(?i).*${keyword}.*"),
                    Filters.eq("parentCode", keyword)
                )
            ),
            // Project stage to transform SiteConfig into Site
            Aggregates.project(
                BasicDBObject(
                    mapOf(
                        "code" to "\$code",
                        "name" to "\$name",
                        "icon" to "\$siteIconUrl",
                        "parentCode" to "\$parentCode",
                    )
                )
            ),
            // Sort stage to sort by the 'sort' field
            Aggregates.sort(BasicDBObject("sort", 1))
        )

        // Execute the aggregation pipeline
        return siteConfigs.aggregate<SubscriptionSite>(pipeline).toList()
    }

    override suspend fun getAllSiteConfigs(): List<SiteConfig> {
        return siteConfigs.find().toList()
    }

    override suspend fun createSiteConfigs(requestBean: List<SiteConfig>) {
        siteConfigs.insertMany(requestBean)
    }
}