package com.elvr.quizybara.components



import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import nl.dionsegijn.konfetti.compose.KonfettiView
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.compose.OnParticleSystemUpdateListener
import nl.dionsegijn.konfetti.core.PartySystem

@Composable
fun ConfettiOverlay(
    show: Boolean,
    parties: List<Party>,
    onComplete: () -> Unit
) {
    if (show) {
        KonfettiView(
            modifier = Modifier.fillMaxSize(),
            parties = parties,
            updateListener = object : OnParticleSystemUpdateListener {
                override fun onParticleSystemEnded(system: PartySystem, activeSystems: Int) {
                    if (activeSystems == 0) onComplete()
                }
            }
        )
    }
}
