package com.elvr.quizybara.repository


object FlagProvider {
    fun getFlagUrl(countryCode: String): String {
        return "https://flagcdn.com/w320/${countryCode.lowercase()}.png"
    }
}

object LogoProvider {
    fun getLogoUrl(brandName: String): String {
        val domain = when (brandName.lowercase()) {
            "apple" -> "apple.com"
            "tesla" -> "tesla.com"
            "nike" -> "nike.com"
            "google" -> "google.com"
            "facebook" -> "facebook.com"
            "samsung" -> "samsung.com"
            "microsoft" -> "microsoft.com"
            "amazon" -> "amazon.com"
            else -> "$brandName.com"
        }
        return "https://logo.clearbit.com/$domain"
    }
}
