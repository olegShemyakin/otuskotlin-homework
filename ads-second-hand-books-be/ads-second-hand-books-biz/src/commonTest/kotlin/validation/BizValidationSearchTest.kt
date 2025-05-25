package org.akira.otuskotlin.ads.biz.validation

import kotlinx.coroutines.test.runTest
import org.akira.otuskotlin.ads.common.AdContext
import org.akira.otuskotlin.ads.common.models.AdCommand
import org.akira.otuskotlin.ads.common.models.AdFilter
import org.akira.otuskotlin.ads.common.models.AdState
import org.akira.otuskotlin.ads.common.models.AdWorkMode
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class BizValidationSearchTest : BazeBizValidationTest() {
    override val command = AdCommand.SEARCH

    @Test
    fun correctEmpty() = runTest {
        val ctx = AdContext(
            command = command,
            state = AdState.NONE,
            workMode = AdWorkMode.TEST,
            adFilterRequest = AdFilter()
        )
        processor.exec(ctx)
        assertEquals(0, ctx.errors.size)
        assertNotEquals(AdState.FAILING, ctx.state)
    }
}