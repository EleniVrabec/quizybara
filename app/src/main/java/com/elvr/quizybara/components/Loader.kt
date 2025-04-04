package com.elvr.quizybara.components

import androidx.compose.ui.graphics.drawscope.Stroke


import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Loader(
    modifier: Modifier = Modifier.size(80.dp),
    strokeWidth: Dp = 6.dp,
    color: Color = Color(0xFF4CAF50) // Green
) {
    val infiniteTransition = rememberInfiniteTransition(label = "rotation")

    val angleOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "angle"
    )

    Canvas(modifier = modifier) {
        val canvasSize = size.minDimension
        val strokePx = strokeWidth.toPx()

        drawArc(
            color = color,
            startAngle = angleOffset,
            sweepAngle = 270f,
            useCenter = false,
            style = Stroke(width = strokePx, cap = StrokeCap.Round),
            size = Size(canvasSize, canvasSize),
            topLeft = Offset((size.width - canvasSize) / 2, (size.height - canvasSize) / 2)
        )
    }
}
