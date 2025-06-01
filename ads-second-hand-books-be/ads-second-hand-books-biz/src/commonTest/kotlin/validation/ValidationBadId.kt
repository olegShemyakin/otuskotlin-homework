package org.akira.otuskotlin.ads.biz.validation

import com.ionspin.kotlin.bignum.decimal.BigDecimal
import kotlinx.coroutines.test.runTest
import org.akira.otuskotlin.ads.biz.AdProcessor
import org.akira.otuskotlin.ads.common.AdContext
import org.akira.otuskotlin.ads.common.models.*
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

fun validationIdCorrect(command: AdCommand, processor: AdProcessor) = runTest {
    val ctx = AdContext(
        command = command,
        state = AdState.NONE,
        workMode = AdWorkMode.TEST,
        adRequest = Ad(
            id = AdId("f545e018-4a62-48da-abe6-52e3000a8e1e"),
            title = "test",
            authors = "test",
            publishing = "test",
            year = 2025,
            adType = AdType.DEMAND,
            lock = AdLock("c2efa5bf-00f8-4da3-bbe1-6cc68429dd6b"),
            price = BigDecimal.parseString("700.00")
        )
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(AdState.FAILING, ctx.state)
}

fun validationIdTrim(command: AdCommand, processor: AdProcessor) = runTest {
    val ctx = AdContext(
        command = command,
        state = AdState.NONE,
        workMode = AdWorkMode.TEST,
        adRequest = Ad(
            id = AdId("\n\t f545e018-4a62-48da-abe6-52e3000a8e1e \n\t"),
            title = "test",
            authors = "test",
            publishing = "test",
            year = 2025,
            adType = AdType.DEMAND,
            lock = AdLock("c2efa5bf-00f8-4da3-bbe1-6cc68429dd6b"),
            price = BigDecimal.parseString("700.00")
        )
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(AdState.FAILING, ctx.state)
}

fun validationIdEmpty(command: AdCommand, processor: AdProcessor) = runTest {
    val ctx = AdContext(
        command = command,
        state = AdState.NONE,
        workMode = AdWorkMode.TEST,
        adRequest = Ad(
            id = AdId(""),
            title = "test",
            authors = "test",
            publishing = "test",
            year = 2025,
            adType = AdType.DEMAND,
            lock = AdLock("c2efa5bf-00f8-4da3-bbe1-6cc68429dd6b"),
            price = BigDecimal.parseString("700.00")
        )
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(AdState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("id", error?.field)
    assertContains(error?.message ?: "", "id")
}

fun validationIdFormat(command: AdCommand, processor: AdProcessor) = runTest {
    val ctx = AdContext(
        command = command,
        state = AdState.NONE,
        workMode = AdWorkMode.TEST,
        adRequest = Ad(
            id = AdId("{f545e018-4a62-48da-abe6-52e3000a8e1e}"),
            title = "test",
            authors = "test",
            publishing = "test",
            year = 2025,
            adType = AdType.DEMAND,
            lock = AdLock("c2efa5bf-00f8-4da3-bbe1-6cc68429dd6b"),
            price = BigDecimal.parseString("700.00")
        )
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(AdState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("id", error?.field)
    assertContains(error?.message ?: "", "id")
}