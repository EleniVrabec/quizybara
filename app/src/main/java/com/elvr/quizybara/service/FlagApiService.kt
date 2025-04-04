package com.elvr.quizybara.service


import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

data class FlagApiResponse(
    val country: String,
    val square_image_url: String,
    val rectangle_image_url: String
)

interface FlagApiService {
    @GET("countryflag")
    suspend fun getFlag(
        @Query("country") countryCode: String,
        @Header("X-Api-Key") apiKey: String // Pass API Key in request header
    ): FlagApiResponse
}
