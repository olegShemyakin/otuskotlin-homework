package org.akira.otuskotlin.ads.common.models

import kotlin.jvm.JvmInline

@JvmInline
value class AdId(private val id: String) {
    fun asString() = id

    companion object {
        val NONE = AdId("")
    }
}