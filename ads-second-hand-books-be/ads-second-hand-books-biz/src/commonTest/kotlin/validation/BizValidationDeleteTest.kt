package org.akira.otuskotlin.ads.biz.validation

import org.akira.otuskotlin.ads.common.models.AdCommand
import kotlin.test.Test

class BizValidationDeleteTest : BazeBizValidationTest() {
    override val command: AdCommand = AdCommand.DELETE

    @Test fun correctId() = validationIdCorrect(command, processor)
    @Test fun trimId() = validationIdTrim(command, processor)
    @Test fun emptyId() = validationIdEmpty(command, processor)
    @Test fun badFormatId() = validationIdFormat(command, processor)

    @Test fun correctLock() = validationLockCorrect(command, processor)
    @Test fun trimLock() = validationLockTrim(command, processor)
    @Test fun emptyLock() = validationLockEmpty(command, processor)
    @Test fun badFormatLock() = validationLockFormat(command, processor)
}