package org.akira.otuskotlin.ads.biz.validation

import com.ionspin.kotlin.bignum.decimal.BigDecimal
import kotlinx.coroutines.test.runTest
import org.akira.otuskotlin.ads.biz.AdProcessor
import org.akira.otuskotlin.ads.common.AdContext
import org.akira.otuskotlin.ads.common.models.*
import org.akira.otuskotlin.ads.stubs.AdStub
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

private val stub = AdStub.get()

fun validationPriceCorrect(command: AdCommand, processor: AdProcessor) = runTest {
    val ctx = AdContext(
        command = command,
        state = AdState.NONE,
        workMode = AdWorkMode.TEST,
        adRequest = Ad(
            id = stub.id,
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
    assertEquals(BigDecimal.parseString("700.00"), ctx.adValidated.price)
}

fun validationPriceNegative(command: AdCommand, processor: AdProcessor) = runTest {
    val ctx = AdContext(
        command = command,
        state = AdState.NONE,
        workMode = AdWorkMode.TEST,
        adRequest = Ad(
            id = stub.id,
            title = "test",
            authors = "test",
            publishing = "test",
            year = 2025,
            adType = AdType.DEMAND,
            lock = AdLock("c2efa5bf-00f8-4da3-bbe1-6cc68429dd6b"),
            price = BigDecimal.parseString("-700.00")
        )
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(AdState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("price", error?.field)
    assertContains(error?.message ?: "", "price")
}
