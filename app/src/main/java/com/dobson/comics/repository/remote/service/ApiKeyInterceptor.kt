package com.dobson.comics.repository.remote.service

import com.dobson.comics.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import java.security.MessageDigest
import javax.inject.Inject

class ApiKeyInterceptor @Inject constructor(
    private val messageDigest: MessageDigest,
    private val dateService: DateService,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val ts = "${dateService.now()}"
        val url = request.url.newBuilder()
            .addQueryParameter(PARAM_API_KEY, BuildConfig.PUBLIC_KEY)
            .addQueryParameter(PARAM_TIMESTAMP, ts)
            .addQueryParameter(
                PARAM_HASH, getHash(ts)
            )
            .build()
        return chain.proceed(
            request.newBuilder().url(url).build()
        )
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun getHash(timestamp: String): String {
        return messageDigest.digest("${timestamp}${BuildConfig.PRIVATE_KEY}${BuildConfig.PUBLIC_KEY}".toByteArray())
            .toHexString()
    }

    companion object {
        const val PARAM_API_KEY = "apikey"
        const val PARAM_HASH = "hash"
        const val PARAM_TIMESTAMP = "ts"
    }
}