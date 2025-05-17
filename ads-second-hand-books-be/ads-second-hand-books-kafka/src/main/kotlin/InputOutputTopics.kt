package org.akira.otuskotlin.ads.app.kafka


data class InputOutputTopics(
    /**
     *  Входящий топик
     */
    val input: String,
    /**
     * Исходящий топик
     */
    val output: String
)
