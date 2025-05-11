package org.akira.otuskotlin.ads.app.ktor.plugins

import AdAppSettings
import io.ktor.server.application.*
import org.akira.otuskotlin.ads.biz.AdProcessor
import org.akira.otuskotlin.ads.common.AdCorSettings

fun Application.initAppSettings() : AdAppSettings {
    val corSettings = AdCorSettings(
        loggerProvider = getLoggerProviderConf()
    )
    return AdAppSettings(
        appUrls = environment.config.propertyOrNull("ktor.urls")?.getList() ?: emptyList(),
        corSettings = corSettings,
        processor = AdProcessor(corSettings)
    )
}

