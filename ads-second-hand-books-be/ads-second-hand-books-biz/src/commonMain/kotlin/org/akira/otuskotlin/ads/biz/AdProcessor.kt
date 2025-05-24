package org.akira.otuskotlin.ads.biz

import org.akira.otuskotlin.ads.biz.general.initStatus
import org.akira.otuskotlin.ads.biz.general.operation
import org.akira.otuskotlin.ads.biz.general.stubs
import org.akira.otuskotlin.ads.biz.stubs.*
import org.akira.otuskotlin.ads.common.AdContext
import org.akira.otuskotlin.ads.common.AdCorSettings
import org.akira.otuskotlin.ads.common.models.AdCommand
import org.akira.otuskotlin.ads.cor.ICorExec
import org.akira.otuskotlin.ads.cor.rootChain

@Suppress("unused", "RedundantSuspendModifier")
class AdProcessor(
    private val corSettings: AdCorSettings = AdCorSettings.NONE
) {
    suspend fun exec(ctx: AdContext) = businessChain.exec(ctx.also { it.corSettings = corSettings })

    private val businessChain: ICorExec<AdContext> = rootChain {
        initStatus("Инициализация статуса")

        operation("Создание объявления", AdCommand.CREATE) {
            stubs("Обработка стабов") {
                stubCreateSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadTitle("Имитация ошибки валидации заголовка")
                stubValidationBadAuthors("Имитиация ошибки валидации авторов")
                stubValidationBadPrice("Имитация ошибки валидации цены")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
        }
        operation("Получить объявление", AdCommand.READ) {
            stubs("Обработка стабов") {
                stubReadSuccess("Имитиция успешной обработки", corSettings)
                stubValidationBadId("Имитация ошибки валидации id")
                stubReadNotFound("Имитиция отсутствия записи")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
        }
        operation("Изменить объявление", AdCommand.UPDATE) {
            stubs("Обработка стабов") {
                stubUpdateSuccess("Имитация успешной обработки",corSettings)
                stubValidationBadId("Имитация ошибки валидации id")
                stubValidationBadTitle("Имитация ошибки валидации заголовка")
                stubValidationBadAuthors("Имитиация ошибки валидации авторов")
                stubValidationBadPrice("Имитация ошибки валидации цены")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
        }
        operation("Удалить объявление", AdCommand.DELETE) {
            stubs("Обработка стабов") {
                stubDeleteSuccess("Имитация успешной обработки", corSettings)
                stubCannotDelete("Ошибка удаления записи")
                stubValidationBadId("Имитация ошибки валидации id")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
        }
        operation("Поиск объявлений", AdCommand.SEARCH) {
            stubs("Обработка стабов") {
                stubSearchSuccess("Имитация успешной обработки", corSettings)
                stubSearchBadSearchString("Имитация ошибки поисковой строки")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
        }
    }.build()
}