package com.elvr.quizybara.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.graphicsLayer
import com.elvr.quizybara.ui.theme.BlueAccent
import com.elvr.quizybara.ui.theme.ButtonCorrectColor
import com.elvr.quizybara.ui.theme.ButtonIncorrectColor
import com.elvr.quizybara.ui.theme.YellowAccent
import com.elvr.quizybara.viewmodels.QuizViewModel

@Composable
fun WordCard(
    word: String,
    isSelected: Boolean,
    isMatched: Boolean,
    isGlowing: Boolean,
    isWrong: Boolean,
    onClick: () -> Unit
) {
    var visible by remember { mutableStateOf(true) }

    // Animate visibility fade out for matched
    val alpha by animateFloatAsState(
        targetValue = if (isMatched && !isGlowing) 0f else 1f,
        animationSpec = tween(durationMillis = 500), label = "cardAlpha"
    )

    // Animate background color
    val backgroundColor by animateColorAsState(
        targetValue = when {
            isMatched || isGlowing -> ButtonCorrectColor
            isWrong -> ButtonIncorrectColor
            isSelected -> YellowAccent
            else -> BlueAccent
        },
        label = "cardBackground"
    )

    val scale = remember { Animatable(1f) }
    LaunchedEffect(isSelected) {
        if (isSelected) {
            scale.snapTo(1.1f)
            scale.animateTo(1f, tween(200))
        }
    }

    if (visible) {
        Card(
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .padding(8.dp)
                .graphicsLayer {
                    scaleX = scale.value
                    scaleY = scale.value
                    this.alpha = alpha
                }
                .fillMaxWidth()
                .aspectRatio(1.5f)
                .clickable(enabled = !isMatched) {
                    onClick()
                },
            colors = CardDefaults.cardColors(containerColor = backgroundColor),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Box(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = word,
                    color = Color.White,
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}
