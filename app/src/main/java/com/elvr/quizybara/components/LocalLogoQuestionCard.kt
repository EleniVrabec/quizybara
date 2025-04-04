package com.elvr.quizybara.components


import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.elvr.quizybara.ui.theme.GradiantSemiTransparentBckg
import com.elvr.quizybara.ui.theme.LightText
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LocalLogoQuestionCard(
    question: String,
    logoFileName: String, // like "nike.svg"
    answers: List<String>,
    correctAnswer: String,
    selectedAnswer: String?,
    onAnswerSelected: (String) -> Unit,
    onNextQuestion: () -> Unit,
    key: Int
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    //val logoAssetPath = "file:///android_asset/logos/behance.svg"
   // val flagAssetPath = "file:///android_asset/flags/$flagFileName"
    val logoAssetPath = "file:///android_asset/logos/$logoFileName"
    Log.d("LOCAL_LOGO", "🔍 Loading logo from: $logoAssetPath")
    Log.d("LOCAL_LOGO", "Loading logo from $logoAssetPath")

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column(
            modifier = Modifier
                .background(GradiantSemiTransparentBckg)
                .padding(16.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = question,
                style = MaterialTheme.typography.headlineMedium,
                color = LightText,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            // ✅ Show logo from local asset
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(logoAssetPath)
                    .decoderFactory(SvgDecoder.Factory())
                    .build(),
                contentDescription = "Logo Image",
                modifier = Modifier
                    .size(200.dp)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .zIndex(1f),
                alignment = Alignment.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            AnswerButtons(
                answers = answers,
                selectedAnswer = selectedAnswer,
                correctAnswer = correctAnswer,
                questionType = "multiple",
                onAnswerSelected = { answer ->
                    onAnswerSelected(answer)
                    coroutineScope.launch {
                        delay(2000)
                        onNextQuestion()
                    }
                }
            )
        }
    }
}
