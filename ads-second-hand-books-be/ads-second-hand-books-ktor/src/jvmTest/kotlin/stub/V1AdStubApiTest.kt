package org.akira.otuskotlin.ads.app.ktor.stub

import AdAppSettings
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.server.testing.*
import moduleJvm
import org.akira.otuskotlin.ads.api.models.*
import org.akira.otuskotlin.ads.common.AdCorSettings
import kotlin.test.Test
import kotlin.test.assertEquals

const val idExpected = "1ad61c11-a84d-4bb2-af82-e0481e42015d"
const val idExpectedSearch = "2ff9cccf-3abd-46a2-93fc-bbef792ef7e0"

class V1AdStubApiTest {
    @Test
    fun create() = v1TestApplication(
        func = "create",
        request = AdCreateRequest(
            ad = AdCreateObject(
                title = "Имя ветра",
                authors = "Патрик Ротфусс",
                publishing = "fanzon",
                year = 2021,
                price = "700.00",
                typeAd = TypeAd.PROPOSAL
            ),
            debug = AdDebug(
                mode = AdRequestDebugMode.STUB,
                stub = AdRequestDebugStubs.SUCCESS
            )
        )
    ) { response ->
        val responseObj = response.body<AdCreateResponse>()
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(idExpected, responseObj.ad?.id)
    }

    @Test
    fun read() = v1TestApplication(
        func = "read",
        request = AdReadRequest(
            ad = AdReadObject("1ad61c11-a84d-4bb2-af82-e0481e42015d"),
            debug = AdDebug(
                mode = AdRequestDebugMode.STUB,
                stub = AdRequestDebugStubs.SUCCESS
            )
        )
    ) { response ->
        val responseObj = response.body<AdReadResponse>()
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(idExpected, responseObj.ad?.id)
    }

    @Test
    fun update() = v1TestApplication(
        func = "update",
        request = AdUpdateRequest(
            ad = AdUpdateObject(
                title = "Имя ветра",
                authors = "Патрик Ротфусс",
                publishing = "fanzon",
                year = 2021,
                price = "700.00",
                typeAd = TypeAd.PROPOSAL
            ),
            debug = AdDebug(
                mode = AdRequestDebugMode.STUB,
                stub = AdRequestDebugStubs.SUCCESS
            )
        )
    ) { response ->
        val responseObj = response.body<AdUpdateResponse>()
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(idExpected, responseObj.ad?.id)
    }

    @Test
    fun delete() = v1TestApplication(
        func = "delete",
        request = AdDeleteRequest(
            ad = AdDeleteObject(id = idExpected),
            debug = AdDebug(
                mode = AdRequestDebugMode.STUB,
                stub = AdRequestDebugStubs.SUCCESS
            )
        )
    ) { response ->
        val responseObj = response.body<AdDeleteResponse>()
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(idExpected, responseObj.ad?.id)
    }

    @Test
    fun search() = v1TestApplication(
        func = "search",
        request = AdSearchRequest(
            adFilter = AdSearchFilter(),
            debug = AdDebug(
                mode = AdRequestDebugMode.STUB,
                stub = AdRequestDebugStubs.SUCCESS
            )
        )
    ) { response ->
        val responseObj = response.body<AdSearchResponse>()
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(idExpectedSearch, responseObj.ads?.first()?.id)
    }

    private fun v1TestApplication(
        func: String,
        request: IRequest,
        function: suspend (HttpResponse) -> Unit
    ): Unit = testApplication {
        application { moduleJvm(AdAppSettings(corSettings = AdCorSettings())) }
        val client = createClient {
            install(ContentNegotiation) {
                jackson {
                    disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

                    enable(SerializationFeature.INDENT_OUTPUT)
                    writerWithDefaultPrettyPrinter()
                }
            }
        }
        val response = client.post("/v1/ad/$func") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }
        function(response)
    }
}