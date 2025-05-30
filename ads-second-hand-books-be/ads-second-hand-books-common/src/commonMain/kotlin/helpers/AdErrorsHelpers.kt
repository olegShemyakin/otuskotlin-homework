package org.akira.otuskotlin.ads.common.helpers

import org.akira.otuskotlin.ads.common.AdContext
import org.akira.otuskotlin.ads.common.models.AdError
import org.akira.otuskotlin.ads.common.models.AdState
import org.akira.otuskotlin.ads.logging.common.LogLevel

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
inline fun AdContext.addErrors(error: Collection<AdError>) = errors.addAll(error)

inline fun AdContext.fail(error: AdError) {
    addError(error)
    state = AdState.FAILING
}

inline fun AdContext.fail(errors: Collection<AdError>) {
    addErrors(errors)
    state = AdState.FAILING
}

inline fun errorValidation(
    field: String,
    /**
     * Код, характеризующий ошибку. Не должен включать имя поля или указание на валидацию.
     * Например: empty, badSymbols, tooLong, etc
     */
    violationCode: String,
    description: String,
    level: LogLevel = LogLevel.ERROR
) = AdError(
    code = "validation-$field-$violationCode",
    field = field,
    group = "validation",
    message = "Validation error for field $field: $description",
    level = level
)

inline fun errorSystem(
    violationCode: String,
    level: LogLevel = LogLevel.ERROR,
    e: Throwable
) = AdError(
    code = "system-$violationCode",
    group = "system",
    message = "System error occurred. Our stuff has been informed, please retry later",
    level = level,
    exception = e
)