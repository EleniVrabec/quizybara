package com.elvr.quizybara.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.elvr.quizybara.components.QuestionCard
import com.elvr.quizybara.viewmodels.QuizViewModel
import android.os.Handler
import android.os.Looper
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import com.elvr.quizybara.components.FinishModal
import com.elvr.quizybara.components.PointsBadge
import com.elvr.quizybara.components.QuizProgressBar
import com.elvr.quizybara.components.loader.CircleLoader
import com.elvr.quizybara.ui.theme.BlueBackground
import com.elvr.quizybara.ui.theme.ComputersGradient
import com.elvr.quizybara.ui.theme.*
import com.elvr.quizybara.ui.theme.GuessLogoGradient
import kotlinx.coroutines.delay
import androidx.compose.animation.with
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith

@Composable
fun QuizScreen(navController: NavController, quizViewModel: QuizViewModel) {
    val questions = quizViewModel.questions.collectAsState()
    val category = quizViewModel.category.collectAsState()
    val difficulty = quizViewModel.difficulty.collectAsState()
    val currentQuestionIndex = quizViewModel.currentQuestionIndex.collectAsState()
    val lastEarnedPoints = quizViewModel.lastEarnedPoints.collectAsState()

    // Track the selected answer at this screen level
    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    var key by remember { mutableStateOf(0) }

    // ✅ State to show the modal before navigation
    var showFinishModal by remember { mutableStateOf(false) }
    var shouldNavigateToResults by remember { mutableStateOf(false) }

    LaunchedEffect(category.value, difficulty.value) {
        quizViewModel.fetchQuestions()
    }
    // ✅ Handles navigation after the FinishModal has been shown
    LaunchedEffect(shouldNavigateToResults) {
        if (shouldNavigateToResults) {
            showFinishModal = false
            navController.navigate("results")
        }
    }

    if (questions.value.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize() .background(DarkBackground), contentAlignment = Alignment.Center) {
           // Text("Loading questions...", style = MaterialTheme.typography.headlineMedium)
            CircleLoader(
                isVisible = true,
                color = LoaderPrimaryColor,
                secondColor = LoaderSecondaryColor,
                modifier = Modifier.size(100.dp)
            )
        }
    }/* else if (currentQuestionIndex.value >= questions.value.size) {
        navController.navigate("results")
    }*/else {
        val totalQuestions = questions.value.size
        val isLastQuestion = currentQuestionIndex.value == questions.value.size - 1
        val question = questions.value[currentQuestionIndex.value]
        val progress = (currentQuestionIndex.value + 1) / totalQuestions.toFloat()
        Box(
            modifier = Modifier
                .background( ComputersGradient)
                .fillMaxSize()
                .padding(16.dp)
                //.padding(bottom = 16.dp)


        ) {
            Spacer(modifier = Modifier.height(100.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
              //  .padding(top = 100.dp),
               .padding(16.dp),

           verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            QuizProgressBar(
                currentQuestion = currentQuestionIndex.value,
                totalQuestions = questions.value.size,
                modifier = Modifier
                .fillMaxWidth()
                   // .fillMaxWidth(0.9f)
                // .padding(bottom = 16.dp)
            )
            Spacer(modifier = Modifier.height(50.dp))
            // Inside your Column, where you call QuestionCard:
            AnimatedContent(
                targetState = currentQuestionIndex.value,
                transitionSpec = {
                    (slideInHorizontally { width -> width } + fadeIn()) togetherWith
                            (slideOutHorizontally { width -> -width } + fadeOut())
                },
                label = "questionAnimation"
            ) { index -> // 👈 You can name this whatever
                QuestionCard(
                    key = key,
                    question = questions.value[index].questionText,
                    answers = questions.value[index].options,
                    flagUrl = null,
                    questionType = questions.value[index].type,
                    correctAnswer = questions.value[index].correctAnswer,
                    selectedAnswer = selectedAnswer,
                    onAnswerSelected = { answer ->
                        if (selectedAnswer == null) {
                            selectedAnswer = answer
                            quizViewModel.submitAnswer(answer)
                        }
                    },
                    onNextQuestion = {
                        selectedAnswer = null
                        key++
                        if (index == questions.value.lastIndex) {
                            showFinishModal = true
                            Handler(Looper.getMainLooper()).postDelayed({
                                shouldNavigateToResults = true
                            }, 3000)
                        } else {
                            quizViewModel.moveToNextQuestion(
                                navController,
                                { showFinishModal = it },
                                { shouldNavigateToResults = true }
                            )
                        }
                    }
                )
            }

        }//column
            // 🎯 Points badge (optional)
            Box(
                modifier = Modifier.align(Alignment.TopEnd).padding(16.dp)
            ) {
                PointsBadge(quizViewModel.totalPoints.collectAsState().value)
            }
    }


        if (lastEarnedPoints.value != null && lastEarnedPoints.value!! > 0) {
            LaunchedEffect(lastEarnedPoints.value) {
                delay(1000) // Show for 1 second
                quizViewModel.clearLastEarnedPoints()
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                Text(
                    text = "+${lastEarnedPoints.value} Points!",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

    }
    // ✅ Show the Finish Modal before navigating to results
    if (showFinishModal) {
        FinishModal(
            totalPoints = quizViewModel.totalPoints.collectAsState().value,
            onDismissRequest = { showFinishModal = false },
            onConfirmation = { shouldNavigateToResults = true }
        )
    }
}