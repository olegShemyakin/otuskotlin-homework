package org.akira.otuskotlin.ads.app.kafka

import org.akira.otuskotlin.ads.api.apiRequestDeserialize
import org.akira.otuskotlin.ads.api.apiResponseSerialize
import org.akira.otuskotlin.ads.api.models.IRequest
import org.akira.otuskotlin.ads.api.models.IResponse
import org.akira.otuskotlin.ads.common.AdContext
import org.akira.otuskotlin.ads.mappers.fromTransport
import org.akira.otuskotlin.ads.mappers.toTransport

class ConsumerStrategyV1 : IConsumerStrategy {
    override fun topics(config: AppKafkaConfig): InputOutputTopics {
        return InputOutputTopics(config.kafkaTopicInV1, config.kafkaTopicOutV1)
    }

    override fun serialize(source: AdContext): String {
        val response: IResponse = source.toTransport()
        return apiResponseSerialize(response)
    }

    override fun deserialize(value: String, target: AdContext) {
        val request: IRequest = apiRequestDeserialize(value)
        target.fromTransport(request)
    }
}