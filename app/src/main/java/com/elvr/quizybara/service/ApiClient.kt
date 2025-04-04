package com.elvr.quizybara.service


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private const val BASE_URL = "https://opentdb.com/"
    private const val FLAG_BASE_URL = "https://api.api-ninjas.com/v1/"
    private const val LOGO_BASE_URL = "https://api.api-ninjas.com/v1/"

    val triviaApiService: TriviaApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // Converts JSON to Kotlin objects
            .build()
            .create(TriviaApiService::class.java)
    }

    val flagApiService: FlagApiService by lazy {
        Retrofit.Builder()
            .baseUrl(FLAG_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FlagApiService::class.java)
    }

    val logoApiService: LogoApiService by lazy {
        Retrofit.Builder()
            .baseUrl(LOGO_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LogoApiService::class.java)
    }

}
