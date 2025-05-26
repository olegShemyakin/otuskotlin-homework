package org.akira.otuskotlin.ads.biz.validation

import org.akira.otuskotlin.ads.common.models.AdCommand
import kotlin.test.Test

class BizValidationReadTest : BazeBizValidationTest( ){
    override val command: AdCommand = AdCommand.READ

    @Test fun correctId() = validationIdCorrect(command, processor)
    @Test fun trimId() = validationIdTrim(command, processor)
    @Test fun emptyId() = validationIdEmpty(command, processor)
    @Test fun badFormatId() = validationIdFormat(command, processor)
}