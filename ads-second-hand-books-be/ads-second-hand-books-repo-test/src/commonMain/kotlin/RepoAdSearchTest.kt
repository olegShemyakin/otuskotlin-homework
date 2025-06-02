package org.akira.otuskotlin.ads.repo.tests

import org.akira.otuskotlin.ads.common.models.Ad
import org.akira.otuskotlin.ads.common.models.AdType
import org.akira.otuskotlin.ads.common.models.AdUserId
import org.akira.otuskotlin.ads.common.repo.DbAdFilterRequest
import org.akira.otuskotlin.ads.common.repo.DbAdsResponseOk
import org.akira.otuskotlin.ads.common.repo.IRepoAd
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

abstract class RepoAdSearchTest {
    abstract val repo: IRepoAd
    protected open val initializedObjects: List<Ad> = initObjects

    @Test
    fun searchOwner() = runRepoTest {
        val result = repo.searchAd(DbAdFilterRequest(ownerId = searchOwnerId))
        assertIs<DbAdsResponseOk>(result)
        val expected = listOf(initializedObjects[1], initializedObjects[3]).sortedBy { it.id.asString() }
        assertEquals(expected, result.data.sortedBy { it.id.asString() })
    }

    @Test
    fun searchAdType() = runRepoTest {
        val result = repo.searchAd(DbAdFilterRequest(adType = AdType.DEMAND))
        assertIs<DbAdsResponseOk>(result)
        val expected = listOf(initializedObjects[2], initializedObjects[4]).sortedBy { it.id.asString() }
        assertEquals(expected, result.data.sortedBy { it.id.asString() })
    }

    companion object : BaseInitAds("search") {
        val searchOwnerId = AdUserId("owner-124")
        override val initObjects: List<Ad> = listOf(
            createInitTestModel("ad1"),
            createInitTestModel("ad2", ownerId = searchOwnerId),
            createInitTestModel("ad3", adType = AdType.DEMAND),
            createInitTestModel("ad4", ownerId = searchOwnerId),
            createInitTestModel("ad5", adType = AdType.DEMAND),
        )
    }
}