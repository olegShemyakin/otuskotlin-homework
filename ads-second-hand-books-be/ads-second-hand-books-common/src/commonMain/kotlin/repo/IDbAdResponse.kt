package org.akira.otuskotlin.ads.common.repo

import org.akira.otuskotlin.ads.common.models.Ad
import org.akira.otuskotlin.ads.common.models.AdError

sealed interface IDbAdResponse : IDbResponse<Ad>

data class DbAdResponseOk(val data: Ad) : IDbAdResponse

data class DbAdResponseErr(val errors: List<AdError> = emptyList()) : IDbAdResponse {
    constructor(err: AdError): this(listOf(err))
}

data class DbAdResponseErrWithData(
    val data: Ad,
    val errors: List<AdError> = emptyList()
) : IDbAdResponse {
    constructor(ad: Ad, err: AdError): this(ad, listOf(err))
}