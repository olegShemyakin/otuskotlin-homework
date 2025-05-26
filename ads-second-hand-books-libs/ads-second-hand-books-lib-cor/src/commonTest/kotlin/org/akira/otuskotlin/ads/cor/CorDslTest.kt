package org.akira.otuskotlin.ads.cor

import kotlinx.coroutines.test.runTest
import org.akira.otuskotlin.ads.cor.TestContext.CorStatuses
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class CorDslTest {

    private suspend fun execute(dsl: ICorExecDsl<TestContext>): TestContext {
        val ctx = TestContext()
        dsl.build().exec(ctx)
        return ctx
    }

    @Test
    fun handle_execute_test() = runTest {
        val chain = rootChain<TestContext> {
            worker {
                handle { history += "w1; " }
            }
        }
        val ctx = execute(chain)
        assertEquals("w1; ", ctx.history)
    }

    @Test
    fun on_check_test() = runTest {
        val chain = rootChain<TestContext> {
            worker {
                on { status == CorStatuses.ERROR }
                handle { history += "w1; " }
            }
            worker {
                on { status == CorStatuses.NONE }
                handle {
                    history += "w2; "
                    status = CorStatuses.FAILING
                }
            }
            worker {
                on { status == CorStatuses.FAILING }
                handle { history += "w3; " }
            }
        }
        val ctx = execute(chain)
        assertEquals("w2; w3; ", ctx.history)
    }

    @Test
    fun execute_exception_test() = runTest {
        val chain = rootChain<TestContext> {
            worker {
                handle { throw RuntimeException("some error") }
                except { history += it.message }
            }
        }
        val ctx = execute(chain)
        assertEquals("some error", ctx.history)
    }

    @Test
    fun execute_exception_throw_test() = runTest {
        val chain = rootChain<TestContext> {
            worker("throw") { throw RuntimeException("some error") }
        }
        assertFails {
            execute(chain)
        }
    }

    @Test
    fun complex_chain_test() = runTest {
        val chain = rootChain<TestContext> {
            worker {
                title = "Проверка статуса"
                description = "Проверяем начальный статус"

                on { status == CorStatuses.NONE }
                handle { status = CorStatuses.RUNNING }
                except { status = CorStatuses.ERROR }
            }

            chain {
                on { status == CorStatuses.RUNNING }

                worker(
                    title = "Обработчик",
                    description = "Обработка some"
                ) {
                    some += 4
                }
            }

            printResult()
        }.build()

        val ctx = TestContext()
        chain.exec(ctx)
        println("Complete: $ctx")
        assertEquals(4, ctx.some)
    }

    private fun ICorChainDsl<TestContext>.printResult() = worker(title = "Print example") {
        println("some = $some")
    }
}