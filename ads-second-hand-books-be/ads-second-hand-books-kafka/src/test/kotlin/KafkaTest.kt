package org.akira.otuskotlin.ads.app.kafka

import org.akira.otuskotlin.ads.api.apiRequestSerialize
import org.akira.otuskotlin.ads.api.apiResponseDeserialize
import org.akira.otuskotlin.ads.api.models.*
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.clients.consumer.MockConsumer
import org.apache.kafka.clients.consumer.OffsetResetStrategy
import org.apache.kafka.clients.producer.MockProducer
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.StringSerializer
import java.util.Collections
import kotlin.test.Test
import kotlin.test.assertEquals

class KafkaTest {

    @Test
    fun testKafka() {
        val consumer = MockConsumer<String, String>(OffsetResetStrategy.EARLIEST)
        val producer = MockProducer<String, String>(true, StringSerializer(), StringSerializer())
        val config = AppKafkaConfig()
        val inputTopic = config.kafkaTopicInV1
        val outputTopic = config.kafkaTopicOutV1

        val app = AppKafkaConsumer(config, listOf(ConsumerStrategyV1()), consumer, producer)
        consumer.schedulePollTask {
            consumer.rebalance(Collections.singletonList(TopicPartition(inputTopic, 0)))
            consumer.addRecord(
                ConsumerRecord(
                    inputTopic,
                    PARTITION,
                    0L,
                    "5526d2b9-44ae-4a5f-8888-c5ae405bcb60",
                    apiRequestSerialize(
                        AdCreateRequest(
                            ad = AdCreateObject(
                                title = "Имя ветра",
                                authors = "Патрик Ротфусс",
                                publishing = "fanzon",
                                year = 2021,
                                price = "700.00",
                                typeAd = TypeAd.PROPOSAL,
                            ),
                            debug = AdDebug(
                                mode = AdRequestDebugMode.STUB,
                                stub = AdRequestDebugStubs.SUCCESS
                            )
                        )
                    )
                )
            )
            app.close()
        }

        val startOffsets: MutableMap<TopicPartition, Long> = mutableMapOf()
        val tp = TopicPartition(inputTopic, PARTITION)
        startOffsets[tp] = 0L
        consumer.updateBeginningOffsets(startOffsets)

        app.start()

        val message: ProducerRecord<String, String> = producer.history().first()
        val result: AdCreateResponse = apiResponseDeserialize(message.value())
        assertEquals(outputTopic, message.topic())
        assertEquals(TITLE_EXCPECTED, result.ad?.title)
    }

    companion object {
        const val PARTITION = 0
        const val TITLE_EXCPECTED = "Имя ветра"
    }
}