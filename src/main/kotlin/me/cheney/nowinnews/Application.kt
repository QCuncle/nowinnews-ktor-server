package me.cheney.nowinnews

import io.ktor.server.application.*
import me.cheney.nowinnews.data.repository.SiteService
import me.cheney.nowinnews.data.repository.impl.SiteServiceImpl
import me.cheney.nowinnews.plugins.configureMonitoring
import me.cheney.nowinnews.plugins.configureRouting
import me.cheney.nowinnews.plugins.configureSerialization
import org.koin.core.context.startKoin
import org.koin.dsl.module
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
}

val appModule = module {
    single { KMongo.createClient().coroutine }
    single {
        val client: CoroutineClient = get()
        client.getDatabase("AppDatabase")
    }
    single<SiteService> { SiteServiceImpl(get()) }
}
