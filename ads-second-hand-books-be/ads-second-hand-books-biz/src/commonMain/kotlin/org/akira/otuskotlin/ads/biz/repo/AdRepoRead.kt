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

fun ICorChainDsl<AdContext>.repoRead(title: String) = worker {
    this.title = title
    description = "Чтение объявления из БД"
    on { state == AdState.RUNNING }
    handle {
        val request = DbAdIdRequest(adValidated)
        when(val result = adRepo.readAd(request)) {
            is DbAdResponseOk -> adRepoRead = result.data
            is DbAdResponseErr -> fail(result.errors)
            is DbAdResponseErrWithData -> {
                fail(result.errors)
                adRepoRead = result.data
            }
        }
    }
}