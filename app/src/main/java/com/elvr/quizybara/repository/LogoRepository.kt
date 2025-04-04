package com.elvr.quizybara.repository

import com.elvr.quizybara.service.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LogoRepository {
    fun getLogo(companyName: String): String {
        return LogoProvider.getLogoUrl(companyName)
    }
}

/*
*  private val apiKey = "+7TfII7DGG43LYy8qjJkIQ==I3PkAi6km60mss7f" // ✅ Replace with your actual API key

    suspend fun getLogo(companyName: String): String? {
        return withContext(Dispatchers.IO) {
            try {
                val response = ApiClient.logoApiService.getLogo(companyName, apiKey)

                if (response.isNotEmpty()) {
                    val logoUrl = response[0].image
                    println("✅ Logo fetched for $companyName: $logoUrl")
                    return@withContext logoUrl
                } else {
                    println("❌ No logo found for $companyName")
                    return@withContext null
                }
            } catch (e: Exception) {
                println("❌ Error fetching logo for $companyName: ${e.message}")
                null
            }
        }
    }
*
* */