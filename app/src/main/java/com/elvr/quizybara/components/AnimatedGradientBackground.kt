package com.elvr.quizybara.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.sin
import com.elvr.quizybara.ui.theme.*

@Composable
fun AnimatedGradientBackground() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkBackground),
        contentAlignment = Alignment.Center
    ) {
        // Define each blob manually with different sizes and animation parameters
        AnimatedGradientShadow(
            startOffset = Offset(-250f, -150f), // Top-left
            size = 300.dp, // Circle size
            duration = 7000,
            frequencyX = 1.5f,
            frequencyY = 2.0f,
            phaseShift = 0.5f,
            amplitudeX = 150f,
            amplitudeY = 100f
        )
        AnimatedGradientShadow(
            startOffset = Offset(250f, -100f), // Top-right
            size = 350.dp, // Circle size
            duration = 8000,
            frequencyX = 1.8f,
            frequencyY = 1.2f,
            phaseShift = 1.0f,
            amplitudeX = 120f,
            amplitudeY = 180f
        )
        AnimatedGradientShadow(
            startOffset = Offset(-200f, 250f), // Bottom-left
            size = 270.dp, // Circle size
            duration = 7500,
            frequencyX = 2.0f,
            frequencyY = 1.7f,
            phaseShift = 0.7f,
            amplitudeX = 140f,
            amplitudeY = 110f
        )
        AnimatedGradientShadow(
            startOffset = Offset(200f, 300f), // Bottom-right
            size = 280.dp, // Circle size
            duration = 8500,
            frequencyX = 1.2f,
            frequencyY = 2.2f,
            phaseShift = 1.2f,
            amplitudeX = 160f,
            amplitudeY = 130f
        )
        AnimatedGradientShadow(
            startOffset = Offset(0f, 300f), // Center-bottom
            size = 80.dp, // Smaller Circle
            duration = 6500,
            frequencyX = 2.5f,
            frequencyY = 1.5f,
            phaseShift = 0.3f,
            amplitudeX = 80f,
            amplitudeY = 70f
        )
        AnimatedGradientShadow(
            startOffset = Offset(300f, -100f), // Far right
            size = 140.dp, // Medium Circle
            duration = 7500,
            frequencyX = 1.7f,
            frequencyY = 2.3f,
            phaseShift = 0.9f,
            amplitudeX = 90f,
            amplitudeY = 120f
        )
    }
}

@Composable
fun AnimatedGradientShadow(
    startOffset: Offset,
    size: androidx.compose.ui.unit.Dp,
    duration: Int,
    frequencyX: Float,
    frequencyY: Float,
    phaseShift: Float,
    amplitudeX: Float,
    amplitudeY: Float
) {
    val infiniteTransition = rememberInfiniteTransition()

    // Create a continuous animation value from 0 to 1
    val animationProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = duration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    // Calculate the position using smooth sine wave motion
    val position by remember(animationProgress) {
        derivedStateOf {
            val x = startOffset.x + amplitudeX * sin(frequencyX * animationProgress * 2 * PI.toFloat())
            val y = startOffset.y + amplitudeY * sin(frequencyY * animationProgress * 2 * PI.toFloat() + phaseShift)
            Offset(x, y)
        }
    }

    // Animate gradient color shifting
    val color1 by infiniteTransition.animateColor(
        initialValue = Color(0xFF1E40AF).copy(alpha = 0.3f), // HSL(226, 81%, 64%) - Deep Blue
        targetValue = Color(0xFF7C3AED).copy(alpha = 0.3f), // HSL(271, 81%, 64%) - Purple (Violet)
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = duration, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val color2 by infiniteTransition.animateColor(
        initialValue = Color(0xFFEC4899).copy(alpha = 0.1f), // HSL(316, 81%, 64%) - Magenta (Pink)
        targetValue = Color(0xFFEF4444).copy(alpha = 0.1f), // HSL(1, 81%, 64%) - Red
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = duration, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    val color5 = Color.White.copy(alpha = 0.05f)

    Box(
        modifier = Modifier
            .absoluteOffset(x = position.x.dp, y = position.y.dp)
            .size(size)
            .clip(CircleShape)
            .background(Brush.radialGradient(listOf(color1, color2, color5)))
            .blur(280.dp)
            .scale(1.1f)
    )
}