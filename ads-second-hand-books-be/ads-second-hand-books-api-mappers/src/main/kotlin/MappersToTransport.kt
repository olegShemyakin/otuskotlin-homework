package org.akira.otuskotlin.ads.mappers

import org.akira.otuskotlin.ads.api.models.*
import org.akira.otuskotlin.ads.common.AdContext
import org.akira.otuskotlin.ads.common.exceptions.UnknownAdCommand
import org.akira.otuskotlin.ads.common.models.*

fun AdContext.toTransport(): IResponse = when (val cmd = command) {
    AdCommand.CREATE -> toTransportCreate()
    AdCommand.READ -> toTransportRead()
    AdCommand.UPDATE -> toTransportUpdate()
    AdCommand.DELETE -> toTransportDelete()
    AdCommand.SEARCH -> toTransportSearch()
    AdCommand.NONE -> throw UnknownAdCommand(cmd)
}

fun AdContext.toTransportCreate() = AdCreateResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    ad = adResponse.toTransport()
)

fun AdContext.toTransportRead() = AdReadResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    ad = adResponse.toTransport()
)

fun AdContext.toTransportUpdate() = AdUpdateResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    ad = adResponse.toTransport()
)

fun AdContext.toTransportDelete() = AdDeleteResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    ad = adResponse.toTransport()
)

fun AdContext.toTransportSearch() = AdSearchResponse(
    result = state.toResult(),
    errors = errors.toTransportErrors(),
    ads = adsResponse.toTransport()
)

private fun List<Ad>.toTransport(): List<AdResponseObject>? = this
    .map { it.toTransport() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun Ad.toTransport(): AdResponseObject = AdResponseObject(
    id = id.takeIf { it != AdId.NONE }?.asString(),
    title = title.takeIf { it.isNotBlank() },
    authors = authors.takeIf { it.isNotBlank() },
    publishing = publishing.takeIf { it.isNotBlank() },
    year = year,
    typeAd = adType.toTransport(),
    price = price.toPlainString(),
    ownerId = ownerId.takeIf { it != AdUserId.NONE }?.asString(),
    lock = lock.takeIf { it != AdLock.NONE }?.asString(),
    permissions = permissionClient.toTransport()
)

private fun Set<AdPermissionClient>.toTransport(): Set<AdPermissions>? = this
    .map { it.toTransport() }
    .toSet()
    .takeIf { it.isNotEmpty() }

private fun AdPermissionClient.toTransport(): AdPermissions = when (this) {
    AdPermissionClient.READ -> AdPermissions.READ
    AdPermissionClient.UPDATE -> AdPermissions.UPDATE
    AdPermissionClient.DELETE -> AdPermissions.DELETE
}

private fun AdType.toTransport(): TypeAd? = when (this) {
    AdType.DEMAND -> TypeAd.DEMAND
    AdType.PROPOSAL -> TypeAd.PROPOSAL
    AdType.NONE -> null
}

private fun List<AdError>.toTransportErrors(): List<Error>? = this
    .map { it.toTransport() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun AdError.toTransport() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() }
)

private fun AdState.toResult(): ResponseResult? = when (this) {
    AdState.RUNNING -> ResponseResult.SUCCESS
    AdState.FAILING -> ResponseResult.ERROR
    AdState.FINISHING -> ResponseResult.SUCCESS
    AdState.NONE -> null
}