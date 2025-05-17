package org.akira.otuskotlin.ads.stubs

import com.ionspin.kotlin.bignum.decimal.BigDecimal
import org.akira.otuskotlin.ads.common.models.*

object AdStubBook {
    val AD_PROPOSAL_BOOK: Ad
        get() = Ad(
            id = AdId("1ad61c11-a84d-4bb2-af82-e0481e42015d"),
            title = "Имя ветра",
            authors = "Патрик Ротфусс",
            publishing = "fanzon",
            year = 2021,
            price = BigDecimal.parseString("700.00"),
            adType = AdType.PROPOSAL,
            ownerId = AdUserId("user-1"),
            permissionClient = mutableSetOf(
                AdPermissionClient.READ,
                AdPermissionClient.UPDATE,
                AdPermissionClient.DELETE
            )
        )
    val AD_DEMAND_BOOK = AD_PROPOSAL_BOOK.copy(adType = AdType.DEMAND)
}