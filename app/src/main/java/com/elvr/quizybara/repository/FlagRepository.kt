package com.elvr.quizybara.repository


import com.elvr.quizybara.service.ApiClient
import com.elvr.quizybara.service.FlagApiResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext



class FlagRepository {
  fun getFlag(countryCode: String): String {
            return FlagProvider.getFlagUrl(countryCode)
        }
}


/*
   private val apiKey = "+7TfII7DGG43LYy8qjJkIQ==I3PkAi6km60mss7f"
   private val cachedFlags = mutableMapOf<String, String>() // Cache to avoid multiple API calls

   suspend fun getFlag(countryCode: String): String? {
       return withContext(Dispatchers.IO) {
           try {
               println("🌍 Requesting flag for $countryCode") // ✅ Debugging
               val response: FlagApiResponse = ApiClient.flagApiService.getFlag(countryCode, apiKey)
               println("✅ Flag URL for $countryCode: ${response.rectangle_image_url}") // ✅ Log response
               response.rectangle_image_url
           } catch (e: Exception) {
               println("❌ Failed to fetch flag for $countryCode: ${e.message}")
               null
           }
       }
   }
   */