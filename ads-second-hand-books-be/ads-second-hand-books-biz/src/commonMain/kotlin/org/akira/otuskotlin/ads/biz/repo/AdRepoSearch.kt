package org.akira.otuskotlin.ads.biz.repo

import org.akira.otuskotlin.ads.common.AdContext
import org.akira.otuskotlin.ads.common.helpers.fail
import org.akira.otuskotlin.ads.common.models.AdState
import org.akira.otuskotlin.ads.common.repo.DbAdFilterRequest
import org.akira.otuskotlin.ads.common.repo.DbAdsResponseErr
import org.akira.otuskotlin.ads.common.repo.DbAdsResponseOk
import org.akira.otuskotlin.ads.cor.ICorChainDsl
import org.akira.otuskotlin.ads.cor.worker

fun ICorChainDsl<AdContext>.repoSearch(title: String) = worker {
    this.title = title
    description = "Поиск объявлений в БД по фильтру"
    on { state == AdState.RUNNING }
    handle {
        val request = DbAdFilterRequest(
            titleFilter = adFilterValidated.searchString,
            ownerId = adFilterValidated.ownerId,
            adType = adFilterValidated.adType
        )
        when(val result = adRepo.searchAd(request)) {
            is DbAdsResponseOk -> result.data.toMutableList()
            is DbAdsResponseErr -> fail(result.errors)
        }
    }
}