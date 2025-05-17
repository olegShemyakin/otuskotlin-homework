package org.akira.otuskotlin.ads.app.ktor.v1

import AdAppSettings
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import org.akira.otuskotlin.ads.api.models.IRequest
import org.akira.otuskotlin.ads.api.models.IResponse
import org.akira.otuskotlin.ads.app.common.controllerHelper
import org.akira.otuskotlin.ads.mappers.fromTransport
import org.akira.otuskotlin.ads.mappers.toTransport
import kotlin.reflect.KClass

suspend inline fun <reified Q : IRequest, reified R : IResponse> ApplicationCall.processV1(
    appSettings: AdAppSettings,
    clazz: KClass<*>,
    logId: String
) = appSettings.controllerHelper(
    {
        fromTransport(receive<Q>())
    },
    { respond(toTransport()) },
    clazz,
    logId
)

