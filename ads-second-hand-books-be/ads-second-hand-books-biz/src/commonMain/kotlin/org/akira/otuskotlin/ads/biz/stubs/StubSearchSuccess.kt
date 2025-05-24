package org.akira.otuskotlin.ads.biz.stubs

import org.akira.otuskotlin.ads.common.AdContext
import org.akira.otuskotlin.ads.common.AdCorSettings
import org.akira.otuskotlin.ads.common.models.AdState
import org.akira.otuskotlin.ads.common.models.AdType
import org.akira.otuskotlin.ads.common.stubs.AdsStubs
import org.akira.otuskotlin.ads.cor.ICorChainDsl
import org.akira.otuskotlin.ads.cor.worker
import org.akira.otuskotlin.ads.logging.common.LogLevel
import org.akira.otuskotlin.ads.stubs.AdStub

fun ICorChainDsl<AdContext>.stubSearchSuccess(title: String, corSettings: AdCorSettings) = worker {
    this.title = title
    this.description = "Успешный поиск объявления"
    on { stubCase == AdsStubs.SUCCESS && state == AdState.RUNNING }
    val logger = corSettings.loggerProvider.logger("stubSearchSuccess")
    handle {
        logger.doWithLogging(id = this.requestId.asString(), LogLevel.DEBUG) {
            state = AdState.FINISHING
            adsResponse.addAll(AdStub.prepareSearchList(adFilterRequest.searchString, AdType.DEMAND))
        }
    }
}