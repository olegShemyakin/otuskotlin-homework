package org.akira.otuskotlin.ads.app.ktor

import AdAppSettings
import io.ktor.serialization.jackson.*
import io.ktor.server.application.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.plugins.cachingheaders.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.routing.*
import org.akira.otuskotlin.ads.api.apiMapper
import org.akira.otuskotlin.ads.app.ktor.module
import org.akira.otuskotlin.ads.app.ktor.plugins.initAppSettings
import org.slf4j.event.Level
import v1.v1Ad

//необходим файл конфигурации application.conf
//fun main(args: Array<String>): Unit = io.ktor.server.cio.EngineMain.main(args)

fun Application.moduleJvm(
    appSettings: AdAppSettings = initAppSettings()
) {
    install(CachingHeaders)
    install(DefaultHeaders)
    install(AutoHeadResponse)
    install(CallLogging) {
        level = Level.INFO
    }
    module(appSettings)

    routing {
        route("v1") {
            install(ContentNegotiation) {
                jackson {
                    setConfig(apiMapper.serializationConfig)
                    setConfig(apiMapper.deserializationConfig)
                }
            }
            v1Ad(appSettings)
        }
    }
}