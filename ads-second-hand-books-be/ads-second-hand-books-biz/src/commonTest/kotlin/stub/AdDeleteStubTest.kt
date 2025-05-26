package org.akira.otuskotlin.ads.biz.stub

import kotlinx.coroutines.test.runTest
import org.akira.otuskotlin.ads.biz.AdProcessor
import org.akira.otuskotlin.ads.common.AdContext
import org.akira.otuskotlin.ads.common.models.Ad
import org.akira.otuskotlin.ads.common.models.AdCommand
import org.akira.otuskotlin.ads.common.models.AdId
import org.akira.otuskotlin.ads.common.models.AdWorkMode
import org.akira.otuskotlin.ads.common.stubs.AdsStubs
import org.akira.otuskotlin.ads.stubs.AdStub
import kotlin.test.Test
import kotlin.test.assertEquals

class AdDeleteStubTest {
    private val processor = AdProcessor()
    private val id = AdId("1ad61c11-a84d-4bb2-af82-e0481e42015d")

    @Test
    fun delete() = runTest {
        val ctx = AdContext(
            command = AdCommand.DELETE,
            workMode = AdWorkMode.STUB,
            stubCase = AdsStubs.SUCCESS,
            adRequest = Ad(
                id = id
            )
        )
        processor.exec(ctx)
        val response = ctx.adResponse
        with(AdStub.get()) {
            assertEquals(id, response.id)
            assertEquals(title, response.title)
            assertEquals(authors, response.authors)
            assertEquals(publishing, response.publishing)
            assertEquals(year, response.year)
            assertEquals(price, response.price)
            assertEquals(adType, response.adType)
        }
    }

    @Test
    fun badId() = runTest {
        val ctx = AdContext(
            command = AdCommand.DELETE,
            workMode = AdWorkMode.STUB,
            stubCase = AdsStubs.BAD_ID,
            adRequest = Ad()
        )
        processor.exec(ctx)
        assertEquals(Ad(), ctx.adResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun cannotDelete() = runTest {
        val ctx = AdContext(
            command = AdCommand.DELETE,
            workMode = AdWorkMode.STUB,
            stubCase = AdsStubs.CANNOT_DELETE,
            adRequest = Ad(
                id = id
            )
        )
        processor.exec(ctx)
        assertEquals(Ad(), ctx.adResponse)
        assertEquals("delete", ctx.errors.firstOrNull()?.group)
        assertEquals("can not delete id: ${id}", ctx.errors.firstOrNull()?.message)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = AdContext(
            command = AdCommand.DELETE,
            workMode = AdWorkMode.STUB,
            stubCase = AdsStubs.DB_ERROR,
            adRequest = Ad(
                id = id
            )
        )
        processor.exec(ctx)
        assertEquals(Ad(), ctx.adResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = AdContext(
            command = AdCommand.CREATE,
            workMode = AdWorkMode.STUB,
            stubCase = AdsStubs.BAD_TITLE,
            adRequest = Ad(
                id = id,
            )
        )
        processor.exec(ctx)
        assertEquals(Ad(), ctx.adResponse)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
}