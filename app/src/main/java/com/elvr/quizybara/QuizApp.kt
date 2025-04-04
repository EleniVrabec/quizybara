package com.elvr.quizybara



import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.elvr.quizybara.ui.theme.QuizybaraTheme
import com.elvr.quizybara.utils.DataStoreManager
import com.elvr.quizybara.viewmodels.QuizViewModel
import com.elvr.quizybara.viewmodels.QuizViewModelFactory
import androidx.compose.ui.platform.LocalContext


@Composable
fun QuizApp() {
    QuizybaraTheme {
        val context = LocalContext.current
        val navController = rememberNavController()
       // val quizViewModel: QuizViewModel = viewModel()
        // ✅ Create DataStoreManager once
        val dataStore = remember { DataStoreManager(context) }
        // ✅ Pass it into QuizViewModel
        val quizViewModel: QuizViewModel = viewModel(
            factory = QuizViewModelFactory(dataStore)
        )

        Scaffold(modifier = Modifier.fillMaxSize()) {innerPadding ->

            Navigation(navController = navController, quizViewModel = quizViewModel) // Handles screen navigation
        }
    }
}
