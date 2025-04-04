package com.elvr.quizybara.screens

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*


import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.elvr.quizybara.components.BalloonConfetti
import com.elvr.quizybara.components.BonusPopup
import com.elvr.quizybara.components.ConfettiOverlay
import com.elvr.quizybara.components.QuestionCard
import com.elvr.quizybara.components.FinishModal
import com.elvr.quizybara.components.LocalFlagQuestionCard

import com.elvr.quizybara.components.PointsBadge
import com.elvr.quizybara.components.QuizProgressBar
import com.elvr.quizybara.components.loader.CircleLoader
import com.elvr.quizybara.ui.theme.BlueAccent
import com.elvr.quizybara.ui.theme.ComputersGradient
import com.elvr.quizybara.ui.theme.DarkBackground
import com.elvr.quizybara.ui.theme.LoaderPrimaryColor
import com.elvr.quizybara.ui.theme.LoaderSecondaryColor
import com.elvr.quizybara.ui.theme.OrangeAccent
import com.elvr.quizybara.ui.theme.PinkPrimary
import com.elvr.quizybara.utils.Presets
import com.elvr.quizybara.viewmodels.QuizViewModel


@Composable
fun FlagQuizScreen(navController: NavController, quizViewModel: QuizViewModel) {
    val context = LocalContext.current

    val questions = quizViewModel.flagQuestions.collectAsState()
    val currentQuestionIndex = quizViewModel.currentQuestionIndex.collectAsState()
    val totalPoints = quizViewModel.totalPoints.collectAsState()
    val correctInARow = quizViewModel.correctInARow.collectAsState()
    val correctAnswers = quizViewModel.correctAnswers.collectAsState().value
    val lastEarnedPoints = quizViewModel.lastEarnedPoints.collectAsState()
    var showTryAgainModal by remember { mutableStateOf(false) }
    val showStreakBonusPopup by quizViewModel.showStreakBonusPopup.collectAsState()
    val showEndBonusPopup by quizViewModel.showEndBonusPopup.collectAsState()
    val hasTriggeredStreakBonus = quizViewModel.hasTriggeredStreakBonus.collectAsState().value
    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    var showFireworks by remember { mutableStateOf(false) }
    var key by remember { mutableStateOf(0) }
    // ✅ State to show the modal before navigation
    var showFinishModal by remember { mutableStateOf(false) }
    var shouldNavigateToResults by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        quizViewModel.fetchLocalFlagQuestions(context)
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
          //  Text("Loading flag questions...", style = MaterialTheme.typography.headlineMedium)
            CircleLoader(
                isVisible = true,
                color = LoaderPrimaryColor,
                secondColor = LoaderSecondaryColor,
               // color = MaterialTheme.colorScheme.primary,
              //  secondColor = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.size(100.dp)
            )
        }
    }/* else if (currentQuestionIndex.value >= questions.value.size) {
        navController.navigate("results")
    }*/ else {
        val totalQuestions = questions.value.size
        val progress = (currentQuestionIndex.value + 1) / totalQuestions.toFloat()
        val isLastQuestion = currentQuestionIndex.value == questions.value.size - 1
        val question = questions.value[currentQuestionIndex.value]
        Box(modifier = Modifier.fillMaxSize(),contentAlignment = Alignment.Center) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(ComputersGradient)
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceEvenly,

                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Progress bar
                QuizProgressBar(
                    currentQuestion = currentQuestionIndex.value,
                    totalQuestions = questions.value.size,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
                LocalFlagQuestionCard(
                    key = key,
                    question = question.questionText,
                    flagFileName = question.flagUrl?.takeIf { it.isNotBlank() } ?: "placeholder.svg",


                    answers = question.options,
                    correctAnswer = question.correctAnswer,
                    selectedAnswer = selectedAnswer,
                    onAnswerSelected = { answer ->
                        if (selectedAnswer == null) {
                            selectedAnswer = answer
                            quizViewModel.submitAnswer(answer)
                            if (quizViewModel.correctInARow.value >= 5) {
                                quizViewModel.addBonusPoints(10)
                                quizViewModel.resetCorrectInARow()
                            }
                        }
                    },
                    onNextQuestion = {
                        selectedAnswer = null
                        key++
                        if (isLastQuestion) {
                            val earned7OrMore = quizViewModel.correctAnswers.value >= 7
                            val alreadyShown = quizViewModel.hasTriggeredEndBonus.value

                            // ✅ Bonus for 7+ correct answers
                            if (earned7OrMore && !alreadyShown) {
                                quizViewModel.addBonusPoints(50)
                                quizViewModel.triggerEndBonusPopup()
                                quizViewModel.markEndBonusTriggered()
                                // quizViewModel._hasTriggeredEndBonus.value = true
                                //  quizViewModel.setLastEarnedPoints(20)
                            }
                            if (quizViewModel.correctAnswers.value < 5) {
                                showTryAgainModal = true
                            }
                            // ✅ Show the modal before navigating to results
                            showFinishModal = true
                            Handler(Looper.getMainLooper()).postDelayed({
                                quizViewModel.resetStreakFlag()
                                quizViewModel.resetStreakFlag()
                                shouldNavigateToResults = true
                            }, 3500) // 3 seconds delay for animation
                        } else {
                            quizViewModel.moveToNextQuestion(
                                navController,
                                { showFinishModal = it },
                                { shouldNavigateToResults = true }
                            )
                        }
                    }//onnextquestion
                )

/*
                QuestionCard(
                    key = key,
                    question = question.questionText,
                    flagUrl = question.flagUrl, // ✅ Attach flag URL
                    answers = question.options,
                    questionType = question.type,
                    correctAnswer = question.correctAnswer,
                    selectedAnswer = selectedAnswer,
                    onAnswerSelected = { answer ->
                        if (selectedAnswer == null) {
                            selectedAnswer = answer
                            quizViewModel.submitAnswer(answer)
                            // Bonus for 5 in a row
                            if (quizViewModel.correctInARow.value >= 5) {
                                quizViewModel.addBonusPoints(10)
                                quizViewModel.resetCorrectInARow()
                            }
                        }
                    },
                    onNextQuestion = {
                        selectedAnswer = null
                        key++
                        if (isLastQuestion) {
                            val earned7OrMore = quizViewModel.correctAnswers.value >= 7
                            val alreadyShown = quizViewModel.hasTriggeredEndBonus.value

                            // ✅ Bonus for 7+ correct answers
                            if (earned7OrMore && !alreadyShown) {
                                quizViewModel.addBonusPoints(50)
                                quizViewModel.triggerEndBonusPopup()
                                quizViewModel.markEndBonusTriggered()
                               // quizViewModel._hasTriggeredEndBonus.value = true
                              //  quizViewModel.setLastEarnedPoints(20)
                            }
                            if (quizViewModel.correctAnswers.value < 5) {
                                showTryAgainModal = true
                            }
                            // ✅ Show the modal before navigating to results
                            showFinishModal = true
                            Handler(Looper.getMainLooper()).postDelayed({
                                quizViewModel.resetStreakFlag()
                                quizViewModel.resetStreakFlag()
                                shouldNavigateToResults = true
                            }, 3500) // 3 seconds delay for animation
                        } else {
                            quizViewModel.moveToNextQuestion(
                                navController,
                                { showFinishModal = it },
                                { shouldNavigateToResults = true }
                            )
                        }
                    }//onnextquestion
                )*/
            }//column
            // 🎯 Top-right badge overlay
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 16.dp, end = 16.dp)
            ) {
                val points by quizViewModel.totalPoints.collectAsState()
                PointsBadge(points)
            }
            if (showStreakBonusPopup) {
                BonusPopup(
                    message = "🔥 5 in a Row! You earned 30 bonus points!",
                    backgroundColor = BlueAccent,
                    showConfetti = true,
                    confettiType = "fireworks",
                    onDismiss = { quizViewModel.resetStreakBonusPopup() }
                )
            }

            if (showEndBonusPopup) {
                BalloonConfetti(
                    show = true,
                    parties = Presets.balloons(),
                    onComplete = { quizViewModel.resetEndBonusPopup() }
                )
                BonusPopup(
                    message = "🎈 Amazing! 7 correct \uD83C\uDF88 50 bonus points!",
                    backgroundColor = PinkPrimary,
                    showConfetti = true,
                    confettiType = "balloons",
                    onDismiss = { quizViewModel.resetEndBonusPopup() }
                )
            }


        }//main box
    }
    if (showTryAgainModal) {
        BonusPopup(
            message = "😢 Only $correctAnswers correct. Try again!",
            backgroundColor = OrangeAccent,
            onDismiss = { showTryAgainModal = false }
        )
    }

    // ✅ Show the Finish Modal before navigating to results
  /*  if (showFinishModal) {
        FinishModal(
            totalPoints = quizViewModel.totalPoints.collectAsState().value,
            onDismissRequest = { showFinishModal = false },
            onConfirmation = { shouldNavigateToResults = true }
        )
    }*/
}
