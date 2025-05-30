package org.akira.otuskotlin.ads.repo.tests

import org.akira.otuskotlin.ads.common.models.Ad
import org.akira.otuskotlin.ads.common.models.AdId
import org.akira.otuskotlin.ads.common.repo.DbAdIdRequest
import org.akira.otuskotlin.ads.common.repo.DbAdResponseErr
import org.akira.otuskotlin.ads.common.repo.DbAdResponseOk
import org.akira.otuskotlin.ads.common.repo.IRepoAd
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

abstract class RepoAdReadTest {
    abstract val repo: IRepoAd
    protected open val readSucc = initObjects[0]

    @Test
    fun readSuccess() = runRepoTest {
        val result = repo.readAd(DbAdIdRequest(readSucc.id))

        assertIs<DbAdResponseOk>(result)
        assertEquals(readSucc, result.data)
    }

    @Test
    fun readNotFound() = runRepoTest {
        val result = repo.readAd(DbAdIdRequest(notFoundId))

        assertIs<DbAdResponseErr>(result)
        val error = result.errors.find { it.code == "repo-not-found" }
        assertEquals("id", error?.field)
    }

    companion object : BaseInitAds("read") {
        override val initObjects: List<Ad> = listOf(
            createInitTestModel("read")
        )

        val notFoundId = AdId("ad-repo-read-notFound")
    }
}