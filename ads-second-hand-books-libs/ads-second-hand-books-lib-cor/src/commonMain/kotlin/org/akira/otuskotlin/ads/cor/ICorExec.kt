package org.akira.otuskotlin.ads.cor

/**
 * Интерфейс для обработки контекста.
 */
interface ICorExec<T> {
    val title: String
    val description: String
    suspend fun exec(ctx: T)
}