package org.akira.otuskotlin.ads.common.helpers

import org.akira.otuskotlin.ads.common.AdContext
import org.akira.otuskotlin.ads.common.models.AdError
import org.akira.otuskotlin.ads.common.models.AdState

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

inline fun AdContext.addError(vararg error: AdError) = errors.addAll(error)

inline fun AdContext.fail(error: AdError) {
    addError(error)
    state = AdState.FAILING
}