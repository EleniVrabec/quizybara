package com.elvr.quizybara.screens
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.elvr.quizybara.ui.theme.ButtonCorrectColor
import com.elvr.quizybara.ui.theme.ButtonIncorrectColor
import com.elvr.quizybara.ui.theme.ComputersGradient
import com.elvr.quizybara.ui.theme.LightText
import com.elvr.quizybara.ui.theme.PinkPrimary
import com.elvr.quizybara.ui.theme.YellowAccent
import com.elvr.quizybara.viewmodels.QuizViewModel

@Composable
fun ResultsPairWordScreen(navController: NavController, quizViewModel: QuizViewModel) {
    val correctPairs = quizViewModel.correctPairsCount.collectAsState().value
    val totalPairs = quizViewModel.totalPairsCount.collectAsState().value
    val elapsedTime = quizViewModel.elapsedTime.collectAsState().value

    val isWin = correctPairs == totalPairs
    val resultText = if (isWin) "🎉 You Win!" else "❌ You Lose!"
    val resultColor = if (isWin) ButtonCorrectColor else ButtonIncorrectColor


    Column(
        modifier = Modifier.fillMaxSize()
            .background(ComputersGradient)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(resultText,   modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.headlineLarge, color = if (correctPairs == totalPairs) Color.Green else Color.Red)
      //  Spacer(modifier = Modifier.height(16.dp))
        Text("You found $correctPairs out of $totalPairs pairs!", style = MaterialTheme.typography.bodyLarge, color = LightText)
        if (correctPairs == totalPairs) { // ✅ Show time only if the player wins
            Text("Your time is $elapsedTime seconds!", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.bodyLarge,   color = YellowAccent)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                quizViewModel.resetGame()
                navController.popBackStack("word_association", inclusive = true) // ✅ Clear backstack
                navController.navigate("word_association")
            },
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = PinkPrimary,
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .padding(horizontal = 16.dp)
        ) {
            Text("\uD83C\uDFAE Play Again")
        }
    }
}
