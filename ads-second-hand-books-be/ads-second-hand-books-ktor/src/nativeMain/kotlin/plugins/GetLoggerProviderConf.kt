package org.akira.otuskotlin.ads.app.ktor.plugins

import io.ktor.server.application.*
import org.akira.otuskotlin.ads.logging.common.AdLoggerProvider

actual fun Application.getLoggerProviderConf(): AdLoggerProvider =
    throw Exception()

