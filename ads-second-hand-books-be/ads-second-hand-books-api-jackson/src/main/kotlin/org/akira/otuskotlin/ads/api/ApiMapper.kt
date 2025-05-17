package org.akira.otuskotlin.ads.api

import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import org.akira.otuskotlin.ads.api.models.IRequest
import org.akira.otuskotlin.ads.api.models.IResponse

val apiMapper = JsonMapper.builder().run {
    enable(MapperFeature.USE_BASE_TYPE_AS_DEFAULT_IMPL)
    build()
}

fun apiRequestSerialize(request: IRequest): String = apiMapper.writeValueAsString(request)

@Suppress("UNCHECKED_CAST")
fun <T : IRequest> apiRequestDeserialize(json: String): T =
    apiMapper.readValue(json, IRequest::class.java) as T

fun apiResponseSerialize(response: IResponse): String = apiMapper.writeValueAsString(response)

@Suppress("UNCHECKED_CAST")
fun <T : IResponse> apiResponseDeserialize(json: String): T =
    apiMapper.readValue(json, IResponse::class.java) as T