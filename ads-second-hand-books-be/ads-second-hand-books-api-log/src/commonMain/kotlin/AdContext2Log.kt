package org.akira.otuskotlin.ads.api.log.mapper

import kotlinx.datetime.Clock
import org.akira.otuskotlin.ads.api.log.models.*
import org.akira.otuskotlin.ads.common.AdContext
import org.akira.otuskotlin.ads.common.models.*

fun AdContext.toLog(logId: String) = CommonLogModel(
    messageTime = Clock.System.now().toString(),
    logId = logId,
    source = "ads-second-hand-books",
    ad = toAdLog(),
    errors = errors.map { it.toLog() }
)

private fun AdContext.toAdLog(): AdLogModel? {
    val adNone = Ad()
    return AdLogModel(
        requestId = requestId.takeIf { it != AdRequestId.NONE }?.asString(),
        requestAd = adRequest.takeIf { it != adNone }?.toLog(),
        responseAd = adResponse.takeIf { it != adNone }?.toLog(),
        responseAds = adsResponse.takeIf { it.isNotEmpty() }?.filter { it != adNone }?.map { it.toLog() },
        requestFilter = adFilterRequest.takeIf { it != AdFilter() }?.toLog()
    ).takeIf { it != AdLogModel() }
}

private fun AdFilter.toLog() = AdFilterLog(
    searchString = searchString.takeIf { it.isNotBlank() },
    ownerId = ownerId.takeIf { it != AdUserId.NONE }?.asString(),
    typeAd = adType.takeIf { it != AdType.NONE }?.name
)

private fun AdError.toLog() = ErrorLogModel(
    message = message.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    code = code.takeIf { it.isNotBlank() },
    level = level.name
)

private fun Ad.toLog() = AdLog(
    id = id.takeIf { it != AdId.NONE }?.asString(),
    title = title.takeIf { it.isNotBlank() },
    authors = authors.takeIf { it.isNotBlank() },
    publishing = publishing.takeIf { it.isNotBlank() },
    year = year,
    typeAd = adType.takeIf { it != AdType.NONE }?.name,
    price = price.toPlainString(),
    ownerId = ownerId.takeIf { it != AdUserId.NONE }?.asString(),
    permissions = permissionClient.takeIf { it.isNotEmpty() }?.map { it.name }?.toSet()
)