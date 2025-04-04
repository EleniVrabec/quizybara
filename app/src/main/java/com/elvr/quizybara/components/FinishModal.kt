package com.elvr.quizybara.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.airbnb.lottie.compose.*
import com.elvr.quizybara.R
import com.elvr.quizybara.ui.theme.BlueAccent
import com.elvr.quizybara.ui.theme.ComputersGradient
import com.elvr.quizybara.ui.theme.DarkBackground
import com.elvr.quizybara.ui.theme.PinkBlushGradient
import com.elvr.quizybara.ui.theme.PinkPrimary
import com.elvr.quizybara.ui.theme.YellowAccent

@Composable
fun FinishModal(
    totalPoints: Int,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
) {
    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(usePlatformDefaultWidth = false) // ✅ Ensures full-screen modal
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f)), // ✅ Full-screen black overlay
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .widthIn(max = 380.dp) // limit width
                    .clip(RoundedCornerShape(24.dp))
                    .background(ComputersGradient)
            ) {
                Card(
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier
                        .wrapContentHeight()
                       // .fillMaxWidth()
                       // .background(PinkBlushGradient)
                        .padding(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.Transparent)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        // ✅ Lottie Animation
                        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.success))
                        val progress by animateLottieCompositionAsState(
                            composition,
                            iterations = 1 // Play once
                        )

                        LottieAnimation(
                            composition = composition,
                            progress = { progress },
                            modifier = Modifier.size(400.dp) // Adjust size if needed
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // ✅ Congratulations Text
                        Text(
                            text = "🎉 Congratulations! 🎉",
                            style = MaterialTheme.typography.headlineMedium,
                            color = YellowAccent,
                            textAlign = TextAlign.Center

                        )

                        Text(
                            text = "You have successfully completed the quiz!",
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                        Text(
                            text = "💰 You've earned $totalPoints points so far!",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Medium,
                            color = PinkPrimary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 4.dp)
                        )


                        Spacer(modifier = Modifier.height(16.dp))

                        // ✅ Buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Button(
                                onClick = { onDismissRequest() },
                                modifier = Modifier.padding(8.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = BlueAccent,
                                    contentColor = Color.White
                                )
                            ) {
                                Text("Close")
                            }
                            TextButton(
                                onClick = { onConfirmation() },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = PinkPrimary,
                                    contentColor = Color.White
                                ),
                                modifier = Modifier.padding(8.dp)
                            ) {
                                Text("View Results")
                            }
                        }
                    }
                }//card
            }
        }
    }
}

@Preview
@Composable
fun PreviewFinishModal() {
    FinishModal(totalPoints = 100, onDismissRequest = {}, onConfirmation = {})
}
