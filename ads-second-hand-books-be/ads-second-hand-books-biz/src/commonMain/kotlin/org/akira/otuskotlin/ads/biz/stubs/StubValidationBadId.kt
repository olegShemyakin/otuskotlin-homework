package org.akira.otuskotlin.ads.biz.stubs

import org.akira.otuskotlin.ads.common.AdContext
import org.akira.otuskotlin.ads.common.helpers.fail
import org.akira.otuskotlin.ads.common.models.AdError
import org.akira.otuskotlin.ads.common.models.AdState
import org.akira.otuskotlin.ads.common.stubs.AdsStubs
import org.akira.otuskotlin.ads.cor.ICorChainDsl
import org.akira.otuskotlin.ads.cor.worker

fun ICorChainDsl<AdContext>.stubValidationBadId(title: String) = worker {
    this.title = title
    this.description = "Ошибка валидации для идентификатора объявления"
    on { stubCase == AdsStubs.BAD_ID && state == AdState.RUNNING }
    handle {
        fail(
            AdError(
                group = "validation",
                code = "validation-id",
                field = "id",
                message = "Wrong id field"
            )
        )
    }
}