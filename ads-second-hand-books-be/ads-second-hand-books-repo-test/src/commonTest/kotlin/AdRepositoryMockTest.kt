import kotlinx.coroutines.test.runTest
import org.akira.otuskotlin.ads.common.models.Ad
import org.akira.otuskotlin.ads.common.repo.*
import org.akira.otuskotlin.ads.common.stubs.AdsStubs
import org.akira.otuskotlin.ads.repo.tests.AdRepositoryMock
import org.akira.otuskotlin.ads.stubs.AdStub
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class AdRepositoryMockTest {
    private val repo = AdRepositoryMock(
        invokeCreateAd = { DbAdResponseOk(AdStub.prepareResult { title = "create" }) },
        invokeReadAd = { DbAdResponseOk(AdStub.prepareResult { title = "read" }) },
        invokeUpdateAd = { DbAdResponseOk(AdStub.prepareResult { title = "update" }) },
        invokeDeleteAd = { DbAdResponseOk(AdStub.prepareResult { title = "delete" }) },
        invokeSearchAd = { DbAdsResponseOk(listOf(AdStub.prepareResult { title = "search" })) }
    )

    @Test
    fun mockCreate() = runTest {
        val result = repo.createAd(DbAdRequest(Ad()))
        assertIs<DbAdResponseOk>(result)
        assertEquals("create", result.data.title)
    }

    @Test
    fun mockRead() = runTest {
        val result = repo.readAd(DbAdIdRequest(Ad()))
        assertIs<DbAdResponseOk>(result)
        assertEquals("read", result.data.title)
    }

    @Test
    fun mockUpdate() = runTest {
        val result = repo.updateAd(DbAdRequest(Ad()))
        assertIs<DbAdResponseOk>(result)
        assertEquals("update", result.data.title)
    }

    @Test
    fun mockDelete() = runTest {
        val result = repo.deleteAd(DbAdIdRequest(Ad()))
        assertIs<DbAdResponseOk>(result)
        assertEquals("delete", result.data.title)
    }

    @Test
    fun mockSearch() = runTest {
        val result = repo.searchAd(DbAdFilterRequest())
        assertIs<DbAdsResponseOk>(result)
        assertEquals("search", result.data.first().title)
    }
}