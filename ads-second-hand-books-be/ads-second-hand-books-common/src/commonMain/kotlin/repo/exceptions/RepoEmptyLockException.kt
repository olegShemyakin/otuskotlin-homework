package org.akira.otuskotlin.ads.common.repo.exceptions

import org.akira.otuskotlin.ads.common.models.AdId

class RepoEmptyLockException(id: AdId) : RepoAdException(
    id,
    "Lock is empty in DB"
)