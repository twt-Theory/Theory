package com.example.twttheory.service

import kotlinx.coroutines.Deferred
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceFactory {
    private const val BASE_URL = "http://101.200.122.177/api/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client: OkHttpClient = OkHttpClient.Builder()
        .retryOnConnectionFailure(false)
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(20, TimeUnit.SECONDS)
        .writeTimeout(20, TimeUnit.SECONDS)
        .addNetworkInterceptor(loggingInterceptor)
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    inline operator fun <reified T> invoke(): T = retrofit.create(T::class.java)
}

data class CommonBody<out T>(
    val error_code: Int,
    val message: String,
    val data: T?
)

sealed class RefreshState<M> {
    class Success<M>(val message: M) : RefreshState<M>()
    class Failure<M>(val throwable: Throwable) : RefreshState<M>()
    class Error<M>(val message: M) : RefreshState<M>()
    class Refreshing<M> : RefreshState<M>()
}

suspend fun <T> Deferred<T>.awaitAndHandle(handler: suspend (Throwable) -> Unit = {}): T? =
    try {
        await()
    } catch (t: Throwable) {
        handler(t)
        null
    }