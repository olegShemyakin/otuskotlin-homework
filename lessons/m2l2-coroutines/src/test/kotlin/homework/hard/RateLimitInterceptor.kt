package homework.hard

import com.google.common.util.concurrent.RateLimiter
import okhttp3.Interceptor
import okhttp3.Response

class RateLimitInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        rateLimiter.acquire(1)
        return chain.proceed(chain.request())
    }

    companion object {
        private val rateLimiter: RateLimiter = RateLimiter.create(2.0)
    }
}