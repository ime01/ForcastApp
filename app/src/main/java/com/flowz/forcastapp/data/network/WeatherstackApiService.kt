package com.flowz.forcastapp.data.network

import com.flowz.forcastapp.data.network.response.CurrentWeatherResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "305f045993f33edb3670756aa132549c"
const val BASE_URL = "http://api.weatherstack.com/"

//http://api.weatherstack.com/current?access_key=305f045993f33edb3670756aa132549c&query=New%20York&language=en

interface WeatherstackApiService {


    @GET("current")
    fun getCurrentWeather(
        @Query("query") location: String,
        @Query("lang") languageCode: String = "en"
    ): Deferred<CurrentWeatherResponse>

    companion object{

        operator fun invoke(connectivityInterceptor: ConnectivityInterceptor): WeatherstackApiService {
           val requestInterceptor = Interceptor{chain ->

               val url = chain.request()
                   .url()
                   .newBuilder()
                   .addQueryParameter("access_key", API_KEY)
                   .build()

               val request = chain.request()
                   .newBuilder()
                   .url(url)
                   .build()

               return@Interceptor chain.proceed(request)
           }
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherstackApiService::class.java)
        }

    }

}