package org.akira.otuskotlin.ads.common.repo

import org.akira.otuskotlin.ads.common.models.Ad
import org.akira.otuskotlin.ads.common.models.AdId

data class DbAdIdRequest(val id: AdId) {
    constructor(ad: Ad): this(ad.id)
}
