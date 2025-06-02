package org.akira.otuskotlin.ads.biz.repo

import org.akira.otuskotlin.ads.common.AdContext
import org.akira.otuskotlin.ads.common.models.AdState
import org.akira.otuskotlin.ads.cor.ICorChainDsl
import org.akira.otuskotlin.ads.cor.worker
import org.akira.otuskotlin.ads.stubs.AdStub

fun ICorChainDsl<AdContext>.repoPrepareCreate(title: String) = worker {
    this.title = title
    description = "Подготовка объекта к сохранению в БД"
    on { state == AdState.RUNNING }
    handle {
        adRepoPrepare = adValidated.deepCopy()
        //Todo реазировать позднее
        adRepoPrepare.ownerId = AdStub.get().ownerId
    }
}