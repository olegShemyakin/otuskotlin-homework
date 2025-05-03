package org.akira.otuskotlin.ads.logging.jvm

import ch.qos.logback.classic.Logger
import org.akira.otuskotlin.ads.logging.common.IAdLogWrapper
import org.slf4j.LoggerFactory
import kotlin.reflect.KClass

fun adLoggerLogback(logger: Logger) : IAdLogWrapper = AdLogWrapperLogback(
    logger = logger,
    loggerId = logger.name
)

fun adLoggerLogback(clazz: KClass<*>) : IAdLogWrapper = adLoggerLogback(LoggerFactory.getLogger(clazz.java) as Logger)

fun adLoggerLogback(loggerId: String) : IAdLogWrapper = adLoggerLogback(LoggerFactory.getLogger(loggerId) as Logger)
