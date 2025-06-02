package org.akira.otuskotlin.ads.mappers

import com.ionspin.kotlin.bignum.decimal.BigDecimal
import org.akira.otuskotlin.ads.api.models.*
import org.akira.otuskotlin.ads.common.AdContext
import org.akira.otuskotlin.ads.common.models.*
import org.akira.otuskotlin.ads.common.stubs.AdsStubs
import org.akira.otuskotlin.ads.mappers.exceptions.UnknownRequestClass

fun AdContext.fromTransport(request: IRequest) = when (request) {
    is AdCreateRequest -> fromTransport(request)
    is AdReadRequest -> fromTransport(request)
    is AdUpdateRequest -> fromTransport(request)
    is AdDeleteRequest -> fromTransport(request)
    is AdSearchRequest -> fromTransport(request)
    else -> throw UnknownRequestClass(request.javaClass)
}

private fun String?.toAdId() = this?.let { AdId(it) } ?: AdId.NONE
private fun String?.toAdLock() = this?.let { AdLock(it) } ?: AdLock.NONE

private fun AdDebug?.toWorkMode(): AdWorkMode = when (this?.mode) {
    AdRequestDebugMode.PROD -> AdWorkMode.PROD
    AdRequestDebugMode.TEST -> AdWorkMode.TEST
    AdRequestDebugMode.STUB -> AdWorkMode.STUB
    null -> AdWorkMode.PROD
}

private fun AdDebug?.toStubCase(): AdsStubs = when (this?.stub) {
    AdRequestDebugStubs.SUCCESS -> AdsStubs.SUCCESS
    AdRequestDebugStubs.NOT_FOUND -> AdsStubs.NOT_FOUND
    AdRequestDebugStubs.BAD_ID -> AdsStubs.BAD_ID
    AdRequestDebugStubs.BAD_TITLE -> AdsStubs.BAD_TITLE
    AdRequestDebugStubs.BAD_AUTHORS -> AdsStubs.BAD_AUTHORS
    AdRequestDebugStubs.BAD_PRICE -> AdsStubs.BAD_PRICE
    AdRequestDebugStubs.CANNOT_DELETE -> AdsStubs.CANNOT_DELETE
    AdRequestDebugStubs.BAD_SEARCH_STRING -> AdsStubs.BAD_SEARCH_STRING
    null -> AdsStubs.NONE
}

fun AdContext.fromTransport(request: AdCreateRequest) {
    command = AdCommand.CREATE
    adRequest = request.ad?.toInternal() ?: Ad()
    workMode = request.debug.toWorkMode()
    stubCase = request.debug.toStubCase()
}

fun AdContext.fromTransport(request: AdReadRequest) {
    command = AdCommand.READ
    adRequest = request.ad.toInternal()
    workMode = request.debug.toWorkMode()
    stubCase = request.debug.toStubCase()
}

fun AdContext.fromTransport(request: AdUpdateRequest) {
    command = AdCommand.UPDATE
    adRequest = request.ad?.toInternal() ?: Ad()
    workMode = request.debug.toWorkMode()
    stubCase = request.debug.toStubCase()
}

fun AdContext.fromTransport(request: AdDeleteRequest) {
    command = AdCommand.DELETE
    adRequest = request.ad.toInternal()
    workMode = request.debug.toWorkMode()
    stubCase = request.debug.toStubCase()
}

fun AdContext.fromTransport(request: AdSearchRequest) {
    command = AdCommand.SEARCH
    adFilterRequest = request.adFilter.toInternal()
    workMode = request.debug.toWorkMode()
    stubCase = request.debug.toStubCase()
}

private fun AdReadObject?.toInternal(): Ad = if (this != null) {
    Ad(id = id.toAdId())
} else {
    Ad()
}

private fun AdDeleteObject?.toInternal(): Ad = if (this != null) {
    Ad(
        id = id.toAdId(),
        lock = lock.toAdLock()
    )
} else {
    Ad()
}

private fun AdCreateObject?.toInternal(): Ad = Ad(
    title = this?.title ?: "",
    authors = this?.authors ?: "",
    publishing = this?.publishing ?: "",
    year = this?.year ?: 0,
    adType = this?.typeAd.toAdType(),
    price = this?.price?.let { BigDecimal.parseString(it) } ?: BigDecimal.ZERO
)

private fun AdSearchFilter?.toInternal(): AdFilter = AdFilter(
    searchString = this?.searchString ?: "",
    ownerId = this?.ownerId?.let { AdUserId(it) } ?: AdUserId.NONE,
    adType = this?.typeAd.toAdType()
)

private fun AdUpdateObject?.toInternal(): Ad = Ad(
    id = this?.id.toAdId(),
    title = this?.title ?: "",
    authors = this?.authors ?: "",
    publishing = this?.publishing ?: "",
    year = this?.year ?: 0,
    adType = this?.typeAd.toAdType(),
    price = this?.price?.let { BigDecimal.parseString(it) } ?: BigDecimal.ZERO,
    lock = this?.lock.toAdLock()
)

private fun TypeAd?.toAdType(): AdType = when (this) {
    TypeAd.DEMAND -> AdType.DEMAND
    TypeAd.PROPOSAL -> AdType.PROPOSAL
    null -> AdType.NONE
}