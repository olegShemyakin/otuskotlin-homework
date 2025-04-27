package org.akira.otuskotlin.ads.common

import kotlinx.datetime.Instant

private val INSTANT_NONE = Instant.fromEpochMilliseconds(Long.MIN_VALUE)
val Instant.Companion.NONE
    get() = INSTANT_NONE