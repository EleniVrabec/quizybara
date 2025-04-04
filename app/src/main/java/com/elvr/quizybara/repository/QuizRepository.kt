package com.elvr.quizybara.repository

import com.elvr.quizybara.model.Question
import com.elvr.quizybara.model.TriviaApiResponse
import com.elvr.quizybara.service.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import android.util.Base64

class QuizRepository {
/*
    suspend fun getQuestions(amount: Int, category: Int, difficulty: String): List<Question> {
        return withContext(Dispatchers.IO) {
            try {
                println("🌍 Sending API request with category: $category, difficulty: $difficulty")
                val response: TriviaApiResponse = ApiClient.triviaApiService.getQuestions(amount, category, difficulty)
                println("🌍 API Response: $response")
                response.results.map { apiQuestion ->
                    Question(
                        questionText = decodeBase64(apiQuestion.question), // ✅ Decode question text
                        correctAnswer = decodeBase64(apiQuestion.correct_answer), // ✅ Decode correct answer
                        options = (apiQuestion.incorrect_answers.map { decodeBase64(it) } + decodeBase64(apiQuestion.correct_answer)).shuffled(),
                        type = apiQuestion.type
                    )
                }
            } catch (e: Exception) {
                println("❌ API request failed: ${e.message}")
                emptyList()
            }
        }
    }

 */
suspend fun getQuestions(amount: Int, category: Int): List<Question> {
    return withContext(Dispatchers.IO) {
        try {
            println("🌍 Sending API request with category: $category")

            val response: TriviaApiResponse = ApiClient.triviaApiService.getQuestions(
                amount = amount,
                category = category, // ✅ Ensure category is actually passed
                type = "multiple",
                encode = "base64"
            )

            println("🌍 API Response: $response")

            if (response.response_code == 2) {
                println("❌ No questions found for category $category")
                return@withContext emptyList()
            }

            if (response.response_code == 429) {
                println("⚠️ API rate limit reached! Waiting 5 seconds before retrying...")
                Thread.sleep(5000)
                return@withContext emptyList()
            }

            response.results.map { apiQuestion ->
                Question(
                    questionText = decodeBase64(apiQuestion.question),
                    correctAnswer = decodeBase64(apiQuestion.correct_answer),
                    options = (apiQuestion.incorrect_answers.map { decodeBase64(it) } + decodeBase64(apiQuestion.correct_answer)).shuffled(),
                    type = apiQuestion.type
                )
            }
        } catch (e: Exception) {
            println("❌ API request failed: ${e.message}")
            emptyList()
        }
    }
}


    // ✅ Function to decode Base64 strings
    private fun decodeBase64(encodedString: String): String {
        return String(Base64.decode(encodedString, Base64.DEFAULT))
    }
}
