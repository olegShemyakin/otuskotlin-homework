package org.akira.otuskotlin.ads.biz.exceptions

import org.akira.otuskotlin.ads.common.models.AdWorkMode

class AdDbNotConfiguredException(val workMode: AdWorkMode): Exception(
    "Database is not configured properly for workmode $workMode"
)