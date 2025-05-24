package org.akira.otuskotlin.ads.biz.stubs

import org.akira.otuskotlin.ads.common.AdContext
import org.akira.otuskotlin.ads.common.models.AdState
import org.akira.otuskotlin.ads.common.models.AdWorkMode
import org.akira.otuskotlin.ads.cor.ICorChainDsl
import org.akira.otuskotlin.ads.cor.chain

fun ICorChainDsl<AdContext>.stubs(title: String, block: ICorChainDsl<AdContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { workMode == AdWorkMode.STUB && state == AdState.RUNNING }
}