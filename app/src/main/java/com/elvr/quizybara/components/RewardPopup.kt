package com.elvr.quizybara.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.elvr.quizybara.R // make sure your coin icon is in res/drawable
import com.elvr.quizybara.ui.theme.PinkPrimary

@Composable
fun RewardFlyInOverlay(
    show: Boolean,
    points: Int,
    badgeTargetOffset: Offset,
    onComplete: () -> Unit
) {
    if (!show) return

    val scope = rememberCoroutineScope()
    var showCoins by remember { mutableStateOf(false) }
    var startOffset by remember { mutableStateOf(Offset.Zero) }
    val density = LocalDensity.current
    LaunchedEffect(Unit) {
        delay(500)
        showCoins = true
        delay(2000) // let coins fly
        onComplete()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ) {
        Box(
           // modifier = Modifier
               // .background(Color.Black.copy(alpha = 0.9f)),
            contentAlignment = Alignment.Center
               ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(PinkPrimary.copy(alpha = 0.9f))
                    .padding(36.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Image(
                    painter = painterResource(id = R.drawable.diamond),
                    contentDescription = "Gem icon",
                    modifier = Modifier.size(32.dp)
                        .onGloballyPositioned { coords ->

                            val position = coords.localToWindow(Offset.Zero)
                            val pxOffset = with(density) { position }
                            startOffset = pxOffset
                        }
                )
                Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "You earned $points points!",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,

            )

            Spacer(modifier = Modifier.height(20.dp))
            }
            if (showCoins && startOffset != Offset.Zero) {
                val numberOfGems = (points / 10).coerceAtLeast(10)
                FlyCoins(startOffset = startOffset, targetOffset = badgeTargetOffset, gemCount = numberOfGems)
            }
        }
    }
}

@Composable
fun FlyCoins(startOffset: Offset, targetOffset: Offset, gemCount: Int = 5) {
    val coinCount = 5
    val density = LocalDensity.current
    val screenCenter = with(density) {
        Offset(
            x = LocalConfiguration.current.screenWidthDp.dp.toPx() / 2,
            y = LocalConfiguration.current.screenHeightDp.dp.toPx() / 2
        )
    }
    //val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    //val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val scope = rememberCoroutineScope()

    val positions = remember {
        List(gemCount) {
            Animatable(startOffset, Offset.VectorConverter)
           /* Animatable(initialValue = Offset(0f, 0f), typeConverter = TwoWayConverter(
                convertToVector = { AnimationVector2D(it.x, it.y) },
                convertFromVector = { Offset(it.v1, it.v2) }
            ))*/

        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        positions.forEach { animatable ->
            Image(
                painter = painterResource(id = R.drawable.diamond), // your coin icon
                contentDescription = "Flying coin",
                modifier = Modifier
                    .size(24.dp)
                    .offset {
                        IntOffset(
                            animatable.value.x.toInt(),
                            animatable.value.y.toInt()
                        )
                    }
            )
        }

        LaunchedEffect(Unit) {
            positions.forEachIndexed { i, animatable ->
                scope.launch {
                    delay(i * 100L)
                    animatable.animateTo(
                        targetOffset,
                       // Offset(screenWidth.value * 0.9f, 60f), // target: badge
                        animationSpec = tween(1000, easing = FastOutSlowInEasing)
                    )
                }
            }
        }
    }
}
