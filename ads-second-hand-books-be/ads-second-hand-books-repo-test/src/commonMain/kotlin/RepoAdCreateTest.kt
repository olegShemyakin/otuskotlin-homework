package org.akira.otuskotlin.ads.repo.tests

import com.ionspin.kotlin.bignum.decimal.BigDecimal
import org.akira.otuskotlin.ads.common.models.Ad
import org.akira.otuskotlin.ads.common.models.AdId
import org.akira.otuskotlin.ads.common.models.AdType
import org.akira.otuskotlin.ads.common.models.AdUserId
import org.akira.otuskotlin.ads.common.repo.DbAdRequest
import org.akira.otuskotlin.ads.common.repo.DbAdResponseOk
import org.akira.otuskotlin.ads.repo.common.IRepoAdInitializable
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

abstract class RepoAdCreateTest {
    abstract val repo: IRepoAdInitializable
    protected open val uuidNew = AdId("67d40afd-5f8b-4d54-9e74-ecc50e1fa876")

    private val createObj = Ad(
        title = "create object",
        authors = "create object authors",
        publishing = "create object publishing",
        year = 2025,
        adType = AdType.DEMAND,
        price = BigDecimal.parseString("700.00"),
        ownerId = AdUserId("owner-123")
    )

    @Test
    fun createSuccess() = runRepoTest {
        val result = repo.createAd(DbAdRequest(createObj))
        val expected = createObj
        assertIs<DbAdResponseOk>(result)
        assertEquals(uuidNew, result.data.id)
        assertEquals(expected.title, result.data.title)
        assertEquals(expected.authors, result.data.authors)
        assertEquals(expected.publishing, result.data.publishing)
        assertEquals(expected.year, result.data.year)
        assertEquals(expected.adType, result.data.adType)
        assertEquals(expected.price, result.data.price)
    }

    companion object : BaseInitAds("create") {
        override val initObjects: List<Ad> = emptyList()

    }
}