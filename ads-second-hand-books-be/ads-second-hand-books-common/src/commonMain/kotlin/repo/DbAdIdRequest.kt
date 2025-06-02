package org.akira.otuskotlin.ads.common.repo

import org.akira.otuskotlin.ads.common.models.Ad
import org.akira.otuskotlin.ads.common.models.AdId
import org.akira.otuskotlin.ads.common.models.AdLock

data class DbAdIdRequest(
    val id: AdId,
    val lock: AdLock = AdLock.NONE
) {
    constructor(ad: Ad): this(ad.id, ad.lock)
}
