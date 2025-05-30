package org.akira.otuskotlin.ads.repo.tests

import com.ionspin.kotlin.bignum.decimal.BigDecimal
import org.akira.otuskotlin.ads.common.models.Ad
import org.akira.otuskotlin.ads.common.models.AdId
import org.akira.otuskotlin.ads.common.models.AdType
import org.akira.otuskotlin.ads.common.models.AdUserId
import org.akira.otuskotlin.ads.common.repo.DbAdRequest
import org.akira.otuskotlin.ads.common.repo.DbAdResponseErr
import org.akira.otuskotlin.ads.common.repo.DbAdResponseOk
import org.akira.otuskotlin.ads.common.repo.IRepoAd
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

abstract class RepoAdUpdateTest {
    abstract val repo: IRepoAd
    protected open val updateSucc = initObjects[0]
    protected val updateNotFound = AdId("ad-repo-update-not-found")

    private val reqUpdateSucc by lazy {
        Ad(
            id = updateSucc.id,
            title = "update object",
            authors = "update object authors",
            publishing = "update object publishing",
            year = 2025,
            adType = AdType.DEMAND,
            price = BigDecimal.parseString("700.00"),
            ownerId = AdUserId("owner-123")
        )
    }

    private val reqUpdateNotFound by lazy {
        Ad(
            id = updateNotFound,
            title = "update object not found",
            authors = "update object authors not found",
            publishing = "update object publishing not found",
            year = 2025,
            adType = AdType.DEMAND,
            price = BigDecimal.parseString("700.00"),
            ownerId = AdUserId("owner-123")
        )
    }

    @Test
    fun updateSuccess() = runRepoTest {
        val result = repo.updateAd(DbAdRequest(reqUpdateSucc))
        assertIs<DbAdResponseOk>(result)
        assertEquals(reqUpdateSucc.id, result.data.id)
        assertEquals(reqUpdateSucc.title, result.data.title)
        assertEquals(reqUpdateSucc.authors, result.data.authors)
    }

    @Test
    fun updateNotFound() = runRepoTest {
        val result = repo.updateAd(DbAdRequest(reqUpdateNotFound))
        assertIs<DbAdResponseErr>(result)
        val error = result.errors.find { it.code == "repo-not-found" }
        assertEquals("id", error?.field)
    }

    companion object : BaseInitAds("update") {
        override val initObjects: List<Ad> = listOf(
            createInitTestModel("update")
        )
    }
}