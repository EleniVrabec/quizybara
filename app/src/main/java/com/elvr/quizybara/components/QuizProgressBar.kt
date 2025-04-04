package com.elvr.quizybara.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.elvr.quizybara.ui.theme.*

@Composable
fun QuizProgressBar(
    currentQuestion: Int,
    totalQuestions: Int,
    modifier: Modifier = Modifier
) {
    val targetProgress = (currentQuestion + 1) / totalQuestions.toFloat()

    // 🔥 Animate the progress change
    val animatedProgress by animateFloatAsState(
        targetValue = targetProgress,
        animationSpec = tween(durationMillis = 500)
    )

    LinearProgressIndicator(
        progress = { animatedProgress }, // ✅ use animated value
        modifier = modifier,
        color = ProgressBarColor,       // 💙 active color from your theme
        trackColor = ProgressBarTrack   // 🎨 subtle background color
    )
}
