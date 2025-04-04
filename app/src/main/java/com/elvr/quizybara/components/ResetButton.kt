package com.elvr.quizybara.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.elvr.quizybara.viewmodels.QuizViewModel
import kotlinx.coroutines.launch

@Composable
fun ResetButton(quizViewModel: QuizViewModel) {
    val scope = rememberCoroutineScope()

    Button(onClick = {
        scope.launch {
            quizViewModel.resetAllData()
        }
    }) {
        Text("Reset Data (for testing)")
    }
}
