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
import kotlin.test.assertNotNull

abstract class RepoAdDeleteTest {
    abstract val repo: IRepoAd
    protected open val deleteSucc = initObjects[0]
    protected open val notFoundId = AdId("ad-repo-delete-notFound")

    @Test
    fun deleteSuccess() = runRepoTest {
        val result = repo.deleteAd(DbAdIdRequest(deleteSucc.id))
        assertIs<DbAdResponseOk>(result)
        assertEquals(deleteSucc.title, result.data.title)
    }

    @Test
    fun deleteNotFound() = runRepoTest {
        val result = repo.readAd(DbAdIdRequest(notFoundId))

        assertIs<DbAdResponseErr>(result)
        val error = result.errors.find { it.code == "repo-not-found" }
        assertNotNull(error)
    }

    companion object : BaseInitAds("delete") {
        override val initObjects: List<Ad> = listOf(
            createInitTestModel("delete")
        )
    }
}