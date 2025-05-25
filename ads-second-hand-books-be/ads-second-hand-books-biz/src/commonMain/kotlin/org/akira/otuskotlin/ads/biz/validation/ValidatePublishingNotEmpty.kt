package org.akira.otuskotlin.ads.biz.validation

import org.akira.otuskotlin.ads.common.AdContext
import org.akira.otuskotlin.ads.common.helpers.errorValidation
import org.akira.otuskotlin.ads.common.helpers.fail
import org.akira.otuskotlin.ads.cor.ICorChainDsl
import org.akira.otuskotlin.ads.cor.worker

fun ICorChainDsl<AdContext>.validatePublishingNotEmpty(title: String) = worker {
    this.title = title
    on { adValidating.publishing.isEmpty() }
    handle {
        fail(
            errorValidation(
                field = "publishing",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}