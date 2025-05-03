package org.akira.otuskotlin.ads.common.models

import org.akira.otuskotlin.ads.logging.common.LogLevel

data class AdError(
    val code: String = "",
    val group: String = "",
    val field: String = "",
    val message: String = "",
    val level: LogLevel = LogLevel.ERROR,
    val exception: Throwable? = null
)
