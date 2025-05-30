package org.akira.otuskotlin.ads.common

import org.akira.otuskotlin.ads.common.repo.IRepoAd
import org.akira.otuskotlin.ads.logging.common.AdLoggerProvider

data class AdCorSettings(
    val loggerProvider: AdLoggerProvider = AdLoggerProvider(),
    val repoStub: IRepoAd = IRepoAd.NONE,
    val repoTest: IRepoAd = IRepoAd.NONE,
    val repoProd: IRepoAd = IRepoAd.NONE
) {
    companion object {
        val NONE = AdCorSettings()
    }
}
