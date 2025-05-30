package org.akira.otuskotlin.ads.biz.repo

import org.akira.otuskotlin.ads.common.AdContext
import org.akira.otuskotlin.ads.common.models.AdState
import org.akira.otuskotlin.ads.cor.ICorChainDsl
import org.akira.otuskotlin.ads.cor.worker

fun ICorChainDsl<AdContext>.repoPrepareUpdate(title: String) = worker {
    this.title = title
    description = "Готовим данные к обновлению в БД путем замены сохраненых значений новыми"
    on { state == AdState.RUNNING }
    handle {
        adRepoPrepare = adRepoRead.deepCopy().apply {
            this.title = adValidated.title
            authors = adValidated.authors
            publishing = adValidated.publishing
            year = adValidated.year
            adType = adValidated.adType
            price = adValidated.price
        }
    }
}