package com.elvr.quizybara.components



import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.elvr.quizybara.utils.Presets
import nl.dionsegijn.konfetti.compose.KonfettiView
import nl.dionsegijn.konfetti.core.Party

@Composable
fun BalloonConfetti(
    show: Boolean,
    parties: List<Party>,
    onComplete: () -> Unit
) {
    if (show) {
        Box(modifier = Modifier.fillMaxSize()) {
            KonfettiView(
                parties = parties,
                modifier = Modifier.fillMaxSize(),


            )
        }
    }
}
