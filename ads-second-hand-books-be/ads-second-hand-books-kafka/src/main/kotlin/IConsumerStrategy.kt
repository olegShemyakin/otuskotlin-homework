package org.akira.otuskotlin.ads.app.kafka

import org.akira.otuskotlin.ads.common.AdContext

/**
 * Интерфейс стратегии для разных версий API
 */
interface IConsumerStrategy {

    /**
     * Топики, для которых применяется стратегия
     */
    fun topics(config: AppKafkaConfig): InputOutputTopics

    /**
     * Сериализато
     */
    fun serialize(source: AdContext): String

    /**
     * Десериализатор
     */
    fun deserialize(value: String, target: AdContext)
}