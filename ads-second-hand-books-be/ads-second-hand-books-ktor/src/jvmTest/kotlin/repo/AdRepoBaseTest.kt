package org.akira.otuskotlin.ads.app.ktor.repo

import AdAppSettings
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.testing.*
import org.akira.otuskotlin.ads.api.models.*
import org.akira.otuskotlin.ads.app.ktor.moduleJvm
import org.akira.otuskotlin.ads.common.models.AdId
import org.akira.otuskotlin.ads.common.models.AdLock
import org.akira.otuskotlin.ads.common.models.AdType
import org.akira.otuskotlin.ads.mappers.toTransportCreate
import org.akira.otuskotlin.ads.mappers.toTransportDelete
import org.akira.otuskotlin.ads.mappers.toTransportRead
import org.akira.otuskotlin.ads.mappers.toTransportUpdate
import org.akira.otuskotlin.ads.stubs.AdStub
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

abstract class AdRepoBaseTest {
    abstract val workMode: AdRequestDebugMode
    abstract val appSettingsCreate: AdAppSettings
    abstract val appSettingsRead:   AdAppSettings
    abstract val appSettingsUpdate: AdAppSettings
    abstract val appSettingsDelete: AdAppSettings
    abstract val appSettingsSearch: AdAppSettings

    protected val uuidOld = "77438dcd-a94f-4d34-8b30-caa4b52660d2"
    protected val uuidNew = "2f99e647-bb83-42f1-ba7c-d6591a066938"
    protected val uuidProp = "0b56d920-103b-44bc-9d62-3d37147a124d"
    protected val initAd = AdStub.prepareResult {
        id = AdId(uuidOld)
        adType = AdType.DEMAND
        lock = AdLock(uuidOld)
    }
    protected val initAdProposal = AdStub.prepareResult {
        id = AdId(uuidProp)
        adType = AdType.PROPOSAL
    }

    @Test
    fun create() {
        val ad = initAd.toTransportCreate()
        v1TestApplication(
            conf = appSettingsCreate,
            func = "create",
            request = AdCreateRequest(
                ad = ad,
                debug = AdDebug(mode = workMode)
            )
        ) { response ->
            val responseObj = response.body<AdCreateResponse>()
            assertEquals(200, response.status.value)
            assertEquals(uuidNew, responseObj.ad?.id)
            assertEquals(ad.title, responseObj.ad?.title)
            assertEquals(ad.authors, responseObj.ad?.authors)
            assertEquals(ad.publishing, responseObj.ad?.publishing)
            assertEquals(ad.year, responseObj.ad?.year)
        }
    }

    @Test
    fun read() {
        val ad = initAd.toTransportRead()
        v1TestApplication(
            conf = appSettingsRead,
            func = "read",
            request = AdReadRequest(
                ad = ad,
                debug = AdDebug(mode = workMode),
            ),
        ) { response ->
            val responseObj = response.body<IResponse>() as AdReadResponse
            assertEquals(200, response.status.value)
            assertEquals(uuidOld, responseObj.ad?.id)
        }
    }

    @Test
    fun update() {
        val ad = initAd.toTransportUpdate()
        v1TestApplication(
            conf = appSettingsUpdate,
            func = "update",
            request = AdUpdateRequest(
                ad = ad,
                debug = AdDebug(mode = workMode),
            ),
        ) { response ->
            val responseObj = response.body<AdUpdateResponse>()
            assertEquals(200, response.status.value)
            assertEquals(ad.id, responseObj.ad?.id)
            assertEquals(ad.title, responseObj.ad?.title)
            assertEquals(ad.authors, responseObj.ad?.authors)
            assertEquals(ad.publishing, responseObj.ad?.publishing)
            assertEquals(ad.year, responseObj.ad?.year)
        }
    }

    @Test
    fun delete() {
        val ad = initAd.toTransportDelete()
        v1TestApplication(
            conf = appSettingsDelete,
            func = "delete",
            request = AdDeleteRequest(
                ad = ad,
                debug = AdDebug(mode = workMode),
            ),
        ) { response ->
            val responseObj = response.body<AdDeleteResponse>()
            assertEquals(200, response.status.value)
            assertEquals(uuidOld, responseObj.ad?.id)
        }
    }

    @Test
    fun search() = v1TestApplication(
        conf = appSettingsSearch,
        func = "search",
        request = AdSearchRequest(
            adFilter = AdSearchFilter(),
            debug = AdDebug(mode = workMode),
        ),
    ) { response ->
        val responseObj = response.body<AdSearchResponse>()
        assertEquals(200, response.status.value)
        assertNotEquals(0, responseObj.ads?.size)
        assertEquals(uuidOld, responseObj.ads?.first()?.id)
    }

    private inline fun <reified T: IRequest> v1TestApplication(
        conf: AdAppSettings,
        func: String,
        request: T,
        crossinline function: suspend (HttpResponse) -> Unit,
    ): Unit = testApplication {
        application { moduleJvm(appSettings = conf) }
        val client = createClient {
            install(ContentNegotiation) {
                jackson()
            }
        }
        val response = client.post("/v1/ad/$func") {
            contentType(ContentType.Application.Json)
            header("X-Trace-Id", "12345")
            setBody(request)
        }
        function(response)
    }

}