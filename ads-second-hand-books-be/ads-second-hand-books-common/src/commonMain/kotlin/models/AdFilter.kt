package org.akira.otuskotlin.ads.common.models

data class AdFilter(
    var searchString: String = "",
    var ownerId: AdUserId = AdUserId.NONE,
    var adType: AdType = AdType.NONE
) {
    fun deepCopy(): AdFilter = copy()

    fun isEmpty() = this == NONE

    companion object {
        private val NONE = AdFilter()
    }
}
