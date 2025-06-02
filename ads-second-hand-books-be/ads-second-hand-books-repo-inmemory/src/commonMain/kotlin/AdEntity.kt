package org.akira.otuskotlin.ads.repo.inmemory

import com.ionspin.kotlin.bignum.decimal.BigDecimal
import org.akira.otuskotlin.ads.common.models.*

data class AdEntity(
    val id: String? = null,
    val title: String? = null,
    val authors: String? = null,
    val publishing: String? = null,
    val year: Int,
    val adType: String? = null,
    val price: String,
    val ownerId: String? = null,
    val lock: String? = null,
) {
    constructor(model: Ad): this(
        id = model.id.asString().takeIf { it.isNotBlank() },
        title = model.title.takeIf { it.isNotBlank() },
        authors = model.authors.takeIf { it.isNotBlank() },
        publishing = model.publishing.takeIf { it.isNotBlank() },
        year = model.year,
        adType = model.adType.takeIf { it != AdType.NONE }?.name,
        price = model.price.toStringExpanded(),
        ownerId = model.ownerId.asString().takeIf { it.isNotBlank() },
        lock = model.lock.asString().takeIf { it.isNotBlank() }
    )

    fun toInternal() = Ad(
        id = id?.let { AdId(it) } ?: AdId.NONE,
        title = title ?: "",
        authors = authors ?: "",
        publishing = publishing ?: "",
        year = year,
        adType = adType?.let { AdType.valueOf(it) } ?: AdType.NONE,
        price = price.let { BigDecimal.parseString(it) },
        ownerId = ownerId?.let { AdUserId(it) } ?: AdUserId.NONE,
        lock = lock?.let { AdLock(it) } ?: AdLock.NONE
    )
}
