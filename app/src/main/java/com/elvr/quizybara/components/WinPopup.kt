package com.elvr.quizybara.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.tooling.preview.Preview
import com.elvr.quizybara.ui.theme.BlueAccent
import com.elvr.quizybara.ui.theme.DarkBackground
import com.elvr.quizybara.ui.theme.PinkPrimary
import com.elvr.quizybara.ui.theme.YellowAccent

@Composable
fun WinPopup(onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(DarkBackground, shape = RoundedCornerShape(24.dp))
                  /*  .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(BlueAccent, PinkPrimary)
                        ),
                        shape = RoundedCornerShape(24.dp)
                    )*/
                    .padding(24.dp)
                    .shadow(12.dp, RoundedCornerShape(24.dp)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "🎉 Congratulations!",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = YellowAccent,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "You won the game! Well done!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PinkPrimary,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .height(48.dp)
                        .defaultMinSize(minWidth = 150.dp)
                ) {
                    Text("🎮 Play Again")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WinPopupPreview() {
    WinPopup(onDismiss = {})
}
