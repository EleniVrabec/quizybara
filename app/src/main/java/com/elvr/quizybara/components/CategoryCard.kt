package com.elvr.quizybara.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.material3.Icon
import com.elvr.quizybara.ui.theme.QuizybaraTheme
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Brush
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import kotlinx.coroutines.delay

@Composable
fun CategoryCard(
    icon: ImageVector,
    text: String,
   // backgroundColor: Color,
    backgroundGradient: Brush,
    onClick: () -> Unit,
    isLocked: Boolean = false,
    modifier: Modifier = Modifier
) {
    var isClicked by remember { mutableStateOf(false) }
    val scale = remember { Animatable(0f) } // Start at 0 for bounce-in
    val clickScale = remember { Animatable(1f) }

    // Bounce-in when card appears
    LaunchedEffect(Unit) {
        delay(100) // slight stagger if multiple cards
        scale.animateTo(1f, animationSpec = tween(durationMillis = 600))
    }
    // Outer Box defines layout (square shape via aspectRatio from parent)
    Box(
        modifier = modifier
    ) {
        // Inner Box with clip — this will apply shape consistently to both Card and overlay
        Box(
            modifier = Modifier
                .scale(scale.value * clickScale.value) // bounce-in + click effect
                .aspectRatio(1f)
                .clip(RoundedCornerShape(24.dp))
                .fillMaxSize()
                .clip(MaterialTheme.shapes.medium)
                .background(brush = backgroundGradient)
                .clickable(enabled = !isLocked) {
                    onClick() },
            contentAlignment = Alignment.Center

                    //.padding(4.dp)

            ) {
            // The card

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                       // tint = Color.White,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = text,
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center
                        )
                        //style = MaterialTheme.typography.bodyLarge,
                       // color = Color.White,
                       // textAlign = TextAlign.Center
                    )
                }


            // 🔒 Overlay
            if (isLocked) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(MaterialTheme.shapes.medium)
                        .background(Color.Black.copy(alpha = 0.4f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Locked",
                        tint = Color.Yellow,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }
    }
}


/*
@Composable
fun CategoryCard(
    icon: ImageVector,
    text: String,
    backgroundColor: Color,
    onClick: () -> Unit,
    isLocked: Boolean = false,
    modifier: Modifier = Modifier // ✅ Add this line
) {
    Box(
        modifier = modifier

    ) {
        Card(
            colors = CardDefaults.cardColors(containerColor = backgroundColor),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clip(MaterialTheme.shapes.medium)
                .clickable(enabled = !isLocked) { onClick() }, // prevent click if locked
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center

            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null, // No need for accessibility description since text is present
                    tint = Color.White,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }//column

            }
            if (isLocked) {
                Box(
                    modifier = Modifier
                        .zIndex(10F)
                        .fillMaxSize()
                        .clip(MaterialTheme.shapes.medium)
                        .background(Color.Black.copy(alpha = 0.4f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = "Locked",
                        tint = Color.White,
                        modifier = Modifier.size(40.dp)
                    )
                }

            }//is locked

        }

}
*/
@Preview
@Composable
fun CategoryCardPreview() {
    QuizybaraTheme {
        CategoryCard(
            icon = Icons.Default.Star,
            text = "Locked Category",
            backgroundGradient = Brush.linearGradient(
                colors = listOf(Color(0xFFF2075D), Color(0xFF139DF2))
            ),
           // backgroundColor = Color.Gray,
            onClick = {},
            isLocked = true // ✅ Show lock in preview
        )
    }
}

