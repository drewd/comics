package com.dobson.comics.repository.remote.service

import android.util.Log
import com.dobson.comics.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.security.MessageDigest
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
    @Singleton
    @Provides
    fun provideMoshiConverterFactory(): MoshiConverterFactory {
        return MoshiConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor { message -> Log.v("HTTP", message) }
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        interceptor.redactQueryParams(ApiKeyInterceptor.PARAM_API_KEY)
        interceptor.redactQueryParams(ApiKeyInterceptor.PARAM_HASH)
        interceptor.redactQueryParams(ApiKeyInterceptor.PARAM_TIMESTAMP)
        return interceptor
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        apiKeyInterceptor: ApiKeyInterceptor, httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(apiKeyInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, factory: MoshiConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(factory)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideRemoteComicDataSource(retrofit: Retrofit): ComicService {
        return retrofit.create(ComicService::class.java)
    }

    @Singleton
    @Provides
    fun provideMessageDigest(): MessageDigest {
        return MessageDigest.getInstance("MD5")
    }
}