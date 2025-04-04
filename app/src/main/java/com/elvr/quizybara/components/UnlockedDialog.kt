package com.elvr.quizybara.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.elvr.quizybara.ui.theme.DarkBackground
import com.elvr.quizybara.ui.theme.GradiantSemiTransparentBckg
import com.elvr.quizybara.ui.theme.GuessLogoGradient
import com.elvr.quizybara.ui.theme.PinkPrimary
import com.elvr.quizybara.ui.theme.YellowAccent
import com.elvr.quizybara.ui.theme.LightText
import com.elvr.quizybara.ui.theme.PinkBlushGradient
import com.elvr.quizybara.utils.Presets
import kotlinx.coroutines.delay
import nl.dionsegijn.konfetti.compose.KonfettiView
import nl.dionsegijn.konfetti.compose.OnParticleSystemUpdateListener
import nl.dionsegijn.konfetti.core.PartySystem

@Composable
fun UnlockedDialog(
    justUnlocked: List<Int>,
    onDismiss: () -> Unit,
    getCategoryNameById: (Int) -> String
) {
    var confettiActive by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(2500)
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (confettiActive) {
            KonfettiView(
                modifier = Modifier.fillMaxSize(),
                parties = Presets.festive(),
                updateListener = object : OnParticleSystemUpdateListener {
                    override fun onParticleSystemEnded(system: PartySystem, activeSystems: Int) {
                        if (activeSystems == 0) confettiActive = false
                    }
                }
            )
        }

        AnimatedVisibility(
            visible = true,
            enter = fadeIn() + scaleIn()
        ) {
            Card(
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .padding(24.dp)
                   // .background(GradiantSemiTransparentBckg)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                colors = CardDefaults.cardColors(containerColor = DarkBackground)
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "🎉 New Category Unlocked!",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = YellowAccent,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "You've unlocked:",
                        style = MaterialTheme.typography.bodyLarge,
                        color = LightText,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    justUnlocked.forEach { id ->
                        Text(
                            text = " ${getCategoryNameById(id)}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = PinkPrimary,
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            confettiActive = false
                            onDismiss()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = PinkPrimary,
                            contentColor = LightText
                        )
                    ) {
                        Text("Awesome!")
                    }
                }
            }
        }
    }
}
