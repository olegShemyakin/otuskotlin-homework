package org.akira.otuskotlin.ads.biz.validation

import org.akira.otuskotlin.ads.common.models.AdCommand
import kotlin.test.Test

class BizValidationCreateTest : BazeBizValidationTest() {
    override val command: AdCommand = AdCommand.CREATE

    @Test fun correctTitle() = validationTitleCorrect(command, processor)
    @Test fun trimTitle() = validationTitleTrim(command, processor)
    @Test fun emptyTitle() = validationTitleEmpty(command, processor)
    @Test fun badSymbolsTitle() = validationTitleSymbols(command, processor)

    @Test fun correctAuthors() = validationAuthorsCorrect(command, processor)
    @Test fun trimAuthors() = validationAuthorsTrim(command, processor)
    @Test fun emptyAuthors() = validationAuthorsEmpty(command, processor)
    @Test fun badSymbolsAuthors() = validationAuthorsSymbols(command, processor)

    @Test fun correctPublishing() = validationPublishingCorrect(command, processor)
    @Test fun trimPublishing() = validationPublishingTrim(command, processor)
    @Test fun emptyPublishing() = validationPublishingEmpty(command, processor)
    @Test fun badSymbolsPublishing() = validationPublishingSymbols(command, processor)

    @Test fun correctYear() = validationYearCorrect(command, processor)
    @Test fun negativeYear() = validationYearNegative(command, processor)
    @Test fun futureYear() = validationYearFuture(command, processor)

    @Test fun correctPrice() = validationPriceCorrect(command, processor)
    @Test fun negativePrice() = validationPriceNegative(command, processor)
}