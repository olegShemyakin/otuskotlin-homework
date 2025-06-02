package org.akira.otuskotlin.ads.repo.postgresql

import org.akira.otuskotlin.ads.common.models.AdType
import org.jetbrains.exposed.sql.Table
import org.postgresql.util.PGobject

fun Table.adTypeEnumeration(
    columnName: String
) = customEnumeration(
    name = columnName,
    sql = SqlFields.AD_TYPE_TYPE,
    fromDb = { value ->
        when (value.toString()) {
            SqlFields.AD_TYPE_DEMAND -> AdType.DEMAND
            SqlFields.AD_TYPE_PROPOSAL -> AdType.PROPOSAL
            else -> AdType.NONE
        }
    },
    toDb = { value ->
        when (value) {
            AdType.DEMAND -> PgAdTypeDemand
            AdType.PROPOSAL -> PgAdTypeProposal
            AdType.NONE -> throw Exception("Wrong value of Ad Type. NONE is unsupported")
        }
    }
)

sealed class PgAdTypeValue(enVal: String): PGobject() {
    init {
        type = SqlFields.AD_TYPE_TYPE
        value = enVal
    }
}

object PgAdTypeDemand: PgAdTypeValue(SqlFields.AD_TYPE_DEMAND) {
    private fun readResolve(): Any = PgAdTypeDemand
}

object PgAdTypeProposal: PgAdTypeValue(SqlFields.AD_TYPE_PROPOSAL) {
    private fun readResolve(): Any = PgAdTypeProposal
}