package com.elvr.quizybara.components

import androidx.compose.material3.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/*
@Composable
fun QuestionReviewCard(
    questionNumber: Int,
    questionText: String,
    userAnswer: String,
    correctAnswer: String,
    isCorrect: Boolean
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Question $questionNumber",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = questionText, style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Text(
                    text = "Your answer: ",
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = userAnswer,
                    color = if (isCorrect) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            if (!isCorrect) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Text(
                        text = "Correct answer: ",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Text(
                        text = correctAnswer,
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}
*/


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.elvr.quizybara.ui.theme.PinkPrimary
import com.elvr.quizybara.ui.theme.YellowAccent
import com.elvr.quizybara.ui.theme.BlueAccent

@Composable
fun QuestionReviewCard(
    questionNumber: Int,
    questionText: String,
    userAnswer: String,
    correctAnswer: String,
    isCorrect: Boolean
) {
    val backgroundColor = if (isCorrect) Color(0xFFE6FFED) else Color(0xFFFFEBEB)
    val answerColor = if (isCorrect) BlueAccent else PinkPrimary
    val icon = if (isCorrect) "✅" else "❌"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "$icon Question $questionNumber",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                color = YellowAccent
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = questionText,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Your answer: $userAnswer",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                color = answerColor
            )

            if (!isCorrect) {
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Correct answer: $correctAnswer",
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                    color = BlueAccent
                )
            }
        }
    }
}
