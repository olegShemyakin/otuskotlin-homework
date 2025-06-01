package org.akira.otuskotlin.ads.biz.stubs

import org.akira.otuskotlin.ads.common.AdContext
import org.akira.otuskotlin.ads.common.AdCorSettings
import org.akira.otuskotlin.ads.common.models.AdState
import org.akira.otuskotlin.ads.common.stubs.AdsStubs
import org.akira.otuskotlin.ads.cor.ICorChainDsl
import org.akira.otuskotlin.ads.cor.worker
import org.akira.otuskotlin.ads.logging.common.LogLevel
import org.akira.otuskotlin.ads.stubs.AdStub

fun ICorChainDsl<AdContext>.stubDeleteSuccess(title: String, corSettings: AdCorSettings) = worker {
    this.title = title
    this.description = "Успешное удаление объявления"
    on { stubCase == AdsStubs.SUCCESS && state == AdState.RUNNING }
    val logger = corSettings.loggerProvider.logger("stubOffersSuccess")
    handle {
        logger.doWithLogging(id = this.requestId.asString(), LogLevel.DEBUG) {
            state = AdState.FINISHING
            val stub = AdStub.prepareResult {
                adRequest.title.takeIf { it.isNotBlank() }?.also { this.title = it }
            }
            adResponse = stub
        }
    }
}