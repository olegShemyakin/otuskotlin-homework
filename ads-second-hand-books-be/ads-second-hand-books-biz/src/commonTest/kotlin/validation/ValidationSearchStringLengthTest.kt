package org.akira.otuskotlin.ads.biz.validation

import kotlinx.coroutines.test.runTest
import org.akira.otuskotlin.ads.common.AdContext
import org.akira.otuskotlin.ads.common.models.AdFilter
import org.akira.otuskotlin.ads.common.models.AdState
import org.akira.otuskotlin.ads.cor.rootChain
import kotlin.test.Test
import kotlin.test.assertEquals

class ValidationSearchStringLengthTest {

    @Test
    fun emptyString() = runTest {
        val ctx = AdContext(
            state = AdState.RUNNING,
            adFilterValidating = AdFilter(searchString = "")
        )
        chain.exec(ctx)
        assertEquals(AdState.RUNNING, ctx.state)
        assertEquals(0, ctx.errors.size)
    }

    @Test
    fun blankString() = runTest {
        val ctx = AdContext(
            state = AdState.RUNNING,
            adFilterValidating = AdFilter(searchString = " ")
        )
        chain.exec(ctx)
        assertEquals(AdState.RUNNING, ctx.state)
        assertEquals(0, ctx.errors.size)
    }

    @Test
    fun shortString() = runTest {
        val ctx = AdContext(
            state = AdState.RUNNING,
            adFilterValidating = AdFilter(searchString = "ab")
        )
        chain.exec(ctx)
        assertEquals(AdState.FAILING, ctx.state)
        assertEquals(1, ctx.errors.size)
        assertEquals("validation-searchString-tooShort", ctx.errors.first().code)
    }

    @Test
    fun normalString() = runTest {
        val ctx = AdContext(
            state = AdState.RUNNING,
            adFilterValidating = AdFilter(searchString = "abc")
        )
        chain.exec(ctx)
        assertEquals(AdState.RUNNING, ctx.state)
        assertEquals(0, ctx.errors.size)
    }

    @Test
    fun longString() = runTest {
        val ctx = AdContext(
            state = AdState.RUNNING,
            adFilterValidating = AdFilter(searchString = "ab".repeat(51))
        )
        chain.exec(ctx)
        assertEquals(AdState.FAILING, ctx.state)
        assertEquals(1, ctx.errors.size)
        assertEquals("validation-searchString-tooLong", ctx.errors.first().code)
    }

    companion object {
        val chain = rootChain {
            validateSearchString("")
        }.build()
    }
}