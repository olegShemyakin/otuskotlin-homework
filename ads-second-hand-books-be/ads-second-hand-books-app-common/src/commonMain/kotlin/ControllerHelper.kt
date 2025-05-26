package org.akira.otuskotlin.ads.app.common

import kotlinx.datetime.Clock
import org.akira.otuskotlin.ads.api.log.mapper.toLog
import org.akira.otuskotlin.ads.common.AdContext
import org.akira.otuskotlin.ads.common.helpers.asAdError
import org.akira.otuskotlin.ads.common.models.AdCommand
import org.akira.otuskotlin.ads.common.models.AdState
import kotlin.reflect.KClass

suspend inline fun <T> IAdAppSettings.controllerHelper(
    crossinline getRequest: suspend AdContext.() -> Unit,
    crossinline toResponse: suspend AdContext.() -> T,
    clazz: KClass<*>,
    logId: String
): T {
    val logger = corSettings.loggerProvider.logger(clazz)
    val ctx = AdContext(
        timeStart = Clock.System.now()
    )
    return try {
        ctx.getRequest()
        logger.info(
            msg = "Request $logId started for ${clazz.simpleName}",
            marker = "BIZ",
            data = ctx.toLog(logId)
        )
        processor.exec(ctx)
        logger.info(
            msg = "Request $logId processed for ${clazz.simpleName}",
            marker = "BIZ",
            data = ctx.toLog(logId)
        )
        ctx.toResponse()
    } catch (e: Throwable) {
        logger.error(
            msg = "Request $logId failed for ${clazz.simpleName}",
            marker = "BIZ",
            data = ctx.toLog(logId)
        )
        ctx.state = AdState.FAILING
        ctx.errors.add(e.asAdError())
        processor.exec(ctx)
        if (ctx.command == AdCommand.NONE) {
            ctx.command = AdCommand.READ
        }
        ctx.toResponse()
    }
}