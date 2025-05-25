package org.akira.otuskotlin.ads.biz.general

import org.akira.otuskotlin.ads.common.AdContext
import org.akira.otuskotlin.ads.common.models.AdState
import org.akira.otuskotlin.ads.cor.ICorChainDsl
import org.akira.otuskotlin.ads.cor.chain

fun ICorChainDsl<AdContext>.validation(block: ICorChainDsl<AdContext>.() -> Unit) = chain {
    block()
    title = "Валидация"

    on { state == AdState.RUNNING }
}