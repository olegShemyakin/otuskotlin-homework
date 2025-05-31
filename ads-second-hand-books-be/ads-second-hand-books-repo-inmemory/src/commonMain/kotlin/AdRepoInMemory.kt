package org.akira.otuskotlin.ads.repo.inmemory

import com.benasher44.uuid.uuid4
import io.github.reactivecircus.cache4k.Cache
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.akira.otuskotlin.ads.common.models.*
import org.akira.otuskotlin.ads.common.repo.*
import org.akira.otuskotlin.ads.common.repo.exceptions.RepoEmptyLockException
import org.akira.otuskotlin.ads.repo.common.IRepoAdInitializable
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class AdRepoInMemory(
    ttl: Duration = 10.minutes,
    val randomUuid: () -> String = { uuid4().toString() }
) : AdRepoBase(), IRepoAdInitializable {

    private val mutex: Mutex = Mutex()
    private val cache = Cache.Builder<String, AdEntity>()
        .expireAfterWrite(ttl)
        .build()

    override fun save(ads: Collection<Ad>): Collection<Ad> = ads.map { ad ->
        val entity = AdEntity(ad)
        require(entity.id != null)
        cache.put(entity.id, entity)
        ad
    }

    override suspend fun createAd(rq: DbAdRequest): IDbAdResponse = tryAdMethod {
        val id = randomUuid()
        val ad = rq.ad.copy(id = AdId(id), lock = AdLock(randomUuid()))
        val entity = AdEntity(ad)
        mutex.withLock {
            cache.put(id, entity)
        }
        DbAdResponseOk(ad)
    }

    override suspend fun readAd(rq: DbAdIdRequest): IDbAdResponse = tryAdMethod {
        val id = rq.id.takeIf { it != AdId.NONE }?.asString() ?: return@tryAdMethod errorEmptyId
        mutex.withLock {
            cache.get(id)
                ?.let {
                    DbAdResponseOk(it.toInternal())
                } ?: errorNotFound(rq.id)
        }
    }

    override suspend fun updateAd(rq: DbAdRequest): IDbAdResponse = tryAdMethod {
        val rqAd = rq.ad
        val id = rqAd.id.takeIf { it != AdId.NONE } ?: return@tryAdMethod errorEmptyId
        val idKey = id.asString()
        val oldLock = rqAd.lock.takeIf { it != AdLock.NONE } ?: return@tryAdMethod errorEmptyLock(id)

        mutex.withLock {
            val oldAd = cache.get(idKey)?.toInternal()
            when {
                oldAd == null -> errorNotFound(id)
                oldAd.lock == AdLock.NONE -> errorDb(RepoEmptyLockException(id))
                oldAd.lock != oldLock -> errorRepoConcurrency(oldAd, oldLock)
                else -> {
                    val newAd = rqAd.copy(lock = AdLock(randomUuid()))
                    val entity = AdEntity(newAd)
                    cache.put(idKey, entity)
                    DbAdResponseOk(newAd)
                }
            }
        }
    }

    override suspend fun deleteAd(rq: DbAdIdRequest): IDbAdResponse = tryAdMethod {
        val id = rq.id.takeIf { it != AdId.NONE } ?: return@tryAdMethod errorEmptyId
        val idKey = id.asString()
        val oldLock = rq.lock.takeIf { it != AdLock.NONE } ?: return@tryAdMethod errorEmptyLock(id)

        mutex.withLock {
            val oldAd = cache.get(idKey)?.toInternal()
            when {
                oldAd == null -> errorNotFound(id)
                oldAd.lock == AdLock.NONE -> errorDb(RepoEmptyLockException(id))
                oldAd.lock != oldLock -> errorRepoConcurrency(oldAd, oldLock)
                else -> {
                    cache.invalidate(idKey)
                    DbAdResponseOk(oldAd)
                }
            }
        }
    }

    override suspend fun searchAd(rq: DbAdFilterRequest): IDbAdsResponse = tryAdsMethod {
        val result: List<Ad> = cache.asMap().asSequence()
            .filter { entry ->
                rq.ownerId.takeIf { it != AdUserId.NONE }?.let {
                    it.asString() == entry.value.ownerId
                } ?: true
            }
            .filter { entry ->
                rq.adType.takeIf { it != AdType.NONE }?.let {
                    it.name == entry.value.adType
                } ?: true
            }
            .filter { entry ->
                rq.titleFilter.takeIf { it.isNotBlank() }?.let {
                    entry.value.title?.contains(it) ?: false
                } ?: true
            }
            .map { it.value.toInternal() }
            .toList()
        DbAdsResponseOk(result)
    }
}