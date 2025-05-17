package org.akira.otuskotlin.ads.app.ktor.plugins

import io.ktor.server.application.*
import org.akira.otuskotlin.ads.logging.common.AdLoggerProvider

expect fun Application.getLoggerProviderConf(): AdLoggerProvider

