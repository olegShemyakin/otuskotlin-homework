package org.akira.otuskotlin.ads.biz.stubs

import org.akira.otuskotlin.ads.common.AdContext
import org.akira.otuskotlin.ads.common.helpers.fail
import org.akira.otuskotlin.ads.common.models.AdError
import org.akira.otuskotlin.ads.common.models.AdState
import org.akira.otuskotlin.ads.cor.ICorChainDsl
import org.akira.otuskotlin.ads.cor.worker

fun ICorChainDsl<AdContext>.stubNoCase(title: String) = worker {
    this.title = title
    this.description = "Не поддерживаемый тип заглушки"
    on { state == AdState.RUNNING }
    handle {
        fail(
            AdError(
                code = "validation",
                field = "stub",
                group = "validation",
                message = "Wrong stub case is requested: ${stubCase.name}"
            )
        )
    }
}