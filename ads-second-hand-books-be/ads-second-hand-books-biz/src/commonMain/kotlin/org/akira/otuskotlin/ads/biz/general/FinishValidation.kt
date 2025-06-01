package org.akira.otuskotlin.ads.biz.general

import org.akira.otuskotlin.ads.common.AdContext
import org.akira.otuskotlin.ads.common.models.AdState
import org.akira.otuskotlin.ads.cor.ICorChainDsl
import org.akira.otuskotlin.ads.cor.worker

fun ICorChainDsl<AdContext>.finishAdValidation(title: String) = worker {
    this.title = title
    on { state == AdState.RUNNING }
    handle {
        adValidated = adValidating
    }
}

fun ICorChainDsl<AdContext>.finishAdFilterValidation(title: String) = worker {
    this.title = title
    on { state == AdState.RUNNING }
    handle {
        adFilterValidated = adFilterValidating
    }
}