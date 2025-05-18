package org.akira.otuskotlin.ads.cor

import kotlinx.coroutines.test.runTest
import org.akira.otuskotlin.ads.cor.TestContext.CorStatuses
import org.akira.otuskotlin.ads.cor.handlers.CorWorker
import kotlin.test.Test
import kotlin.test.assertEquals

class CorWorkerTest {

    @Test
    fun worker_execute_handle_test() = runTest {
        val worker = CorWorker<TestContext>(
            title = "w1",
            blockHandle = { history += "w1; " }
        )
        val ctx = TestContext()
        worker.exec(ctx)
        assertEquals("w1; ", ctx.history)
    }

    @Test
    fun worker_not_execute_handle_test() = runTest {
        val worker = CorWorker<TestContext>(
            title = "w1",
            blockOn = { status == CorStatuses.ERROR },
            blockHandle = { history += "w1; " }
        )
        val ctx = TestContext()
        worker.exec(ctx)
        assertEquals("", ctx.history)
    }

    @Test
    fun worker_handle_exception_test() = runTest {
        val worker = CorWorker<TestContext>(
            title = "w1",
            blockHandle = { throw RuntimeException("some error") },
            blockExcept = { e -> history += e.message }
        )
        val ctx = TestContext()
        worker.exec(ctx)
        assertEquals("some error", ctx.history)
    }
}