package org.akira.otuskotlin.ads.app.ktor

import AdAppSettings
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.akira.otuskotlin.ads.app.ktor.plugins.initAppSettings

fun Application.module(
    appSettings: AdAppSettings = initAppSettings()
) {
    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        allowHeader(HttpHeaders.Authorization)
        allowHeader("MyCustomHeader")
        allowCredentials = true
        //TODO
        anyHost()
    }

    routing {
        get("/") {
            call.respondText("Hello, World!")
        }
    }
}