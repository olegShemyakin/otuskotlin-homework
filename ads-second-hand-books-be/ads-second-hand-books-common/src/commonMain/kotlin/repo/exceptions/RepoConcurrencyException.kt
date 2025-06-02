package org.akira.otuskotlin.ads.common.repo.exceptions

import org.akira.otuskotlin.ads.common.models.AdId
import org.akira.otuskotlin.ads.common.models.AdLock

class RepoConcurrencyException(id: AdId, expectedLock: AdLock, actualLock: AdLock) : RepoAdException(
    id,
    "Expected lock is $expectedLock while actual lock in db is $actualLock"
)