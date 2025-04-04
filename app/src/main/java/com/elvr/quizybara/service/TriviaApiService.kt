package com.elvr.quizybara.service

import com.elvr.quizybara.model.TriviaApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface TriviaApiService {
    @GET("api.php")
    suspend fun getQuestions(
        @Query("amount") amount: Int,
        @Query("category") category: Int,
      //  @Query("difficulty") difficulty: String,
        @Query("type") type: String = "multiple",
        @Query("encode") encode: String = "base64"
    ): TriviaApiResponse // ✅ Return TriviaApiResponse instead of List<Question>
}
