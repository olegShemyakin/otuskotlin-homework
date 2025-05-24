package org.akira.otuskotlin.ads.biz.stubs

import org.akira.otuskotlin.ads.common.AdContext
import org.akira.otuskotlin.ads.common.helpers.fail
import org.akira.otuskotlin.ads.common.models.AdError
import org.akira.otuskotlin.ads.common.models.AdState
import org.akira.otuskotlin.ads.common.stubs.AdsStubs
import org.akira.otuskotlin.ads.cor.ICorChainDsl
import org.akira.otuskotlin.ads.cor.worker

fun ICorChainDsl<AdContext>.stubDbError(title: String) = worker {
    this.title = title
    this.description = "Ошибка базы данных"
    on { stubCase == AdsStubs.DB_ERROR && state == AdState.RUNNING }
    handle {
        fail(
            AdError(
                group = "internal",
                code = "internal-db",
                message = "Internal error"
            )
        )
    }
}