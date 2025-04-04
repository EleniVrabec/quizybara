package com.elvr.quizybara.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.elvr.quizybara.components.AnimatedGradientBackground
import com.elvr.quizybara.components.GradientButton
import com.elvr.quizybara.components.PointsBadge
import com.elvr.quizybara.components.ResetButton
import com.elvr.quizybara.viewmodels.QuizViewModel
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.draw.clip
import kotlinx.coroutines.delay
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.onGloballyPositioned
import com.elvr.quizybara.components.ConfettiOverlay
import com.elvr.quizybara.components.PointsHeaderAnimated
import com.elvr.quizybara.utils.Presets
import androidx.compose.material3.TextFieldDefaults
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.core.content.ContextCompat
import com.elvr.quizybara.Routes

import nl.dionsegijn.konfetti.compose.KonfettiView
import nl.dionsegijn.konfetti.compose.OnParticleSystemUpdateListener
import nl.dionsegijn.konfetti.core.PartySystem
import com.elvr.quizybara.components.RewardFlyInOverlay
import com.elvr.quizybara.ui.theme.*



@Composable
fun StartScreen(navController: NavController, quizViewModel: QuizViewModel) {
    val nickname by quizViewModel.nickname.collectAsState()
    val showInput = nickname.isNullOrBlank()
    // track whether the reward was already given during this session
    var rewardAlreadyGiven by rememberSaveable { mutableStateOf(false) }

    var newName by remember { mutableStateOf("") }
    var showFireworks by remember { mutableStateOf(false) }

    val totalPoints by quizViewModel.totalPoints.collectAsState()
    // State for animated message
    var animatedMessage by remember { mutableStateOf("Welcome, $nickname!") }
    var selectedEmoji by remember { mutableStateOf("🎉") }
    var triggerBounce by remember { mutableStateOf(false) }

    var showRewardPopup by remember { mutableStateOf(false) }
    var earnedPoints by remember { mutableStateOf(0) }
   // var badgeOffset by remember { mutableStateOf(Offset.Zero) }
    var toastOffset by remember { mutableStateOf(Offset.Zero) }
    var gemTargetOffset by remember { mutableStateOf(Offset.Zero) }
    val buttonModifier = Modifier
        .fillMaxWidth(0.8f)

    val glowBrush = Brush.linearGradient(
        colors = listOf(
            Color(0xFF5374E7).copy(alpha = 0.6f),
            Color(0xFFB350E7).copy(alpha = 0.4f),
            Color(0xFFE750B3).copy(alpha = 0.3f),
            Color.Transparent
        ),
       // start = Offset(animatedOffset, animatedOffset),
       // end = Offset(-animatedOffset, -animatedOffset)
    )
// Update message + trigger bounce on nickname change
    LaunchedEffect(nickname) {
        if (!nickname.isNullOrBlank() && !rewardAlreadyGiven) {
            val welcomeEmojis = listOf("🎉", "🔥", "🧠", "\uD83E\uDDD0","\uD83D\uDCDA","\uD83D\uDCA1","🚀", "🏆", "👑", "🌟", "😎", "💥", "🎮", "\uD83D\uDCAA", "\uD83E\uDDB8", "✨")

            val welcomeMessages = listOf(
                "Welcome back, $nickname!",
                "Let's go, $nickname!",
                "Quiz legend $nickname returns!",
                "Time to shine, $nickname!",
                "You're back, $nickname! Ready to conquer?",
                "The quizybara hero arrives — welcome, $nickname! ",
                "Back in action, $nickname!",
                "Let’s make brainwaves, $nickname!",
                "We missed your big brain, $nickname!",
                "Game on, $nickname!",
                "Welcome, $nickname! The quiz awaits ",
                "Back to flex those facts, $nickname! ",
                "You’ve got this, $nickname!",
                "$nickname is here to dominate!",
                "Show us what you’ve got, $nickname!",
                "The comeback is real — welcome $nickname!",
                "Your knowledge is unmatched, $nickname!",
                "Welcome to the arena, $nickname!",
                "Pop quiz? More like pop legend — welcome $nickname!",
                "Time to level up, $nickname!"
            )

            delay(300)
            animatedMessage = welcomeMessages.random()
            selectedEmoji = welcomeEmojis.random()
            triggerBounce = false
            delay(100)
            triggerBounce = true

            // 🟨 Reward logic
            val isFirstTime = quizViewModel.isFirstTimeUser()
            val randomPoints = (1..10).random()
            earnedPoints = if (isFirstTime) 50 else randomPoints
            quizViewModel.addPoints(earnedPoints)
            showRewardPopup = true
            rewardAlreadyGiven = true

            if (isFirstTime) {
                // ✅ Now it's safe to mark as not first time
                quizViewModel.markUserAsReturning()
            }
        }
    }


// Bounce animation using animateFloatAsState
    val scale by animateFloatAsState(
        targetValue = if (triggerBounce) 1.1f else 0.8f,
        animationSpec = tween(durationMillis = 500)
    )



    Box(
        modifier = Modifier.fillMaxSize(), // Ensures content is layered properly
        contentAlignment = Alignment.Center
    ) {
        AnimatedGradientBackground()
        ConfettiOverlay(
            show = showFireworks,
            parties = Presets.fireworks(),
            onComplete = { showFireworks = false }
        )

        // 🟨 Badge in top-right corner
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            if (!showInput) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                        .onGloballyPositioned { coords ->
                            val position = coords.localToWindow(Offset.Zero)
                          //  badgeOffset = position
                            toastOffset = position
                        }
                ) {
                    PointsHeaderAnimated(
                        targetPoints = totalPoints,
                        gemIconOffset = { gemTargetOffset = it }
                        )
                  //  PointsBadge(points = totalPoints)
                }
            }



        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
                .padding(bottom = 56.dp)
            ,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (showInput) {
                Text(
                    "Welcome! What's your nickname?",
                            modifier = Modifier
                            .padding(horizontal = 16.dp)
                        .scale(scale),
                    style = MaterialTheme.typography.headlineMedium.copy(
                        //fontWeight = FontWeight.Bold,
                        letterSpacing = 1.sp,
                        shadow = Shadow(
                            color = Color.Black.copy(alpha = 0.3f),
                            offset = Offset(2f, 2f),
                            blurRadius = 6f
                        )
                    ),
                    color = LightText, // or any color you like
                    textAlign = TextAlign.Center

                )
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                       /* .border(
                            width = 4.dp,
                            brush = Brush.linearGradient(
                                colors = listOf(
                                    PinkPrimary, BlueAccent
                                )
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )*/
                        .clip(RoundedCornerShape(12.dp)) // Apply same shape to clip background
                      //  .background(glowBrush)
                        .padding(2.dp) // Space between border and text field
                ) {
                    OutlinedTextField(
                        value = newName,
                        onValueChange = { newName = it },
                        label = { Text("Nickname") },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = LightText,     // Text color when active
                            unfocusedTextColor = LightText,
                           focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = PinkPrimary,
                            unfocusedIndicatorColor = LightPink,
                            focusedLabelColor = PinkPrimary,
                            unfocusedLabelColor = LightPink,
                            cursorColor = PinkPrimary,
                            disabledLabelColor = Color.LightGray,
                            disabledIndicatorColor = Color.LightGray
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                /* OutlinedTextField(
                     modifier = Modifier
                         .fillMaxWidth()
                         .padding(10.dp),
                     value = newName,
                     onValueChange = { newName = it },
                     label = { Text("Nickname") },
                     singleLine = true,
                     shape = MaterialTheme.shapes.medium
                 )*/
                Spacer(modifier = Modifier.height(8.dp))
               GradientButton(
                   text = "Continue",

                    onClick = {
                        if (newName.isNotBlank()) {
                            quizViewModel.saveNickname(newName.trim())
                            showFireworks = true
                        }
                    }
                )
            } else {
                AnimatedContent(
                    targetState = animatedMessage,
                    transitionSpec = {
                        fadeIn(tween(500)) togetherWith fadeOut(tween(200))
                    },
                    label = "welcomeMessage"
                ) { message ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = selectedEmoji,
                            fontSize = 32.sp,
                            modifier = Modifier
                                .padding(bottom = 4.dp)
                                .scale(scale)
                        )
                        Text(
                            text = message,
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .scale(scale), // 👈 This applies the bounce scale
                            style = MaterialTheme.typography.headlineLarge.copy(
                                shadow = Shadow(
                                    color = LightText,
                                    offset = Offset(2f, 2f),
                                    blurRadius = 6f
                                )
                            ),
                            color = Pink80,
                            textAlign = TextAlign.Center
                        )//text message
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                GradientButton(
                    text = "Get Started",
                    onClick = { navController.navigate("choose_game") }
                )



            }


           // ResetButton(quizViewModel)


        }//column
        val context = LocalContext.current
        Box(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(12.dp),
                // .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Privacy Policy",
                    color = Color.LightGray,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .clickable {
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("https://elenivrabec.github.io/quizybara_privacy_policy/")
                            )
                            context.startActivity(intent)
                        }
                )
                Text("  •  ", color = Color.Gray) // Separator dot

                Text(
                    text = "Game Rules",
                    color = Color.LightGray,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier
                        .clickable {
                            navController.navigate(Routes.GameRules)

                        }
                )
            }
        }


        RewardFlyInOverlay(

            show = showRewardPopup,
            points = earnedPoints,
            badgeTargetOffset = gemTargetOffset,
            onComplete = {
                showRewardPopup = false
            }
        )
    }
}

@Composable
@Preview(showBackground = true)
fun StartScreenPreview() {
    //StartScreen(navController = NavController(LocalContext.current), quizViewModel = QuizViewModel())
}