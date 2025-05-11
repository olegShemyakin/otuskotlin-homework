package org.akira.otuskotlin.ads.common

import org.akira.otuskotlin.ads.logging.common.AdLoggerProvider

data class AdCorSettings(
    val loggerProvider: AdLoggerProvider = AdLoggerProvider()
) {
    companion object {
        val NONE = AdCorSettings()
    }
}
