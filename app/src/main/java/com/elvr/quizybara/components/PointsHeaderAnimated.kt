package com.elvr.quizybara.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.elvr.quizybara.R
import kotlinx.coroutines.delay
import com.elvr.quizybara.ui.theme.*


@Composable
fun PointsHeaderAnimated(
    targetPoints: Int,
    startImmediately: Boolean = true,
    gemIconOffset: (Offset) -> Unit // 👈 this is passed in from the StartScreen
) {
    var animatedPoints by remember { mutableIntStateOf(0) }
    var showBounce by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (showBounce) 1.4f else 1f,
        animationSpec = tween(300),
        label = "starBounce"
    )

    val density = LocalDensity.current

    LaunchedEffect(targetPoints, startImmediately) {
        if (startImmediately) {
            delay(500)
            for (i in 0..targetPoints) {
                animatedPoints = i
                delay(10)
            }
            showBounce = true
            delay(300)
            showBounce = false
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = PinkPrimary)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = R.drawable.diamond),
                    contentDescription = "Gem icon",
                    modifier = Modifier
                        .size(28.dp)
                        .onGloballyPositioned { coords ->
                            val position = coords.localToWindow(Offset.Zero)
                            gemIconOffset(position) // ✅ now we send it up
                        }
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = " $animatedPoints Points",
                    modifier = Modifier.scale(scale),
                    style = MaterialTheme.typography.titleLarge.copy(fontSize = 24.sp),
                    color = Color.White
                )
            }
        }
    }
}
