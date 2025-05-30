package org.akira.otuskotlin.ads.app.ktor.plugins

import AdAppSettings
import io.ktor.server.application.*
import org.akira.otuskotlin.ads.biz.AdProcessor
import org.akira.otuskotlin.ads.common.AdCorSettings
import org.akira.otuskotlin.ads.common.repo.inmemory.AdRepoStub
import org.akira.otuskotlin.ads.repo.inmemory.AdRepoInMemory

fun Application.initAppSettings() : AdAppSettings {
    val corSettings = AdCorSettings(
        loggerProvider = getLoggerProviderConf(),
        repoTest = AdRepoInMemory(),
        repoProd = AdRepoInMemory(),
        repoStub = AdRepoStub()
    )
    return AdAppSettings(
        appUrls = environment.config.propertyOrNull("ktor.urls")?.getList() ?: emptyList(),
        corSettings = corSettings,
        processor = AdProcessor(corSettings)
    )
}

