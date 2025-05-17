package org.akira.otuskotlin.ads.logging.common

enum class LogLevel(
    private val levelInt: Int,
    private val levelStr: String
) {
    ERROR(40, "ERROR"),
    WARN(30, "WARN"),
    INFO(20, "INFO"),
    DEBUG(10, "DEBUG"),
    TRACE(0, "TRACE");

    fun toInt(): Int = levelInt

    override fun toString(): String = levelStr
}