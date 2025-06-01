package org.akira.otuskotlin.ads.common.models

import com.ionspin.kotlin.bignum.decimal.BigDecimal

data class Ad(
    var id: AdId = AdId.NONE,
    var title: String = "",
    var authors: String = "",
    var publishing: String = "",
    var year: Int = 0,
    var adType: AdType = AdType.NONE,
    var price: BigDecimal = BigDecimal.ZERO,
    var ownerId: AdUserId = AdUserId.NONE,
    var lock: AdLock = AdLock.NONE,
    val permissionClient: MutableSet<AdPermissionClient> = mutableSetOf()
) {
    fun deepCopy(): Ad = copy(
        permissionClient = permissionClient.toMutableSet()
    )

    fun isEmpty() = this == NONE

    companion object {
        private val NONE = Ad()

        fun toBigDecimal(price: String): BigDecimal = BigDecimal.parseString(price)
    }
}
