package org.akira.otuskotlin.ads.biz.stubs

import org.akira.otuskotlin.ads.common.AdContext
import org.akira.otuskotlin.ads.common.helpers.fail
import org.akira.otuskotlin.ads.common.models.AdError
import org.akira.otuskotlin.ads.common.models.AdState
import org.akira.otuskotlin.ads.common.stubs.AdsStubs
import org.akira.otuskotlin.ads.cor.ICorChainDsl
import org.akira.otuskotlin.ads.cor.worker

fun ICorChainDsl<AdContext>.stubCannotDelete(title: String) = worker {
    this.title = title
    this.description = "Не смогли удалить объявление"
    on { stubCase == AdsStubs.CANNOT_DELETE && state == AdState.RUNNING }
    handle {
        fail(
            AdError(
                group = "delete",
                code = "delete",
                message = "can not delete id: ${this.adRequest.id}"
            )
        )
    }
}