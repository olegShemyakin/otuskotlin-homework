package org.akira.otuskotlin.ads.repo.tests

import com.ionspin.kotlin.bignum.decimal.BigDecimal
import org.akira.otuskotlin.ads.common.models.Ad
import org.akira.otuskotlin.ads.common.models.AdId
import org.akira.otuskotlin.ads.common.models.AdType
import org.akira.otuskotlin.ads.common.models.AdUserId

abstract class BaseInitAds(private val op: String) : IInitObjects<Ad> {
    fun createInitTestModel(
        suf: String,
        ownerId: AdUserId = AdUserId("owner-123"),
        adType: AdType = AdType.DEMAND
    ) = Ad(
        id = AdId("ad-repo-$op-$suf"),
        title = "$suf stub",
        authors = "$suf stub authors",
        publishing = "$suf stub publishing",
        year = 2025,
        adType = adType,
        price = BigDecimal.parseString("700.00"),
        ownerId = ownerId
    )
}