package com.elvr.quizybara.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import coil.request.ImageRequest
import coil.decode.SvgDecoder
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.zIndex
import com.elvr.quizybara.ui.theme.GradiantSemiTransparentBckg
import com.elvr.quizybara.ui.theme.LightText

@Composable
fun QuestionCard(
    question: String,
    flagUrl: String?,
    answers: List<String>,
    questionType: String,
    correctAnswer: String,
    selectedAnswer: String?,
    onAnswerSelected: (String) -> Unit,
    onNextQuestion: () -> Unit,
    key: Int
) {
    val coroutineScope = rememberCoroutineScope()

    Card(
        modifier = Modifier.fillMaxWidth(),
      //  elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(
            modifier = Modifier
               // .background(Color.Gray)
              .background(GradiantSemiTransparentBckg)
                .padding(16.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp)),
                    horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = question,
                style = MaterialTheme.typography.headlineMedium,
                color = LightText,
                textAlign = TextAlign.Center, // 🆕 centers multiline text content
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            // ✅ Load SVG flag images correctly
            flagUrl?.let {
                Log.d("LOGO_QUIZ", "Loading image: $flagUrl")
                val context = LocalContext.current
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(flagUrl)
                        .addHeader("User-Agent", "Mozilla/5.0") // ← THIS FIXES FLAGCDN ISSUES
                        .crossfade(true)
                        .build(),
                    contentDescription = "Image",
                    modifier = Modifier.size(200.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .zIndex(100F)
                        .padding(8.dp),
                            alignment = Alignment.Center,

                )
            } ?: Text("")
            Spacer(modifier = Modifier.height(16.dp))
            AnswerButtons(
                answers = answers,
                selectedAnswer = selectedAnswer,
                correctAnswer = correctAnswer,
                questionType = questionType,
                onAnswerSelected = { answer ->
                    // Call the parent's onAnswerSelected to update state
                    onAnswerSelected(answer)

                    // Schedule navigation to next question after a delay
                    coroutineScope.launch {
                        // Give more time to see the result (adjust as needed)
                        delay(2000)
                        onNextQuestion()
                    }
                }
            )
        }
    }
}