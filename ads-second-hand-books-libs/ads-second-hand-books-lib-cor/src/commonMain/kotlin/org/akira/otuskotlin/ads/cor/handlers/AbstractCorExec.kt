package org.akira.otuskotlin.ads.cor.handlers

import org.akira.otuskotlin.ads.cor.ICorExec
import org.akira.otuskotlin.ads.cor.ICorExecDsl

abstract class AbstractCorExec<T>(
    override val title: String,
    override val description: String = "",
    private val blockOn: suspend T.() -> Boolean = { true },
    private val blockExcept: suspend T.(Throwable) -> Unit = {}
) : ICorExec<T> {
    protected abstract suspend fun handle(ctx: T)

    private suspend fun on(ctx: T): Boolean = ctx.blockOn()
    private suspend fun except(ctx: T, e: Throwable) = ctx.blockExcept(e)


    override suspend fun exec(ctx: T) {
        if (on(ctx)) {
            try {
                handle(ctx)
            } catch (e: Throwable) {
                except(ctx, e)
            }
        }
    }
}

abstract class CorExecDsl<T> : ICorExecDsl<T> {
    protected var blockOn: suspend T.() -> Boolean = { true }
    protected var blockExcept: suspend T.(e: Throwable) -> Unit = { e: Throwable -> throw e }

    override var title: String = ""
    override var description: String = ""

    override fun except(function: suspend T.(e: Throwable) -> Unit) {
        blockExcept = function
    }

    override fun on(function: suspend T.() -> Boolean) {
        blockOn = function
    }

}

