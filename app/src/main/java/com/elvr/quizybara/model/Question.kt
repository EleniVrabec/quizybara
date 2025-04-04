package com.elvr.quizybara.model


data class Question(
    val questionText: String,
    val options: List<String>,
    val correctAnswer: String,
    val type: String, // "multiple" or "boolean"
    val flagUrl: String? = null
)
