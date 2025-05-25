package org.akira.otuskotlin.ads.biz.validation

import kotlinx.datetime.Clock
import kotlinx.datetime.toLocalDateTime
import org.akira.otuskotlin.ads.common.AdContext
import org.akira.otuskotlin.ads.common.helpers.errorValidation
import org.akira.otuskotlin.ads.common.helpers.fail
import org.akira.otuskotlin.ads.cor.ICorChainDsl
import org.akira.otuskotlin.ads.cor.worker

fun ICorChainDsl<AdContext>.validateYearRange(title: String) = worker {
    this.title = title
    on {
        adValidating.year < 0 || adValidating.year > Clock.System.now()
                    .toLocalDateTime(kotlinx.datetime.TimeZone.currentSystemDefault())
                    .year
    }
    handle {
        fail(
            errorValidation(
                field = "year",
                violationCode = "wrongDate",
                description = "year must be in range 0..now"
            )
        )
    }
}