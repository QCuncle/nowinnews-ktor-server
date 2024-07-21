package me.cheney.nowinnews

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import kotlinx.serialization.json.Json
import me.cheney.nowinnews.data.repository.NewsService
import me.cheney.nowinnews.data.repository.SiteService
import me.cheney.nowinnews.data.repository.XpathService
import me.cheney.nowinnews.data.repository.impl.NewsServiceImpl
import me.cheney.nowinnews.data.repository.impl.SiteServiceImpl
import me.cheney.nowinnews.data.repository.impl.XpathServiceImpl
import me.cheney.nowinnews.plugins.configureMonitoring
import me.cheney.nowinnews.plugins.configureRouting
import me.cheney.nowinnews.plugins.configureSerialization
import me.cheney.nowinnews.util.XpathWorker
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.inject
import org.litote.kmongo.coroutine.CoroutineClient
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureMonitoring()
    configureRouting()

    startKoin {
        modules(appModule)
    }
    startup()
}

val appModule = module {
    single {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
            // 配置请求超时
            install(HttpTimeout) {
                requestTimeoutMillis = 120000 // 设置请求超时时间为 120 秒
            }
        }
    }

    single { KMongo.createClient().coroutine }
    single {
        val client: CoroutineClient = get()
        client.getDatabase("AppDatabase")
    }

    single<SiteService> { SiteServiceImpl(get()) }
    single<NewsService> { NewsServiceImpl(get()) }
    single<XpathService> { XpathServiceImpl(get()) }
    single { XpathWorker(get(), get(), get()) }
}

private fun startup() {
    val xpathWorker by inject<XpathWorker>(XpathWorker::class.java)
    xpathWorker.start()
}
