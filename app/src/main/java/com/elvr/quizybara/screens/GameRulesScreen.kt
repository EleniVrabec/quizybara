package com.elvr.quizybara.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.elvr.quizybara.ui.theme.ComputersGradient
import com.elvr.quizybara.ui.theme.LightText
import com.elvr.quizybara.ui.theme.YellowAccent

@Composable
fun GameRulesScreen(navController: NavController) {
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ComputersGradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(horizontal = 24.dp, vertical = 32.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            Text(
                "🎮 Game Rules",
                style = MaterialTheme.typography.headlineMedium,
                color = YellowAccent
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("📚 Trivia Mode",
                style = MaterialTheme.typography.titleLarge,
                color = YellowAccent
            )
            Text(
                "Choose a category like Geography, History, Science, and more.\nAnswer questions correctly to earn points. Some categories are locked—earn points to unlock them!",
                style = MaterialTheme.typography.bodyLarge,
                        color = LightText
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text("🚩 Flags Quiz",
                style = MaterialTheme.typography.titleLarge,
                color = YellowAccent
            )
            Text(
                "Test your knowledge of world flags! All flag images are stored offline for a smooth experience. Select the correct country for each flag.",
                style = MaterialTheme.typography.bodyLarge,
                color = LightText
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text("🏢 Logos Quiz",
                style = MaterialTheme.typography.titleLarge,
                color = YellowAccent
            )
            Text(
                "Can you guess the brand just from its logo?\nLike the Flag Quiz, logo files are local. Great for logo lovers!",
                style = MaterialTheme.typography.bodyLarge,
                color = LightText
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text("🧠 Word Match",
                style = MaterialTheme.typography.titleLarge,
                color = YellowAccent
            )
            Text(
                "Tap 2 words that go together. If they’re a correct pair, they turn green and fade out!\nGet all pairs before the timer runs out (30 seconds) to win.",
                style = MaterialTheme.typography.bodyLarge,
                color = LightText
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text("💡 Tips",
                style = MaterialTheme.typography.titleLarge,
                color = YellowAccent
            )
            Text(
                "• Bonus points for streaks (5 in a row) and fast finishes.\n" +
                        "• Earn at least 7 correct to get end-of-quiz rewards!\n" +
                        "• Unlock new categories as you play.\n" +
                        "• Have fun and challenge yourself! 🎯",
                style = MaterialTheme.typography.bodyLarge,
                color = LightText
            )

            Spacer(modifier = Modifier.height(48.dp))
        }

        // 🔙 Back button top-left
        IconButton(
            onClick = { navController.navigate("start") },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = YellowAccent
            )
        }
    }
}
