package org.akira.otuskotlin.ads.common.repo.inmemory

import org.akira.otuskotlin.ads.common.models.AdType
import org.akira.otuskotlin.ads.common.repo.*
import org.akira.otuskotlin.ads.stubs.AdStub

class AdRepoStub : IRepoAd {
    override suspend fun createAd(rq: DbAdRequest): IDbAdResponse {
        return DbAdResponseOk(
            data = AdStub.get()
        )
    }

    override suspend fun readAd(rq: DbAdIdRequest): IDbAdResponse {
        return DbAdResponseOk(
            data = AdStub.get()
        )
    }

    override suspend fun updateAd(rq: DbAdRequest): IDbAdResponse {
        return DbAdResponseOk(
            data = AdStub.get()
        )
    }

    override suspend fun deleteAd(rq: DbAdIdRequest): IDbAdResponse {
        return DbAdResponseOk(
            data = AdStub.get()
        )
    }

    override suspend fun searchAd(rq: DbAdFilterRequest): IDbAdsResponse {
        return DbAdsResponseOk(
            data = AdStub.prepareSearchList(filter = "", AdType.DEMAND)
        )
    }
}