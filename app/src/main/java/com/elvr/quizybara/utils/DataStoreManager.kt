package com.elvr.quizybara.utils

import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Create DataStore instance
val Context.dataStore by preferencesDataStore(name = "quiz_preferences")

class DataStoreManager(private val context: Context) {

    companion object {
        val TOTAL_POINTS = intPreferencesKey("total_points")
        val UNLOCKED_CATEGORIES = stringSetPreferencesKey("unlocked_categories")
    }

    val totalPoints: Flow<Int> = context.dataStore.data.map { prefs ->
        prefs[TOTAL_POINTS] ?: 0
    }

    val unlockedCategories: Flow<Set<Int>> = context.dataStore.data.map { prefs ->
        prefs[UNLOCKED_CATEGORIES]
            ?.mapNotNull { it.toIntOrNull() } // safely convert to Int
            ?.toSet()
            ?: setOf(22, 9, 23) // default unlocked categories
    }


    suspend fun saveTotalPoints(points: Int) {
        context.dataStore.edit { prefs ->
            prefs[TOTAL_POINTS] = points
        }
    }

    suspend fun saveUnlockedCategories(categories: Set<Int>) {
        context.dataStore.edit { prefs ->
            prefs[UNLOCKED_CATEGORIES] = categories.map { it.toString() }.toSet()
        }
    }

    val PLAYER_NICKNAME = stringPreferencesKey("player_nickname")

    val nickname: Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[PLAYER_NICKNAME]
    }

    suspend fun saveNickname(name: String) {
        context.dataStore.edit { prefs ->
            prefs[PLAYER_NICKNAME] = name
        }
    }

    val IS_FIRST_TIME = booleanPreferencesKey("is_first_time")

    suspend fun setFirstTime(value: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[IS_FIRST_TIME] = value
        }
    }

    val isFirstTime = context.dataStore.data
        .map { prefs -> prefs[IS_FIRST_TIME] ?: true } // true by default

}
