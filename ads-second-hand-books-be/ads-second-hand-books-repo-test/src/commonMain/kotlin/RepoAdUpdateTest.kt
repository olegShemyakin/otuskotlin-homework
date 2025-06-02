package org.akira.otuskotlin.ads.repo.tests

import com.ionspin.kotlin.bignum.decimal.BigDecimal
import org.akira.otuskotlin.ads.common.models.*
import org.akira.otuskotlin.ads.common.repo.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

abstract class RepoAdUpdateTest {
    abstract val repo: IRepoAd
    protected open val updateSucc = initObjects[0]
    protected open val updateConc = initObjects[1]
    protected val updateNotFound = AdId("ad-repo-update-not-found")
    protected val lockBad = AdLock("d2b65dbf-7121-49fd-ac4e-c41c4edb1a24")
    protected val lockNew = AdLock("70af90ef-2b3d-40a2-9429-ce061677a705")

    private val reqUpdateSucc by lazy {
        Ad(
            id = updateSucc.id,
            title = "update object",
            authors = "update object authors",
            publishing = "update object publishing",
            year = 2025,
            adType = AdType.DEMAND,
            price = BigDecimal.parseString("700.00"),
            ownerId = AdUserId("owner-123"),
            lock = initObjects.first().lock
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
            ownerId = AdUserId("owner-123"),
            lock = initObjects.first().lock
        )
    }
    private val reqUpdateConc by lazy {
        Ad(
            id = updateConc.id,
            title = "update object not found",
            authors = "test",
            publishing = "test",
            year = 2025,
            adType = AdType.DEMAND,
            price = BigDecimal.parseString("700.00"),
            lock = lockBad
        )
    }

    @Test
    fun updateSuccess() = runRepoTest {
        val result = repo.updateAd(DbAdRequest(reqUpdateSucc))
        assertIs<DbAdResponseOk>(result)
        assertEquals(reqUpdateSucc.id, result.data.id)
        assertEquals(reqUpdateSucc.title, result.data.title)
        assertEquals(reqUpdateSucc.authors, result.data.authors)
        assertEquals(lockNew, result.data.lock)
    }

    @Test
    fun updateNotFound() = runRepoTest {
        val result = repo.updateAd(DbAdRequest(reqUpdateNotFound))
        assertIs<DbAdResponseErr>(result)
        val error = result.errors.find { it.code == "repo-not-found" }
        assertEquals("id", error?.field)
    }

    @Test
    fun updateConcurrencyError() = runRepoTest {
        val result = repo.updateAd(DbAdRequest(reqUpdateConc))
        assertIs<DbAdResponseErrWithData>(result)
        val error = result.errors.find { it.code == "repo-concurrency" }
        assertEquals("lock", error?.field)
        assertEquals(updateConc, result.data)
    }

    companion object : BaseInitAds("update") {
        override val initObjects: List<Ad> = listOf(
            createInitTestModel("update"),
            createInitTestModel("updateConc")
        )
    }
}