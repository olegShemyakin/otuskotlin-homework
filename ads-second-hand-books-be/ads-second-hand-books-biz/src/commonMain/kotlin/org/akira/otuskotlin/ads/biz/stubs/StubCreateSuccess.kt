package org.akira.otuskotlin.ads.biz.stubs

import com.ionspin.kotlin.bignum.decimal.BigDecimal
import org.akira.otuskotlin.ads.common.AdContext
import org.akira.otuskotlin.ads.common.AdCorSettings
import org.akira.otuskotlin.ads.common.models.AdState
import org.akira.otuskotlin.ads.common.models.AdType
import org.akira.otuskotlin.ads.common.stubs.AdsStubs
import org.akira.otuskotlin.ads.cor.ICorChainDsl
import org.akira.otuskotlin.ads.cor.worker
import org.akira.otuskotlin.ads.logging.common.LogLevel
import org.akira.otuskotlin.ads.stubs.AdStub

fun ICorChainDsl<AdContext>.stubCreateSuccess(title: String, corSettings: AdCorSettings) = worker {
    this.title = title
    this.description = "Успешное создание объявления"
    on { stubCase == AdsStubs.SUCCESS && state == AdState.RUNNING }
    val logger = corSettings.loggerProvider.logger("stubbOfferSuccess")
    handle {
        logger.doWithLogging(id = this.requestId.asString(), LogLevel.DEBUG) {
            state = AdState.FINISHING
            val stub = AdStub.prepareResult {
                adRequest.title.takeIf { it.isNotBlank() }?.also { this.title = it }
                adRequest.authors.takeIf { it.isNotBlank() }?.also { this.authors = it }
                adRequest.publishing.takeIf { it.isNotBlank() }?.also { this.publishing = it }
                adRequest.year.takeIf { it != 0 }?.also { this.year = it }
                adRequest.adType.takeIf { it != AdType.NONE }?.also { this.adType = it }
                adRequest.price.takeIf { it != BigDecimal.ZERO }?.also { this.price = it }
            }
            adResponse = stub
        }
    }
}