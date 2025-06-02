package org.akira.otuskotlin.ads.biz.repo

import org.akira.otuskotlin.ads.common.AdContext
import org.akira.otuskotlin.ads.common.models.AdState
import org.akira.otuskotlin.ads.cor.ICorChainDsl
import org.akira.otuskotlin.ads.cor.worker

fun ICorChainDsl<AdContext>.repoPrepareDelete(title: String) = worker {
    this.title = title
    description = "Подготовка объекта к удалению из БД"
    on { state == AdState.RUNNING }
    handle {
        adRepoPrepare = adValidated.deepCopy()
    }
}