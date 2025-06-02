package org.akira.otuskotlin.ads.mappers

import org.akira.otuskotlin.ads.api.models.AdCreateObject
import org.akira.otuskotlin.ads.api.models.AdDeleteObject
import org.akira.otuskotlin.ads.api.models.AdReadObject
import org.akira.otuskotlin.ads.api.models.AdUpdateObject
import org.akira.otuskotlin.ads.common.models.Ad
import org.akira.otuskotlin.ads.common.models.AdId
import org.akira.otuskotlin.ads.common.models.AdLock

fun Ad.toTransportCreate() = AdCreateObject(
    title = title.takeIf { it.isNotBlank() },
    authors = authors.takeIf { it.isNotBlank() },
    publishing = publishing.takeIf { it.isNotBlank() },
    year = year,
    typeAd = adType.toTransport(),
    price = price.toPlainString()
)

fun Ad.toTransportRead() = AdReadObject(
    id = id.takeIf { it != AdId.NONE }?.asString()
)

fun Ad.toTransportUpdate() = AdUpdateObject(
    id = id.takeIf { it != AdId.NONE }?.asString(),
    title = title.takeIf { it.isNotBlank() },
    authors = authors.takeIf { it.isNotBlank() },
    publishing = publishing.takeIf { it.isNotBlank() },
    year = year,
    typeAd = adType.toTransport(),
    price = price.toPlainString(),
    lock = lock.takeIf { it != AdLock.NONE }?.asString()
)

fun Ad.toTransportDelete() = AdDeleteObject(
    id = id.takeIf { it != AdId.NONE }?.asString(),
    lock = lock.takeIf { it != AdLock.NONE }?.asString()
)