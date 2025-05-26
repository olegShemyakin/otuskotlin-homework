package org.akira.otuskotlin.ads.app.ktor.v1

import AdAppSettings
import io.ktor.server.application.*
import org.akira.otuskotlin.ads.api.models.*
import kotlin.reflect.KClass

val clCreate: KClass<*> = ApplicationCall::createAd::class
suspend fun ApplicationCall.createAd(appSettings: AdAppSettings) =
    processV1<AdCreateRequest, AdCreateResponse>(appSettings, clCreate, "create")

val clRead: KClass<*> = ApplicationCall::readAd::class
suspend fun ApplicationCall.readAd(appSettings: AdAppSettings) =
    processV1<AdReadRequest, AdReadResponse>(appSettings, clRead, "read")

val clUpdate: KClass<*> = ApplicationCall::updateAd::class
suspend fun ApplicationCall.updateAd(appSettings: AdAppSettings) =
    processV1<AdUpdateRequest, AdUpdateResponse>(appSettings, clUpdate, "update")

val clDelete: KClass<*> = ApplicationCall::deleteAd::class
suspend fun ApplicationCall.deleteAd(appSettings: AdAppSettings) =
    processV1<AdDeleteRequest, AdDeleteResponse>(appSettings, clDelete, "delete")

val clSearch: KClass<*> = ApplicationCall::searchAd::class
suspend fun ApplicationCall.searchAd(appSettings: AdAppSettings) =
    processV1<AdSearchRequest, AdSearchResponse>(appSettings, clSearch, "search")

