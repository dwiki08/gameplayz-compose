package com.compoes.gameplayz.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.compoes.gameplayz.BuildConfig
import com.compoes.gameplayz.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun retrofitClient(@ApplicationContext context: Context): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor(ChuckerInterceptor(context))
        return httpClient.addInterceptor {
            val original = it.request()
            val url = original.url.newBuilder()
                .addQueryParameter("key", BuildConfig.API_KEY)
                .build()
            val requestBuilder = original.newBuilder().url(url)
            return@addInterceptor it.proceed(requestBuilder.build())
        }
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(httpClient: OkHttpClient): ApiService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(httpClient)
            .build()
        return retrofit.create(ApiService::class.java)
    }
}