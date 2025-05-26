package org.akira.otuskotlin.ads.biz.validation

import org.akira.otuskotlin.ads.common.AdContext
import org.akira.otuskotlin.ads.common.helpers.errorValidation
import org.akira.otuskotlin.ads.common.helpers.fail
import org.akira.otuskotlin.ads.cor.ICorChainDsl
import org.akira.otuskotlin.ads.cor.worker

fun ICorChainDsl<AdContext>.validatePriceValue(title: String) = worker {
    this.title = title
    on { this.adValidating.price.isNegative }
    handle {
        fail(
            errorValidation(
                field = "price",
                violationCode = "badValue",
                description = "field must not be negative"
            )
        )
    }
}