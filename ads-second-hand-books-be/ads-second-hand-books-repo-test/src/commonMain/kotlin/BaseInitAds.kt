package org.akira.otuskotlin.ads.repo.tests

import com.ionspin.kotlin.bignum.decimal.BigDecimal
import org.akira.otuskotlin.ads.common.models.*

abstract class BaseInitAds(private val op: String) : IInitObjects<Ad> {
    open val lockOld: AdLock = AdLock("92fe70ef-b85c-424d-b119-2923709efe83")
    open val lockBad: AdLock = AdLock("c5e1889f-2b53-457e-8b92-4a80f157d79b")
    fun createInitTestModel(
        suf: String,
        ownerId: AdUserId = AdUserId("owner-123"),
        adType: AdType = AdType.DEMAND,
        lock: AdLock = lockOld
    ) = Ad(
        id = AdId("ad-repo-$op-$suf"),
        title = "$suf stub",
        authors = "$suf stub authors",
        publishing = "$suf stub publishing",
        year = 2025,
        adType = adType,
        price = BigDecimal.parseString("700.00"),
        ownerId = ownerId,
        lock = lock
    )
}