package com.elvr.quizybara.components

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.elvr.quizybara.R
import com.elvr.quizybara.ui.theme.ButtonCorrectColor
import com.elvr.quizybara.ui.theme.ButtonDefaultColor
import com.elvr.quizybara.ui.theme.ButtonIncorrectColor


@Composable
fun AnswerButtons(
    answers: List<String>,
    questionType: String,
    selectedAnswer: String?,  // Track selected answer
    correctAnswer: String,    // Compare with correct answer
    onAnswerSelected: (String) -> Unit
) {
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxWidth()) {
        val options = if (questionType == "boolean") {
            listOf("True", "False")
        } else {
            answers
        }

        options.forEach { answer ->
            // Determine background color based on selection state
            val backgroundColor = when {
                // No selection yet - default color
                selectedAnswer == null -> ButtonDefaultColor

                // This answer was selected and is correct
                selectedAnswer == answer && answer == correctAnswer -> ButtonCorrectColor

                // This answer was selected and is incorrect
                selectedAnswer == answer -> ButtonIncorrectColor


                // Another answer was selected, and this one is the correct one
                answer == correctAnswer -> ButtonCorrectColor.copy(alpha = 0.5f)

                // Another answer was selected (not this one)
                else -> ButtonDefaultColor
            }

            Button(
                onClick = {
                    // Only trigger if no answer has been selected yet
                    if (selectedAnswer == null) {
                        onAnswerSelected(answer)
                        playSound(context, answer == correctAnswer)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = backgroundColor)
                // Removed the 'enabled' property to keep buttons visually enabled
            ) {
                Text(answer)
            }
        }
    }
}

private fun playSound(context: Context, isCorrect: Boolean) {
    val soundResId = if (isCorrect) R.raw.correct else R.raw.wrong
    val mediaPlayer = MediaPlayer.create(context, soundResId)
    mediaPlayer?.let {
        it.start()
        it.setOnCompletionListener { mp ->
            mp.release()  // Properly release MediaPlayer
        }
    }
}
