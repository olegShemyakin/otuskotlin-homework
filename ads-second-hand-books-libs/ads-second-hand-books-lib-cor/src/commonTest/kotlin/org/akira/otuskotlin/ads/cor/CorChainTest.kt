package org.akira.otuskotlin.ads.cor

import kotlinx.coroutines.test.runTest
import org.akira.otuskotlin.ads.cor.handlers.CorChain
import org.akira.otuskotlin.ads.cor.handlers.CorWorker
import kotlin.test.Test
import kotlin.test.assertEquals

class CorChainTest {

    @Test
    fun chain_execute_workes() = runTest {
        val createWorker = { title: String ->
            CorWorker<TestContext>(
                title = title,
                blockHandle = { history += "$title; " }
            )
        }
        val chain = CorChain(
            execs = listOf(createWorker("w1"), createWorker("w2")),
            title = "chain"
        )
        val ctx = TestContext()
        chain.exec(ctx)
        assertEquals("w1; w2; ", ctx.history)
    }
}