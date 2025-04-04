package com.elvr.quizybara.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.elvr.quizybara.utils.DataStoreManager

class QuizViewModelFactory(
    private val dataStore: DataStoreManager
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuizViewModel::class.java)) {
            return QuizViewModel(dataStore) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
