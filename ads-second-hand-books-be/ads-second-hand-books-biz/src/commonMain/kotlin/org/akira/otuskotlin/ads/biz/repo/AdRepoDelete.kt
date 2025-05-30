package org.akira.otuskotlin.ads.biz.repo

import org.akira.otuskotlin.ads.common.AdContext
import org.akira.otuskotlin.ads.common.helpers.fail
import org.akira.otuskotlin.ads.common.models.AdState
import org.akira.otuskotlin.ads.common.repo.DbAdIdRequest
import org.akira.otuskotlin.ads.common.repo.DbAdResponseErr
import org.akira.otuskotlin.ads.common.repo.DbAdResponseErrWithData
import org.akira.otuskotlin.ads.common.repo.DbAdResponseOk
import org.akira.otuskotlin.ads.cor.ICorChainDsl
import org.akira.otuskotlin.ads.cor.worker

fun ICorChainDsl<AdContext>.repoDelete(title: String) = worker {
    this.title = title
    description = "Удаление объявления из БД по ID"
    on { state == AdState.RUNNING }
    handle {
        val request = DbAdIdRequest(adRepoPrepare)
        when(val result = adRepo.deleteAd(request)) {
            is DbAdResponseOk -> adRepoDone = result.data
            is DbAdResponseErr -> {
                fail(result.errors)
                adRepoDone = adRepoRead
            }
            is DbAdResponseErrWithData -> {
                fail(result.errors)
                adRepoDone = result.data
            }
        }
    }
}