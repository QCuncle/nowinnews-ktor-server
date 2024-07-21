package me.cheney.nowinnews.data.repository

import me.cheney.nowinnews.data.model.SiteConfig
import me.cheney.nowinnews.data.model.SiteNews

interface XpathService {
    /**
     * 通过 [SiteConfig] 配置的规则对网站进行 xpath 查询
     * @param siteConfigs 网站配置集合
     */
    suspend fun executeXpathProcess(siteConfigs: List<SiteConfig>): List<SiteNews>
}