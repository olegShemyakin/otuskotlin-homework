package org.akira.otuskotlin.ads.common.repo

import org.akira.otuskotlin.ads.common.models.AdType
import org.akira.otuskotlin.ads.common.models.AdUserId

data class DbAdFilterRequest(
    val titleFilter: String = "",
    val ownerId: AdUserId = AdUserId.NONE,
    val adType: AdType = AdType.NONE
)
