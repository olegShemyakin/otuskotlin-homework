package org.akira.otuskotlin.ads.biz.validation

import com.ionspin.kotlin.bignum.decimal.BigDecimal
import kotlinx.coroutines.test.runTest
import org.akira.otuskotlin.ads.biz.AdProcessor
import org.akira.otuskotlin.ads.common.AdContext
import org.akira.otuskotlin.ads.common.models.*
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

fun validationLockCorrect(command: AdCommand, processor: AdProcessor) = runTest {
    val ctx = AdContext(
        command = command,
        state = AdState.NONE,
        workMode = AdWorkMode.TEST,
        adRequest = Ad(
            id = AdId("3d4a89c8-25d8-4bee-998e-ecd7a3eec46c"),
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

fun validationLockTrim(command: AdCommand, processor: AdProcessor) = runTest {
    val ctx = AdContext(
        command = command,
        state = AdState.NONE,
        workMode = AdWorkMode.TEST,
        adRequest = Ad(
            id = AdId("3d4a89c8-25d8-4bee-998e-ecd7a3eec46c"),
            title = "test",
            authors = "test",
            publishing = "test",
            year = 2025,
            adType = AdType.DEMAND,
            lock = AdLock("\n\t c2efa5bf-00f8-4da3-bbe1-6cc68429dd6b \n\t"),
            price = BigDecimal.parseString("700.00")
        )
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(AdState.FAILING, ctx.state)
}

fun validationLockEmpty(command: AdCommand, processor: AdProcessor) = runTest {
    val ctx = AdContext(
        command = command,
        state = AdState.NONE,
        workMode = AdWorkMode.TEST,
        adRequest = Ad(
            id = AdId("3d4a89c8-25d8-4bee-998e-ecd7a3eec46c"),
            title = "test",
            authors = "test",
            publishing = "test",
            year = 2025,
            adType = AdType.DEMAND,
            lock = AdLock(""),
            price = BigDecimal.parseString("700.00")
        )
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(AdState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("lock", error?.field)
    assertContains(error?.message ?: "", "lock")
}

fun validationLockFormat(command: AdCommand, processor: AdProcessor) = runTest {
    val ctx = AdContext(
        command = command,
        state = AdState.NONE,
        workMode = AdWorkMode.TEST,
        adRequest = Ad(
            id = AdId("3d4a89c8-25d8-4bee-998e-ecd7a3eec46c"),
            title = "test",
            authors = "test",
            publishing = "test",
            year = 2025,
            adType = AdType.DEMAND,
            lock = AdLock("!@#\$%^&*(),.{}"),
            price = BigDecimal.parseString("700.00")
        )
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(AdState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("lock", error?.field)
    assertContains(error?.message ?: "", "lock")
}