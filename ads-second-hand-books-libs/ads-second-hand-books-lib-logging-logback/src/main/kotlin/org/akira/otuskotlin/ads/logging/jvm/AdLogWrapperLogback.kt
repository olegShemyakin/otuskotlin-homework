package org.akira.otuskotlin.ads.logging.jvm

import ch.qos.logback.classic.Logger
import net.logstash.logback.argument.StructuredArguments
import org.akira.otuskotlin.ads.logging.common.IAdLogWrapper
import org.akira.otuskotlin.ads.logging.common.LogLevel
import org.slf4j.Marker
import org.slf4j.event.KeyValuePair
import org.slf4j.event.Level
import org.slf4j.event.LoggingEvent
import java.time.Instant

class AdLogWrapperLogback(
    /**
     * Экземпляр логера (Logback)
     */
    val logger: Logger,
    /**
     * Идентификатора логера. Пробрасывается в Logback и замещает loggerClass. Также использеутся в сообщениях
     * логера о входе и выходе из функции.
     */
    override val loggerId: String = logger.name
) : IAdLogWrapper {

    private fun log(
        msg: String = "",
        level: Level = Level.TRACE,
        marker: Marker = DefaultMarker("DEV"),
        e: Throwable? = null,
        data: Any? = null,
        objs: Map<String, Any>? = null
    ) {
        logger.log(object : LoggingEvent {
            override fun getLevel(): Level = level
            override fun getLoggerName(): String = logger.name
            override fun getMessage(): String = msg
            override fun getArguments(): MutableList<Any> = argumentArray.toMutableList()
            override fun getArgumentArray(): Array<out Any> = data
                ?.let { d ->
                    listOfNotNull(
                        objs?.map { StructuredArguments.keyValue(it.key, it.value) }?.toTypedArray(),
                        StructuredArguments.keyValue("data", d)
                    ).toTypedArray()
                }
                ?: objs?.mapNotNull { StructuredArguments.keyValue(it.key, it.value) }?.toTypedArray()
                ?: emptyArray()

            override fun getMarkers(): MutableList<Marker> = mutableListOf(marker)
            override fun getKeyValuePairs(): MutableList<KeyValuePair> = objs
                ?.mapNotNull {
                    it.let { KeyValuePair(it.key, it.value) }
                }
                ?.toMutableList()
                ?: mutableListOf()

            override fun getThrowable() = e
            override fun getTimeStamp(): Long = Instant.now().toEpochMilli()
            override fun getThreadName(): String = Thread.currentThread().name
        })
    }

    override fun log(
        msg: String,
        level: LogLevel,
        marker: String,
        e: Throwable?,
        data: Any?,
        objs: Map<String, Any>?
    ) = log(
        msg = msg,
        level = level.toSlf(),
        marker = DefaultMarker(marker),
        e = e,
        data = data,
        objs = objs
    )


    private fun LogLevel.toSlf(): Level  = when (this) {
        LogLevel.ERROR -> Level.ERROR
        LogLevel.WARN -> Level.WARN
        LogLevel.INFO -> Level.INFO
        LogLevel.DEBUG -> Level.DEBUG
        LogLevel.TRACE -> Level.TRACE
    }
}