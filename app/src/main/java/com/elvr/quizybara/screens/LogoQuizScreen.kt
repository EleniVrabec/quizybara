package com.elvr.quizybara.screens

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.elvr.quizybara.components.BonusPopup
import com.elvr.quizybara.components.BalloonConfetti
import com.elvr.quizybara.components.FinishModal
import com.elvr.quizybara.components.PointsBadge
import com.elvr.quizybara.components.QuestionCard
import com.elvr.quizybara.components.QuizProgressBar
import com.elvr.quizybara.components.loader.CircleLoader
import com.elvr.quizybara.ui.theme.LoaderPrimaryColor
import com.elvr.quizybara.ui.theme.LoaderSecondaryColor
import com.elvr.quizybara.utils.Presets
import com.elvr.quizybara.viewmodels.QuizViewModel
import com.elvr.quizybara.components.LocalLogoQuestionCard
import com.elvr.quizybara.ui.theme.BlueAccent
import com.elvr.quizybara.ui.theme.ComputersGradient
import com.elvr.quizybara.ui.theme.DarkBackground
import com.elvr.quizybara.ui.theme.OrangeAccent
import com.elvr.quizybara.ui.theme.PinkPrimary


@Composable
fun LogoQuizScreen(navController: NavController, quizViewModel: QuizViewModel) {
    val context = LocalContext.current
    val questions = quizViewModel.logoQuestions.collectAsState()
    val currentQuestionIndex = quizViewModel.currentQuestionIndex.collectAsState()
    val correctAnswers = quizViewModel.correctAnswers.collectAsState().value
    val showStreakBonusPopup by quizViewModel.showStreakBonusPopup.collectAsState()
    val showEndBonusPopup by quizViewModel.showEndBonusPopup.collectAsState()
    val hasTriggeredEndBonus = quizViewModel.hasTriggeredEndBonus.collectAsState().value

    var selectedAnswer by remember { mutableStateOf<String?>(null) }
    var showFinishModal by remember { mutableStateOf(false) }
    var shouldNavigateToResults by remember { mutableStateOf(false) }
    var key by remember { mutableStateOf(0) }
    var showTryAgainModal by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        quizViewModel.fetchLocalLogoQuestions(context)
    }

    LaunchedEffect(shouldNavigateToResults) {
        if (shouldNavigateToResults) {
            showFinishModal = false
            navController.navigate("results")
        }
    }

    if (questions.value.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize() .background(DarkBackground), contentAlignment = Alignment.Center) {
           // Text("Loading logo questions...", style = MaterialTheme.typography.headlineMedium)
            CircleLoader(
                isVisible = true,
                color = LoaderPrimaryColor,
                secondColor = LoaderSecondaryColor,
                modifier = Modifier.size(100.dp)
            )
        }
    } else {
        val isLastQuestion = currentQuestionIndex.value == questions.value.size - 1
        val question = questions.value[currentQuestionIndex.value]

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(ComputersGradient)
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
               /* LinearProgressIndicator(
                    progress = (currentQuestionIndex.value + 1) / questions.value.size.toFloat(),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
                )*/
                QuizProgressBar(
                    currentQuestion = currentQuestionIndex.value,
                    totalQuestions = questions.value.size,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
                LocalLogoQuestionCard(
                    key = key,
                    question = question.questionText,
                    logoFileName = question.flagUrl ?: "placeholder.svg", // ← fallback
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

                            if (earned7OrMore && !hasTriggeredEndBonus) {
                                quizViewModel.addBonusPoints(50)
                                quizViewModel.triggerEndBonusPopup()
                                quizViewModel.markEndBonusTriggered()
                            }
                            if (quizViewModel.correctAnswers.value < 5) {
                                showTryAgainModal = true
                            }

                            showFinishModal = true
                            Handler(Looper.getMainLooper()).postDelayed({
                                quizViewModel.resetStreakFlag()
                                quizViewModel.resetEndBonusFlag()
                                shouldNavigateToResults = true
                            }, 3500)
                        } else {
                            quizViewModel.moveToNextQuestion(
                                navController,
                                { showFinishModal = it },
                                { shouldNavigateToResults = true }
                            )
                        }
                    }
                )

                /*
                QuestionCard(
                    key = key,
                    question = question.questionText,
                    flagUrl = question.flagUrl,
                    answers = question.options,
                    questionType = question.type,
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

                            if (earned7OrMore && !hasTriggeredEndBonus) {
                                quizViewModel.addBonusPoints(50)
                                quizViewModel.triggerEndBonusPopup()
                                quizViewModel.markEndBonusTriggered()
                            }
                            if (quizViewModel.correctAnswers.value < 5) {
                                showTryAgainModal = true
                            }

                            showFinishModal = true
                            Handler(Looper.getMainLooper()).postDelayed({
                                quizViewModel.resetStreakFlag()
                                quizViewModel.resetEndBonusFlag()
                                shouldNavigateToResults = true
                            }, 3500)
                        } else {
                            quizViewModel.moveToNextQuestion(
                                navController,
                                { showFinishModal = it },
                                { shouldNavigateToResults = true }
                            )
                        }
                    }
                )//question card
                */
            }

            // 🎯 Points badge (optional)
            Box(
                modifier = Modifier.align(Alignment.TopEnd).padding(16.dp)
            ) {
                PointsBadge(quizViewModel.totalPoints.collectAsState().value)
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
                    message = "🎈 Amazing! 7 correct 🎉 50 bonus points!",
                    backgroundColor = PinkPrimary,
                    showConfetti = true,
                    confettiType = "balloons",
                    onDismiss = { quizViewModel.resetEndBonusPopup() }
                )
            }
        }
    }

    if (showTryAgainModal) {
        BonusPopup(
            message = "😢 Only $correctAnswers correct. Try again!",
            backgroundColor = OrangeAccent,
            onDismiss = { showTryAgainModal = false }
        )
    }
/*
    if (showFinishModal) {
        FinishModal(
            totalPoints = quizViewModel.totalPoints.collectAsState().value,
            onDismissRequest = { showFinishModal = false },
            onConfirmation = { shouldNavigateToResults = true }
        )
    }*/
}
