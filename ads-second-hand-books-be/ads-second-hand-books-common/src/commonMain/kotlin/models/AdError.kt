package org.akira.otuskotlin.ads.common.models

data class AdError(
    val code: String = "",
    val group: String = "",
    val field: String = "",
    val message: String = "",
    val exception: Throwable? = null
)
