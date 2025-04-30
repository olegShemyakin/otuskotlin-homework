package org.akira.otuskotlin.ads.api

import org.akira.otuskotlin.ads.api.models.*
import java.math.BigDecimal
import kotlin.test.Test
import kotlin.test.assertEquals

class RequestSerializationTest {
    private val expectedSerReq = "{\"requestType\":\"create\",\"requestType\":\"create\",\"debug\":{\"mode\":\"stub\",\"stub\":\"badId\"},\"ad\":{\"title\":\"Book\",\"authors\":\"John Doe\",\"publishing\":\"Azbooka\",\"year\":2025,\"typeAd\":\"proposal\",\"price\":\"10.0\"}}"
    private val request = AdCreateRequest(
        requestType = "create",
        debug = AdDebug(
            mode = AdRequestDebugMode.STUB,
            stub = AdRequestDebugStubs.BAD_ID
        ),
        ad = AdCreateObject(
            title = "Book",
            authors = "John Doe",
            publishing = "Azbooka",
            year = 2025,
            typeAd = TypeAd.PROPOSAL,
            price = "10.0"
        )
    )

    @Test
    fun serialize() {
        val json = apiRequestSerialize(request)

        assertEquals(expectedSerReq, json)
    }

    @Test
    fun deserialize() {
        val obj = apiRequestDeserialize<AdCreateRequest>(expectedSerReq)

        assertEquals(request, obj)
    }

    @Test
    fun deserializeNaked() {
        val jsonString = """
            {"ad": null}
        """.trimIndent()
        val obj = apiMapper.readValue(jsonString, AdCreateRequest::class.java)

        assertEquals(null, obj.ad)
    }
}