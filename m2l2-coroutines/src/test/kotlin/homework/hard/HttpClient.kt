package ru.otus.otuskotlin.coroutines.homework.hard

import homework.hard.RateLimitInterceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response

object HttpClient : OkHttpClient() {

    private val client: OkHttpClient = Builder()
        .addInterceptor(RateLimitInterceptor())
        .build()

    fun get(uri: String): Response =
        Request.Builder().url(uri).build()
            .let {
                client.newCall(it).execute()
            }
}
