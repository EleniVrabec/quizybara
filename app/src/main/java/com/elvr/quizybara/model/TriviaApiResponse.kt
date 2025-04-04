package com.elvr.quizybara.model

data class TriviaApiResponse(
    val response_code: Int,
    val results: List<ApiQuestion>
)

data class ApiQuestion(
    val question: String,
    val correct_answer: String,
    val incorrect_answers: List<String>,
    val type: String // "multiple" or "boolean"
)