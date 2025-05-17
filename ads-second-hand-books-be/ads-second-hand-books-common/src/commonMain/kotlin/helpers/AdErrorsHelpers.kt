package org.akira.otuskotlin.ads.common.helpers

import org.akira.otuskotlin.ads.common.models.AdError

fun Throwable.asAdError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: ""
) = AdError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this
)