package com.elvr.quizybara.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import com.elvr.quizybara.ui.theme.*

@Composable
fun PointsBadge(points: Int) {
    val infiniteTransition = rememberInfiniteTransition()
    val pulse by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse"
    )

    val scaleBounce = remember { Animatable(1f) }
    LaunchedEffect(points) {
        scaleBounce.snapTo(1.3f)
        scaleBounce.animateTo(1f, animationSpec = tween(400))
    }

    val finalScale = pulse * scaleBounce.value

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(16.dp)
            .wrapContentSize()
    ) {
        // 🔆 Glowing background circle
        Box(
            modifier = Modifier
                .matchParentSize()
                .graphicsLayer {
                    scaleX = finalScale * 1.4f
                    scaleY = finalScale * 1.4f
                    alpha = 0.4f
                    shadowElevation = 0f
                    shape = CircleShape
                    clip = false
                }
                .background(Color.Yellow.copy(alpha = 0.4f), shape = CircleShape)
                .blur(26.dp)
        )

        // 🟡 Foreground badge
        Box(
            modifier = Modifier
                .graphicsLayer {
                    scaleX = finalScale
                    scaleY = finalScale
                }
                .background(YellowAccent, shape = CircleShape)
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Text("\uD83D\uDC8E $points", style = MaterialTheme.typography.labelLarge)
        }
    }
}
