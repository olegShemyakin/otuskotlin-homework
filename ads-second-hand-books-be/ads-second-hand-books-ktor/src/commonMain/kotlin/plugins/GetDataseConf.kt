package org.akira.otuskotlin.ads.app.ktor.plugins

import io.ktor.server.application.*
import org.akira.otuskotlin.ads.common.repo.IRepoAd
import org.akira.otuskotlin.ads.repo.inmemory.AdRepoInMemory
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

expect fun Application.getDatabaseConf(type: AdDbType): IRepoAd

enum class AdDbType(val confName: String) {
    PROD("prod"), TEST("test")
}

fun Application.initInMemory(): IRepoAd {
    val ttlSetting = environment.config.propertyOrNull("db.prod")?.getString()?.let {
        Duration.parse(it)
    }
    return AdRepoInMemory(ttl = ttlSetting ?: 10.minutes)
}