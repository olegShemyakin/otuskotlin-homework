package org.akira.otuskotlin.ads.common.repo

import org.akira.otuskotlin.ads.common.helpers.errorSystem
import org.akira.otuskotlin.ads.common.models.Ad
import org.akira.otuskotlin.ads.common.models.AdError
import org.akira.otuskotlin.ads.common.models.AdId
import org.akira.otuskotlin.ads.common.models.AdLock
import org.akira.otuskotlin.ads.common.repo.exceptions.RepoConcurrencyException
import org.akira.otuskotlin.ads.common.repo.exceptions.RepoException

const val ERROR_GROUP_REPO = "repo"

fun errorNotFound(id: AdId) = DbAdResponseErr(
    AdError(
        code = "$ERROR_GROUP_REPO-not-found",
        group = ERROR_GROUP_REPO,
        field = "id",
        message = "Object with ID: ${id.asString()} is not Found"
    )
)
val errorEmptyId = DbAdResponseErr(
    AdError(
        code = "$ERROR_GROUP_REPO-empty-id",
        group = ERROR_GROUP_REPO,
        field = "id",
        message = "Id must not be null or blank"
    )
)

fun errorRepoConcurrency(
    oldAd: Ad,
    expectedLcok: AdLock,
    exception: Exception = RepoConcurrencyException(
        id = oldAd.id,
        expectedLock = expectedLcok,
        actualLock = oldAd.lock
    )
) = DbAdResponseErrWithData(
    ad = oldAd,
    err = AdError(
        code = "$ERROR_GROUP_REPO-concurrency",
        group = ERROR_GROUP_REPO,
        field = "lock",
        message = "The object with ID ${oldAd.id.asString()} has been change concurrently by another user or process",
        exception = exception
    )
)

fun errorEmptyLock(id: AdId) = DbAdResponseErr(
    AdError(
        code = "$ERROR_GROUP_REPO-concurrency",
        group = ERROR_GROUP_REPO,
        field = "lock",
        message = "Lock for Ad ${id.asString()} is empty that is not admitted"
    )
)

fun errorDb(e: RepoException) = DbAdResponseErr(
    errorSystem(
        violationCode = "dbLockEmpty",
        e = e
    )
)