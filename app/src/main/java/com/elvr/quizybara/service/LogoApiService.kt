package com.elvr.quizybara.service


import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

// ✅ Define the API response model
data class LogoApiResponse(
    val name: String,
    val ticker: String?,
    val image: String // ✅ Logo image URL
)

interface LogoApiService {
    @GET("logo")
    suspend fun getLogo(
        @Query("name") companyName: String,
        @Header("X-Api-Key") apiKey: String // ✅ Pass API Key in header
    ): List<LogoApiResponse> // ✅ API returns a list of results
}
