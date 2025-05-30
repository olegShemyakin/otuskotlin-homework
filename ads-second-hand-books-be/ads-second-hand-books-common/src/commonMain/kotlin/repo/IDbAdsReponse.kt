package org.akira.otuskotlin.ads.common.repo

import org.akira.otuskotlin.ads.common.models.Ad
import org.akira.otuskotlin.ads.common.models.AdError

sealed interface IDbAdsResponse : IDbResponse<List<Ad>>

data class DbAdsResponseOk(val data: List<Ad>) : IDbAdsResponse

data class DbAdsResponseErr(val errors: List<AdError> = emptyList()) : IDbAdsResponse {
    constructor(err: AdError): this(listOf(err))
}