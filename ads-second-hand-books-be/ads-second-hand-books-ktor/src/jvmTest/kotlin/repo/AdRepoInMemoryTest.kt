package org.akira.otuskotlin.ads.app.ktor.repo

import AdAppSettings
import org.akira.otuskotlin.ads.api.models.AdRequestDebugMode
import org.akira.otuskotlin.ads.common.AdCorSettings
import org.akira.otuskotlin.ads.common.repo.IRepoAd
import org.akira.otuskotlin.ads.repo.common.AdRepoInitialized
import org.akira.otuskotlin.ads.repo.inmemory.AdRepoInMemory

class AdRepoInMemoryTest : AdRepoBaseTest() {
    override val workMode: AdRequestDebugMode = AdRequestDebugMode.TEST

    private fun adAppSettings(repo: IRepoAd) = AdAppSettings(
        corSettings = AdCorSettings(repoTest = repo)
    )

    override val appSettingsCreate: AdAppSettings = adAppSettings(
        repo = AdRepoInitialized(AdRepoInMemory(randomUuid = { uuidNew }))
    )

    override val appSettingsRead: AdAppSettings = adAppSettings(
        repo = AdRepoInitialized(
            AdRepoInMemory(randomUuid = { uuidNew }),
            initObjects = listOf(initAd)
        )
    )

    override val appSettingsUpdate: AdAppSettings = adAppSettings(
        repo = AdRepoInitialized(
            AdRepoInMemory(randomUuid = { uuidNew }),
            initObjects = listOf(initAd)
        )
    )

    override val appSettingsDelete: AdAppSettings = adAppSettings(
        repo = AdRepoInitialized(
            AdRepoInMemory(randomUuid = { uuidNew }),
            initObjects = listOf(initAd),
        )
    )
    override val appSettingsSearch: AdAppSettings = adAppSettings(
        repo = AdRepoInitialized(
            AdRepoInMemory(randomUuid = { uuidNew }),
            initObjects = listOf(initAd),
        )
    )
}