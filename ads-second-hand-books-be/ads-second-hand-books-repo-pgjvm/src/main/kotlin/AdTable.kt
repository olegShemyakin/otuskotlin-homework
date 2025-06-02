package org.akira.otuskotlin.ads.repo.postgresql

import com.ionspin.kotlin.bignum.decimal.BigDecimal
import org.akira.otuskotlin.ads.common.models.Ad
import org.akira.otuskotlin.ads.common.models.AdId
import org.akira.otuskotlin.ads.common.models.AdLock
import org.akira.otuskotlin.ads.common.models.AdUserId
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.statements.UpdateBuilder

class AdTable(tableName: String) : Table(tableName) {
    val id = text(SqlFields.ID)
    val title = text(SqlFields.TITLE).nullable()
    val authors = text(SqlFields.AUTHORS).nullable()
    val publishing = text(SqlFields.PUBLISHING).nullable()
    val year = integer(SqlFields.YEAR)
    val owner = text(SqlFields.OWNER_ID)
    val adType = adTypeEnumeration(SqlFields.AD_TYPE)
    val price = text(SqlFields.PRICE)
    val lock = text(SqlFields.LOCK)

    override val primaryKey = PrimaryKey(id)

    fun from(res: ResultRow) = Ad(
        id = AdId(res[id].toString()),
        title = res[title] ?: "",
        authors = res[authors] ?: "",
        publishing = res[publishing] ?: "",
        year = res[year],
        adType = res[adType],
        price = BigDecimal.parseString(res[price]),
        ownerId = AdUserId(res[owner].toString()),
        lock = AdLock(res[lock])
    )

    fun to(it: UpdateBuilder<*>, ad: Ad, randomUuid: () -> String) {
        it[id] = ad.id.takeIf { it != AdId.NONE }?.asString() ?: randomUuid()
        it[title] = ad.title
        it[authors] = ad.authors
        it[publishing] = ad.publishing
        it[year] = ad.year
        it[adType] = ad.adType
        it[price] = ad.price.toPlainString()
        it[owner] = ad.ownerId.asString()
        it[lock] = ad.lock.takeIf { it != AdLock.NONE }?.asString() ?: randomUuid()
    }
}