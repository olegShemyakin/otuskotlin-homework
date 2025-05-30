package org.akira.otuskotlin.ads.biz.repo

import org.akira.otuskotlin.ads.common.AdContext
import org.akira.otuskotlin.ads.common.helpers.fail
import org.akira.otuskotlin.ads.common.models.AdState
import org.akira.otuskotlin.ads.common.repo.DbAdRequest
import org.akira.otuskotlin.ads.common.repo.DbAdResponseErr
import org.akira.otuskotlin.ads.common.repo.DbAdResponseErrWithData
import org.akira.otuskotlin.ads.common.repo.DbAdResponseOk
import org.akira.otuskotlin.ads.cor.ICorChainDsl
import org.akira.otuskotlin.ads.cor.worker

fun ICorChainDsl<AdContext>.repoCreate(title: String) = worker {
    this.title = title
    description = "Добавление обявления в БД"
    on { state == AdState.RUNNING }
    handle {
        val request = DbAdRequest(adRepoPrepare)
        when(val result = adRepo.createAd(request)) {
            is DbAdResponseOk -> adRepoDone = result.data
            is DbAdResponseErr -> fail(result.errors)
            is DbAdResponseErrWithData -> fail(result.errors)
        }
    }
}