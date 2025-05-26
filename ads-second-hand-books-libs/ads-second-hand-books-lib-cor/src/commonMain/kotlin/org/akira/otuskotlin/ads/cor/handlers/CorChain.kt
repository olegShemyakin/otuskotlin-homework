package org.akira.otuskotlin.ads.cor.handlers

import org.akira.otuskotlin.ads.cor.CorDslMarker
import org.akira.otuskotlin.ads.cor.ICorChainDsl
import org.akira.otuskotlin.ads.cor.ICorExec
import org.akira.otuskotlin.ads.cor.ICorExecDsl

/**
 * Реализация цепочки, исполняет свои worker и вложенные цепочки
 */
class CorChain<T>(
    private val execs: List<ICorExec<T>>,
    title: String,
    description: String = "",
    blockOn: suspend T.() -> Boolean = { true },
    blockExcept: suspend T.(Throwable) -> Unit = {}
) : AbstractCorExec<T>(title, description, blockOn, blockExcept) {
    override suspend fun handle(ctx: T) {
        execs.forEach {
            it.exec(ctx)
        }
    }
}

@CorDslMarker
class CorChainDsl<T> : CorExecDsl<T>(), ICorChainDsl<T> {
    private val workers: MutableList<ICorExecDsl<T>> = mutableListOf()

    override fun add(worker: ICorExecDsl<T>) {
        workers.add(worker)
    }

    override fun build(): ICorExec<T> = CorChain(
        title = title,
        description = description,
        execs = workers.map { it.build() },
        blockOn = blockOn,
        blockExcept = blockExcept
    )
}