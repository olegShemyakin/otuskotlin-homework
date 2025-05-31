package org.akira.otuskotlin.ads.biz

import org.akira.otuskotlin.ads.biz.general.*
import org.akira.otuskotlin.ads.biz.general.stubs
import org.akira.otuskotlin.ads.biz.repo.*
import org.akira.otuskotlin.ads.biz.stubs.*
import org.akira.otuskotlin.ads.biz.validation.*
import org.akira.otuskotlin.ads.common.AdContext
import org.akira.otuskotlin.ads.common.AdCorSettings
import org.akira.otuskotlin.ads.common.models.AdCommand
import org.akira.otuskotlin.ads.common.models.AdId
import org.akira.otuskotlin.ads.common.models.AdLock
import org.akira.otuskotlin.ads.common.models.AdState
import org.akira.otuskotlin.ads.cor.ICorExec
import org.akira.otuskotlin.ads.cor.chain
import org.akira.otuskotlin.ads.cor.rootChain
import org.akira.otuskotlin.ads.cor.worker

@Suppress("unused", "RedundantSuspendModifier")
class AdProcessor(
    private val corSettings: AdCorSettings = AdCorSettings.NONE
) {
    suspend fun exec(ctx: AdContext) = businessChain.exec(ctx.also { it.corSettings = corSettings })

    private val businessChain: ICorExec<AdContext> = rootChain {
        initStatus("Инициализация статуса")
        initRepo("Инициализация репозитория")

        operation("Создание объявления", AdCommand.CREATE) {
            stubs("Обработка стабов") {
                stubCreateSuccess("Имитация успешной обработки", corSettings)
                stubValidationBadTitle("Имитация ошибки валидации заголовка")
                stubValidationBadAuthors("Имитиация ошибки валидации авторов")
                stubValidationBadPrice("Имитация ошибки валидации цены")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
            validation {
                worker("Копируем поля в adValidating") { adValidating = adRequest.deepCopy() }
                worker("Очистка id") { adValidating.id = AdId.NONE }
                worker("Обрезка названия") { adValidating.title = adValidating.title.trim() }
                worker("Обрезка авторов") { adValidating.authors = adValidating.authors.trim() }
                worker("Обрезка издательства") { adValidating.publishing = adValidating.publishing.trim() }
                validateTitleNotEmpty("Проверка, что заголовок не пуст")
                validateTitleHasContent("Проверка символов")
                validateAuthorsNotEmpty("Проверка, что авторы не пуст")
                validateAuthorsHasContent("Проверка символов")
                validatePublishingNotEmpty("Проверка, что издательство не пуст")
                validatePublishingHasContent("Проверка символов")
                validateYearRange("Проверка года")
                validatePriceValue("Проверка цены")

                finishAdValidation("Завершена проверка")
            }
            chain {
                title = "Логика сохранения"
                repoPrepareCreate("Подготовка объекта для сохранения")
                repoCreate("Создание объявления в БД")
            }
            prepareResult("Подготовка ответа")
        }
        operation("Получить объявление", AdCommand.READ) {
            stubs("Обработка стабов") {
                stubReadSuccess("Имитиция успешной обработки", corSettings)
                stubValidationBadId("Имитация ошибки валидации id")
                stubReadNotFound("Имитиция отсутствия записи")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
            validation {
                worker("Копируем поля в adValidating") { adValidating = adRequest.deepCopy() }
                worker("Обрезка id") { adValidating.id = AdId(adValidating.id.asString().trim()) }
                validatedIdNotEmpty("Проверка на не пустой id")
                validatedIdProperFormat("Проверка формата id")

                finishAdValidation("Успешное завершение процедуры валидации")
            }
            chain {
                title = "Логика чтения"
                repoRead("Чтение объявления из БД")
                worker {
                    title = "Подготовка ответа для Read"
                    on { state == AdState.RUNNING }
                    handle { adRepoDone = adRepoRead }
                }
            }
            prepareResult("Подготовка ответа")
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
            validation {
                worker("Копируем поля в adValidating") { adValidating = adRequest.deepCopy() }
                worker("Обрезка id") { adValidating.id = AdId(adValidating.id.asString().trim()) }
                worker("Обрезка блокировки") { adValidating.lock = AdLock(adValidating.lock.asString().trim()) }
                worker("Обрезка названия") { adValidating.title = adValidating.title.trim() }
                worker("Обрезка авторов") { adValidating.authors = adValidating.authors.trim() }
                worker("Обрезка издательства") { adValidating.publishing = adValidating.publishing.trim() }
                validatedIdNotEmpty("Проверка на непустой ic")
                validatedIdProperFormat("Проверка формата id")
                validateLockNotEmpty("Проверка на непустой lock")
                validateLockProperFormat("Проверка формата lock")
                validateTitleNotEmpty("Проверка, что заголовок не пуст")
                validateTitleHasContent("Проверка символов")
                validateAuthorsNotEmpty("Проверка, что авторы не пуст")
                validateAuthorsHasContent("Проверка символов")
                validatePublishingNotEmpty("Проверка, что издательство не пуст")
                validatePublishingHasContent("Проверка символов")
                validateYearRange("Проверка года")
                validatePriceValue("Проверка цены")

                finishAdValidation("Завершена проверка")
            }
            chain {
                title = "Логика обновления"
                repoRead("Чтение объявления из БД")
                checkLock("Проверяем консистентность по оптимистичной блокировке")
                repoPrepareUpdate("Подготовка объекта для обновления")
                repoUpdate("Обновление объявления в БД")
            }
            prepareResult("Подготовка ответа")
        }
        operation("Удалить объявление", AdCommand.DELETE) {
            stubs("Обработка стабов") {
                stubDeleteSuccess("Имитация успешной обработки", corSettings)
                stubCannotDelete("Ошибка удаления записи")
                stubValidationBadId("Имитация ошибки валидации id")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
            validation {
                worker("Копируем поля в adValidating") { adValidating = adRequest.deepCopy() }
                worker("Обрезка id") { adValidating.id = AdId(adValidating.id.asString().trim()) }
                worker("Обрезка блокировки") { adValidating.lock = AdLock(adValidating.lock.asString().trim()) }
                validatedIdNotEmpty("Проверка на непустой ic")
                validatedIdProperFormat("Проверка формата id")
                validateLockNotEmpty("Проверка на непустой lock")
                validateLockProperFormat("Проверка формата lock")

                finishAdValidation("Завершена проверка")
            }
            chain {
                title = "Логика удаления объекта"
                repoRead("Чтение объявления из БД")
                checkLock("Проверяем констистентность по оптимистичной блокировке")
                repoPrepareDelete("Подготовка объекта для удаления")
                repoDelete("Удаление объявления из БД")
            }
            prepareResult("Подготовка ответа")
        }
        operation("Поиск объявлений", AdCommand.SEARCH) {
            stubs("Обработка стабов") {
                stubSearchSuccess("Имитация успешной обработки", corSettings)
                stubSearchBadSearchString("Имитация ошибки поисковой строки")
                stubDbError("Имитация ошибки работы с БД")
                stubNoCase("Ошибка: запрошенный стаб недопустим")
            }
            validation {
                worker("Копируем поля в adFilterValidating") { adFilterValidating = adFilterRequest.deepCopy() }
                validateSearchString("Валидация длины строки в фильтре")

                finishAdFilterValidation("Успешное завершение процедуры валидации")
            }
            repoSearch("Поиск объвления в БД по фильтру")
            prepareResult("Подготовка ответа")
        }
    }.build()
}