package org.akira.otuskotlin.ads.biz.general

import org.akira.otuskotlin.ads.common.AdContext
import org.akira.otuskotlin.ads.common.models.AdCommand
import org.akira.otuskotlin.ads.common.models.AdState
import org.akira.otuskotlin.ads.cor.ICorChainDsl
import org.akira.otuskotlin.ads.cor.chain

fun ICorChainDsl<AdContext>.operation(
    title: String,
    command: AdCommand,
    block: ICorChainDsl<AdContext>.() -> Unit
) = chain {
    block()
    this.title = title
    on { this.command == command && state == AdState.RUNNING }
}