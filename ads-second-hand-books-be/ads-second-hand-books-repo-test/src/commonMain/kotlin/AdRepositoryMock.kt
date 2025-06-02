package org.akira.otuskotlin.ads.repo.tests

import org.akira.otuskotlin.ads.common.models.Ad
import org.akira.otuskotlin.ads.common.repo.*

class AdRepositoryMock(
    private val invokeCreateAd: (DbAdRequest) -> IDbAdResponse = { DEFAULT_AD_SUCCESS_EMPTY_MOCK },
    private val invokeReadAd: (DbAdIdRequest) -> IDbAdResponse = { DEFAULT_AD_SUCCESS_EMPTY_MOCK },
    private val invokeUpdateAd: (DbAdRequest) -> IDbAdResponse = { DEFAULT_AD_SUCCESS_EMPTY_MOCK },
    private val invokeDeleteAd: (DbAdIdRequest) -> IDbAdResponse = { DEFAULT_AD_SUCCESS_EMPTY_MOCK },
    private val invokeSearchAd: (DbAdFilterRequest) -> IDbAdsResponse = { DEFAULT_ADS_SUCCESS_EMPTY_MOCK }
) : IRepoAd {
    override suspend fun createAd(rq: DbAdRequest): IDbAdResponse = invokeCreateAd(rq)

    override suspend fun readAd(rq: DbAdIdRequest): IDbAdResponse = invokeReadAd(rq)

    override suspend fun updateAd(rq: DbAdRequest): IDbAdResponse = invokeUpdateAd(rq)

    override suspend fun deleteAd(rq: DbAdIdRequest): IDbAdResponse = invokeDeleteAd(rq)

    override suspend fun searchAd(rq: DbAdFilterRequest): IDbAdsResponse = invokeSearchAd(rq)

    companion object {
        val DEFAULT_AD_SUCCESS_EMPTY_MOCK = DbAdResponseOk(Ad())
        val DEFAULT_ADS_SUCCESS_EMPTY_MOCK = DbAdsResponseOk(emptyList())
    }
}