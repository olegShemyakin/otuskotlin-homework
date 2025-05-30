package org.akira.otuskotlin.ads.common.repo

import org.akira.otuskotlin.ads.common.models.AdError
import org.akira.otuskotlin.ads.common.models.AdId

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