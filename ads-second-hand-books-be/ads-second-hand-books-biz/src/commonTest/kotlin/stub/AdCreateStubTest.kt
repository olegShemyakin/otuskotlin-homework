package org.akira.otuskotlin.ads.biz.stub

import com.ionspin.kotlin.bignum.decimal.BigDecimal
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

class AdCreateStubTest {
    private val processor = AdProcessor()
    private val id = AdId("1ad61c11-a84d-4bb2-af82-e0481e42015d")
    private val title = "title book"
    private val authors = "authors book"
    private val publishing = "publishing book"
    private val year = 2025
    private val price = BigDecimal.parseString("500.00")

    @Test
    fun create() = runTest {
        val ctx = AdContext(
            command = AdCommand.CREATE,
            workMode = AdWorkMode.STUB,
            stubCase = AdsStubs.SUCCESS,
            adRequest = Ad(
                id = id,
                title = title,
                authors = authors,
                publishing = publishing,
                year = year,
                price = price
            )
        )
        processor.exec(ctx)
        val response = ctx.adResponse
        assertEquals(AdStub.get().id, response.id)
        assertEquals(title, response.title)
        assertEquals(authors, response.authors)
        assertEquals(publishing, response.publishing)
        assertEquals(year, response.year)
        assertEquals(price, response.price)
    }

    @Test
    fun badTitle() = runTest {
        val ctx = AdContext(
            command = AdCommand.CREATE,
            workMode = AdWorkMode.STUB,
            stubCase = AdsStubs.BAD_TITLE,
            adRequest = Ad(
                id = id,
                authors = authors,
                publishing = publishing,
                year = year,
                price = price
            )
        )
        processor.exec(ctx)
        assertEquals(Ad(), ctx.adResponse)
        assertEquals("title", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badAuthors() = runTest {
        val ctx = AdContext(
            command = AdCommand.CREATE,
            workMode = AdWorkMode.STUB,
            stubCase = AdsStubs.BAD_AUTHORS,
            adRequest = Ad(
                id = id,
                authors = authors,
                publishing = publishing,
                year = year,
                price = price
            )
        )
        processor.exec(ctx)
        assertEquals(Ad(), ctx.adResponse)
        assertEquals("authors", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badPrice() = runTest {
        val ctx = AdContext(
            command = AdCommand.CREATE,
            workMode = AdWorkMode.STUB,
            stubCase = AdsStubs.BAD_PRICE,
            adRequest = Ad(
                id = id,
                title = title,
                authors = authors,
                publishing = publishing,
                year = year
            )
        )
        processor.exec(ctx)
        assertEquals(Ad(), ctx.adResponse)
        assertEquals("price", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = AdContext(
            command = AdCommand.CREATE,
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
            stubCase = AdsStubs.BAD_ID,
            adRequest = Ad(
                id = id,
                title = title,
                authors = authors,
                publishing = publishing,
                year = year
            )
        )
        processor.exec(ctx)
        assertEquals(Ad(), ctx.adResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
}