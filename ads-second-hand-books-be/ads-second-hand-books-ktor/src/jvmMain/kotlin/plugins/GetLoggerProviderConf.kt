package org.akira.otuskotlin.ads.app.ktor.plugins

import io.ktor.server.application.*
import org.akira.otuskotlin.ads.logging.common.AdLoggerProvider
import org.akira.otuskotlin.ads.logging.jvm.adLoggerLogback

actual fun Application.getLoggerProviderConf(): AdLoggerProvider =
    when (val mode = environment.config.propertyOrNull("ktor.logger")?.getString()) {
        "logback", null -> AdLoggerProvider { adLoggerLogback(it) }
        else -> throw Exception("Logger $mode is not allowed. Additted values are logback (default)")
    }

