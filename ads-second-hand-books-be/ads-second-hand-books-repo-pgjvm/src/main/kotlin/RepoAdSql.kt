package org.akira.otuskotlin.ads.repo.postgresql

import com.benasher44.uuid.uuid4
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.akira.otuskotlin.ads.common.helpers.asAdError
import org.akira.otuskotlin.ads.common.models.*
import org.akira.otuskotlin.ads.common.repo.*
import org.akira.otuskotlin.ads.repo.common.IRepoAdInitializable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class RepoAdSql(
    properties: SqlProperties,
    private val randomUuid: () -> String = { uuid4().toString() }
) : IRepoAd, IRepoAdInitializable {
    private val adTable = AdTable("${properties.schema}.${properties.table}")

    private val driver = when {
        properties.url.startsWith("jdbc:postgresql://") -> "org.postgresql.Driver"
        else -> throw IllegalArgumentException("Unknown driver for url ${properties.url}")
    }

    private val conn = Database.connect(
        properties.url, driver, properties.user, properties.password
    )

    fun clear(): Unit = transaction(conn) {
        adTable.deleteAll()
    }

    private fun saveObj(ad: Ad): Ad = transaction(conn) {
        val res = adTable
            .insert {
                to(it, ad, randomUuid)
            }
            .resultedValues
            ?.map { adTable.from(it) }
        res?.first() ?: throw RuntimeException("BD error: insert statement returned empty result")
    }

    private suspend inline fun <T> transactionWrapper(crossinline block: () -> T, crossinline handle: (Exception) -> T): T =
        withContext(Dispatchers.IO) {
            try {
                transaction(conn) {
                    block()
                }
            } catch (e: Exception) {
                handle(e)
            }
        }

    private suspend inline fun transactionWrapper(crossinline block: () -> IDbAdResponse): IDbAdResponse =
        transactionWrapper(block) { DbAdResponseErr(it.asAdError()) }

    override fun save(ads: Collection<Ad>): Collection<Ad> = ads.map { saveObj(it) }

    override suspend fun createAd(rq: DbAdRequest): IDbAdResponse = transactionWrapper {
        DbAdResponseOk(saveObj(rq.ad))
    }

    private fun read(id: AdId): IDbAdResponse {
        val res = adTable.selectAll().where {
            adTable.id eq id.asString()
        }.singleOrNull() ?: return errorNotFound(id)
        return DbAdResponseOk(adTable.from(res))
    }

    override suspend fun readAd(rq: DbAdIdRequest): IDbAdResponse = transactionWrapper {
        read(rq.id)
    }

    private suspend fun update(
        id: AdId,
        lock: AdLock,
        block: (Ad) -> IDbAdResponse
    ): IDbAdResponse =
        transactionWrapper {
            if (id == AdId.NONE) return@transactionWrapper errorEmptyId

            val current = adTable.selectAll().where { adTable.id eq id.asString() }
                .singleOrNull()
                ?.let { adTable.from(it) }

            when {
                current == null -> errorNotFound(id)
                current.lock != lock -> errorRepoConcurrency(current, lock)
                else -> block(current)
            }
        }

    override suspend fun updateAd(rq: DbAdRequest): IDbAdResponse = update(rq.ad.id, rq.ad.lock) {
        adTable.update({ adTable.id eq rq.ad.id.asString() }) {
            to(it, rq.ad.copy(lock = AdLock(randomUuid())), randomUuid)
        }
        read(rq.ad.id)
    }

    override suspend fun deleteAd(rq: DbAdIdRequest): IDbAdResponse = update(rq.id, rq.lock) {
        adTable.deleteWhere { id eq rq.id.asString() }
        DbAdResponseOk(it)
    }

    override suspend fun searchAd(rq: DbAdFilterRequest): IDbAdsResponse =
        transactionWrapper({
            val res = adTable.selectAll().where {
                buildList {
                    add(Op.TRUE)
                    if (rq.ownerId != AdUserId.NONE) {
                        add(adTable.owner eq rq.ownerId.asString())
                    }
                    if (rq.adType != AdType.NONE) {
                        add(adTable.adType eq rq.adType)
                    }
                    if (rq.titleFilter.isNotBlank()) {
                        add(
                            (adTable.title like "%${rq.titleFilter}%")
                        )
                    }
                }.reduce { a, b -> a and b }
            }
            DbAdsResponseOk(data = res.map { adTable.from(it) })
        }, {
            DbAdsResponseErr(it.asAdError())
        })
}