package org.akira.otuskotlin.ads.common.models

data class Ad(
    var id: AdId = AdId.NONE,
    var title: String = "",
    var authors: String = "",
    var publishing: String = "",
    var year: Int = 0,
    var adType: AdType = AdType.NONE,
    var price: Double = 0.0,
    var ownerId: AdUserId = AdUserId.NONE,
    var lock: AdLock = AdLock.NONE,
    val permissionClient: MutableSet<AdPermissionClient> = mutableSetOf()
) {
    fun isEmpty() = this == NONE

    companion object {
        private val NONE = Ad()
    }
}
