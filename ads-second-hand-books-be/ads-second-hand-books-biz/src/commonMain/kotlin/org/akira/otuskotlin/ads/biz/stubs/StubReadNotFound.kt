package org.akira.otuskotlin.ads.biz.stubs

import org.akira.otuskotlin.ads.common.AdContext
import org.akira.otuskotlin.ads.common.helpers.fail
import org.akira.otuskotlin.ads.common.models.AdError
import org.akira.otuskotlin.ads.common.models.AdState
import org.akira.otuskotlin.ads.common.stubs.AdsStubs
import org.akira.otuskotlin.ads.cor.ICorChainDsl
import org.akira.otuskotlin.ads.cor.worker

fun ICorChainDsl<AdContext>.stubReadNotFound(title: String) = worker {
    this.title = title
    this.description = "Не смогли найти объявление"
    on { stubCase == AdsStubs.NOT_FOUND && state == AdState.RUNNING }
    handle {
        fail(
            AdError(
                group = "not found",
                code = "not found",
                message = "not found id: ${this.adRequest.id}"
            )
        )
    }
}