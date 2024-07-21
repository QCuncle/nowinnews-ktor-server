package me.cheney.nowinnews.util

import kotlinx.coroutines.*
import me.cheney.nowinnews.data.repository.NewsService
import me.cheney.nowinnews.data.repository.SiteService
import me.cheney.nowinnews.data.repository.XpathService
import java.util.concurrent.TimeUnit
import java.util.logging.Level
import java.util.logging.Logger

class XpathWorker(
    private val siteService: SiteService,
    private val newsService: NewsService,
    private val xpathService: XpathService,
) {
    private val scope = CoroutineScope(Dispatchers.IO)
    private val logger = Logger.getLogger(XpathWorker::class.java.name)

    fun start() {
        scope.launch {
            var errorCount = 0
            val maxErrorCount = 5

            while (isActive) {
                try {
                    val siteConfigs = siteService.getAllSiteConfigs()
                    val siteNewsList = xpathService.executeXpathProcess(siteConfigs)
                    println(siteNewsList.toString())
                    newsService.upsertSiteNews(siteNewsList)
                    // 重置错误计数器
                    errorCount = 0
                } catch (e: Exception) {
                    logger.log(Level.SEVERE, "An error occurred: ", e)
                    errorCount++

                    // 检查错误计数器是否超过最大值
                    if (errorCount >= maxErrorCount) {
                        logger.log(Level.SEVERE, "Maximum error count reached, stopping the worker.")
                        stop()
                    }
                }

                // 每分钟执行一次
                delay(TimeUnit.MINUTES.toMillis(5))
            }
        }
    }

    private fun stop() {
        scope.cancel()
    }
}