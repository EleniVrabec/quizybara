package com.elvr.quizybara.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.elvr.quizybara.ui.theme.Pink80

@Composable
fun GradientButton(
    text: String = "Get Started",
    onClick: () -> Unit
) {
    // Animate gradient movement
    val infiniteTransition = rememberInfiniteTransition()
    val animatedOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 400f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 5000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
/*
    val gradientBrush = Brush.linearGradient(
        colors = listOf(
            Color(0xFF5374E7), // Blue
            Color(0xFFB350E7), // Purple
            Color(0xFFE750B3), // Pink
            Color(0xFFE75050), // Red
            Color(0xFFE7B350), // Orange
            Color(0xFF50E750), // Green
            Color(0xFF50E7B3), // Cyan
            Color(0xFF5074E7)  // Back to Blue
        ),
        start = Offset(animatedOffset, animatedOffset),
        end = Offset(-animatedOffset, -animatedOffset)
    )*/
    val glowBrush = Brush.linearGradient(
        colors = listOf(
            Color(0xFF5374E7).copy(alpha = 0.6f),
            Color(0xFFB350E7).copy(alpha = 0.4f),
            Color(0xFFE750B3).copy(alpha = 0.3f),
            Color.Transparent
        ),
        start = Offset(animatedOffset, animatedOffset),
        end = Offset(-animatedOffset, -animatedOffset)
    )

    val borderBrush = Brush.linearGradient(
        colors = listOf(
            Color(0xFF5374E7),
            Color(0xFFB350E7),
            Color(0xFFE750B3),
            Color(0xFFE75050),
            Color(0xFFE7B350),
            Color(0xFF50E750),
            Color(0xFF50E7B3),
            Color(0xFF5074E7)
        ),
        start = Offset(animatedOffset, animatedOffset),
        end = Offset(-animatedOffset, -animatedOffset)
    )


    Box(
        modifier = Modifier

            //.size(width = 180.dp, height = 60.dp)
            .padding(16.dp), // Padding so shadow isn't clipped
        contentAlignment = Alignment.Center
    ) {
        // 🔮 Shadow Glow Layer Behind Button
        Box(
            modifier = Modifier

                .matchParentSize()
                .clip(RoundedCornerShape(50.dp))
                .background(glowBrush)
                .blur(radius = 20.dp) // Blur to create glow
                .scale(1.15f) // Slightly larger to create edge glow
        )
        // 🌈 Actual Button Content
    Box(
        modifier = Modifier
           //.size(width = 180.dp, height = 80.dp)
            .border(
                width = 6.dp,
                brush = borderBrush,
                shape = RoundedCornerShape(50.dp)
            )
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(50.dp))
            //.background(Color(0xFF3A3A3A)) // Button background
            .clickable { onClick() },

            //.padding(vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
               // .size(width = 160.dp, height = 45.dp)

                .clip(RoundedCornerShape(22.dp))
               /* .background(
                    Brush.verticalGradient(
                        colors = listOf(Color(0xFF2B2B2B), Color(0xFF444444))
                    )
                )*/
            .padding(horizontal = 20.dp, vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(

                text = text,
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold,
                color = Pink80
            )
        }
    }//box
}
}

@Composable
@Preview(showBackground = true)
fun GradientButtonPreview() {
    GradientButton(onClick = {})
}