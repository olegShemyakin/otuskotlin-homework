package org.akira.otuskotlin.ads.biz.validation

import org.akira.otuskotlin.ads.common.AdContext
import org.akira.otuskotlin.ads.common.helpers.errorValidation
import org.akira.otuskotlin.ads.common.helpers.fail
import org.akira.otuskotlin.ads.cor.ICorChainDsl
import org.akira.otuskotlin.ads.cor.worker

fun ICorChainDsl<AdContext>.validatePublishingHasContent(title: String) = worker {
    this.title = title
    this.description = """
        Проверяем, что у нас есть какие-то слова в издательстве.
        Отказываем в публикации издательства, в которых только бессмысленные символы типа %^&^$^%#^))&^*&%^^&
    """.trimIndent()
    val regExp = Regex("\\p{L}")
    on { adValidating.publishing.isNotEmpty() && !adValidating.publishing.contains(regExp) }
    handle {
        fail(
            errorValidation(
                field = "publishing",
                violationCode = "noContent",
                description = "field must contain letters"
            )
        )
    }
}