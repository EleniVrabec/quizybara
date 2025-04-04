package com.elvr.quizybara.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.elvr.quizybara.components.QuestionReviewCard
import com.elvr.quizybara.viewmodels.QuizMode
import com.elvr.quizybara.viewmodels.QuizViewModel

import androidx.compose.ui.draw.clip

import com.elvr.quizybara.ui.theme.ComputersGradient
import com.elvr.quizybara.ui.theme.LightText
import com.elvr.quizybara.ui.theme.Pink80
import com.elvr.quizybara.ui.theme.PinkPrimary

@Composable
fun ResultsScreen(navController: NavController, quizViewModel: QuizViewModel) {

    val correctAnswers = quizViewModel.correctAnswers.collectAsState()
    val quizMode = quizViewModel.quizMode.collectAsState()
    val userAnswers = quizViewModel.userAnswers.collectAsState()
    var showReview by remember { mutableStateOf(false) }

    // ✅ Ensure we fetch the correct question list dynamically
    val questions = when (quizMode.value) {
        QuizMode.TRIVIA -> quizViewModel.questions.collectAsState().value
        QuizMode.FLAG -> quizViewModel.flagQuestions.collectAsState().value
        QuizMode.LOGO -> quizViewModel.logoQuestions.collectAsState().value
    }

    Column(
        modifier = Modifier
            .background( ComputersGradient)
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        // ✅ Display Quiz Type Name Dynamically
        Text(
            text = when (quizMode.value) {
                QuizMode.TRIVIA -> "Trivia Quiz Completed!"
                QuizMode.FLAG -> "Flag Quiz Completed!"
                QuizMode.LOGO -> "Logo Quiz Completed!"
            },
            style = MaterialTheme.typography.headlineLarge,
            color = Pink80
        )

        Spacer(modifier = Modifier.height(16.dp))

        // ✅ Display Final Score
        Text(
            text = "You got ${correctAnswers.value} out of ${questions.size} questions correct!",
            style = MaterialTheme.typography.titleLarge,
                    color = Pink80
        )

        Spacer(modifier = Modifier.height(32.dp))
        if (showReview) {
        // ✅ Dynamically Show Quiz Type for Review
        Text(
            text = when (quizMode.value) {
                QuizMode.TRIVIA -> "Trivia Question Review"
                QuizMode.FLAG -> "Flag Quiz Review"
                QuizMode.LOGO -> "Logo Quiz Review"
            },
            style = MaterialTheme.typography.titleMedium,
            color = Pink80,
            modifier = Modifier.padding(vertical = 8.dp)
        )

            // ✅ Display all questions with user answers
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                items(questions.size) { index ->
                    val question = questions[index]
                    val userAnswer = userAnswers.value.getOrNull(index) ?: "Not answered"
                    val isCorrect = userAnswer == question.correctAnswer

                    QuestionReviewCard(
                        questionNumber = index + 1,
                        questionText = question.questionText,
                        userAnswer = userAnswer,
                        correctAnswer = question.correctAnswer,
                        isCorrect = isCorrect
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                }
            }//lazy column
        }else {
            // 🔒 Locked state — tap to show review
            TextButton(
                onClick = { showReview = true },
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                Text("🔓 Review Your Answers", style = MaterialTheme.typography.titleLarge, color = Pink80)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                quizViewModel.resetQuiz()
                navController.navigate("choose_game")
            },
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(56.dp)
                .padding(top = 16.dp)
               /* .border(
                    width = 2.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(PinkPrimary, BlueAccent)
                    ),
                    shape = RoundedCornerShape(50.dp)
                )*/
                .clip(RoundedCornerShape(50.dp))
                .background(PinkPrimary),
            colors = ButtonDefaults.buttonColors(
               containerColor = PinkPrimary,
                contentColor = LightText
            )
        ) {
            Text("🎮 Play Again")
        }

    }//column


}


