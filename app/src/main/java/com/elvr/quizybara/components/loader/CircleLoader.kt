package com.elvr.quizybara.components.loader


import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.toRect
import androidx.compose.ui.graphics.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.unit.dp
import com.elvr.quizybara.ui.theme.DarkBackground

@Composable
fun CircleLoader(
    modifier: Modifier,
    isVisible: Boolean,
    color: Color,
    secondColor: Color? = color,
    tailLength: Float = 140f,
    smoothTransition: Boolean = true,
    strokeStyle: StrokeStyle = StrokeStyle(),
    cycleDuration: Int = 1400
) {
    val transition = rememberInfiniteTransition()
    val spinAngle by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = cycleDuration, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val tailToDisplay = remember { Animatable(0f) }

    LaunchedEffect(isVisible) {
        val targetTail = if (isVisible) tailLength else 0f
        if (smoothTransition) {
            tailToDisplay.animateTo(
                targetValue = targetTail,
                animationSpec = tween(cycleDuration, easing = LinearEasing)
            )
        } else {
            tailToDisplay.snapTo(targetTail)
        }
    }

    Canvas(
        modifier = modifier
           // .background(DarkBackground)
            .rotate(spinAngle)
            .aspectRatio(1f)
    ) {
        listOfNotNull(color, secondColor).forEachIndexed { index, circleColor ->
            rotate(if (index == 0) 0f else 180f) {
                val brush = Brush.sweepGradient(
                    0f to Color.Transparent,
                    tailToDisplay.value / 360f to circleColor,
                    1f to Color.Transparent
                )
                val paint = setupPaint(strokeStyle, brush)

                drawIntoCanvas { canvas ->
                    canvas.drawArc(
                        rect = size.toRect(),
                        startAngle = 0f,
                        sweepAngle = tailToDisplay.value,
                        useCenter = false,
                        paint = paint
                    )
                }
            }
        }
    }
}

fun DrawScope.setupPaint(style: StrokeStyle, brush: Brush): Paint {
    val paint = Paint().apply {
        isAntiAlias = true
        this.style = PaintingStyle.Stroke
        strokeWidth = style.width.toPx()
        strokeCap = style.strokeCap
        brush.applyTo(size, this, 1f)
    }

    style.glowRadius?.let {
        paint.asFrameworkPaint().setShadowLayer(
            it.toPx(),
            0f,
            0f,
            android.graphics.Color.WHITE
        )
    }

    return paint
}
