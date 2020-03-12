package com.tadiuzzz.baseapp.di.module

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.tadiuzzz.baseapp.di.scope.AppScope
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.tadiuzzz.baseapp.BuildConfig
import com.tadiuzzz.baseapp.data.source.remote.BaseApi
import java.util.concurrent.TimeUnit

@Module
class NetworkModule {

    @Provides
    @AppScope
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @AppScope
    fun provideOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(interceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val original = chain.request()
                val originalUrl = original.url
                val url = originalUrl.newBuilder()
//                    .addQueryParameter("APPID", API_KEY) //возможность добавить параметр для каждого выполняемого запроса
                    .build()
                val requestBuilder = original.newBuilder()
                    .url(url)
                val request = requestBuilder.build()
                chain.proceed(request)
            }
        if (BuildConfig.DEBUG)
            builder.addNetworkInterceptor(StethoInterceptor()) //внедряем обработку сетевых запросов Stetho в Debug-сборках
        return builder.build()
    }

    @Provides
    @AppScope
    fun provideBaseApi(retrofit: Retrofit): BaseApi {
        return retrofit.create(BaseApi::class.java)
    }
}