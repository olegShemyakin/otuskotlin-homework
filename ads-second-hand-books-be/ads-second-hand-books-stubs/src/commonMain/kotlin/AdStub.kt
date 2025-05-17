package org.akira.otuskotlin.ads.stubs

import org.akira.otuskotlin.ads.common.models.Ad
import org.akira.otuskotlin.ads.common.models.AdId
import org.akira.otuskotlin.ads.common.models.AdType
import org.akira.otuskotlin.ads.stubs.AdStubBook.AD_DEMAND_BOOK
import org.akira.otuskotlin.ads.stubs.AdStubBook.AD_PROPOSAL_BOOK

object AdStub {
    fun get(): Ad = AD_PROPOSAL_BOOK.copy()

    fun prepareResult(block: Ad.() -> Unit): Ad = get().apply(block)

    fun prepareSearchList(filter: String, type: AdType) = listOf(
        adDemand("2ff9cccf-3abd-46a2-93fc-bbef792ef7e0", filter, type),
        adDemand("7bae7f47-5f08-4516-9a88-8755b367ee12", filter, type),
        adDemand("7ba4d1f0-9aea-40cd-91ff-bf1258414606", filter, type),
        adDemand("dfe230b4-1f17-4fd3-b3db-8a86f80de1e9", filter, type),
        adDemand("fc0c7989-d37b-4545-8fa6-969d9ea4c139", filter, type),
        adDemand("5edd643e-d037-438c-8134-9debb3d75773", filter, type)
    )

    private fun adDemand(id: String, filter: String, type: AdType) =
        createAd(AD_DEMAND_BOOK, id = id, filter = filter, type = type)

    private fun adProposal(id: String, filter: String, type: AdType) =
        createAd(AD_PROPOSAL_BOOK, id = id, filter = filter, type = type)

    private fun createAd(base: Ad, id: String, filter: String, type: AdType) = base.copy(
        id = AdId(id),
        title = "$filter $id",
        adType = type
    )
}