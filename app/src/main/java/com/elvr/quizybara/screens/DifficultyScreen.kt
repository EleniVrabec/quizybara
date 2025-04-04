package com.elvr.quizybara.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.elvr.quizybara.Routes
import com.elvr.quizybara.viewmodels.QuizViewModel

@Composable
fun DifficultyScreen(navController: NavController, quizViewModel: QuizViewModel) {
    var selectedDifficulty by remember { mutableStateOf("easy") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Select Difficulty", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            DifficultyOption("easy", selectedDifficulty) { selectedDifficulty = it }
            DifficultyOption("medium", selectedDifficulty) { selectedDifficulty = it }
            DifficultyOption("hard", selectedDifficulty) { selectedDifficulty = it }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                quizViewModel.setDifficulty(selectedDifficulty)
                navController.navigate(Routes.Quiz) // Navigate to quiz screen
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Start Game")
        }
    }
}

@Composable
fun DifficultyOption(level: String, selectedDifficulty: String, onSelect: (String) -> Unit) {
    val backgroundColor = if (selectedDifficulty == level) Color.Blue else Color.Gray

    Box(
        modifier = Modifier
            .background(backgroundColor, shape = MaterialTheme.shapes.medium)
            .padding(12.dp)
            .clickable { onSelect(level) }
    ) {
        Text(
            text = level,
            color = Color.White,
            modifier = Modifier.padding(8.dp)
        )
    }
}
