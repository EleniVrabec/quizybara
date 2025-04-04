package com.elvr.quizybara.ui.theme

// File: CategoryGradients.kt



import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val GeographyGradient = Brush.linearGradient(
    colors = listOf(Color(0xFF139DF2), Color(0xFF87CEFA)) // Blue to Light Blue
)

val HistoryGradient = Brush.linearGradient(
    colors = listOf(Color(0xFFF2075D), Color(0xFFF24607)) // Pink to Orange
)
val AnimalsGradient = Brush.linearGradient(
    colors = listOf(
        Color(0xFF4CAF50), // Green - nature, jungle
        Color(0xFFFF9800)  // Orange - fur, playful tone
    )
)


val ScienceGradient = Brush.linearGradient(
    colors = listOf(Color(0xFF4CAF50), Color(0xFF8BC34A)) // Green to Light Green
)

val ArtGradient = Brush.linearGradient(
    colors = listOf(Color(0xFFF2D750), Color(0xFFFFEB3B)) // Yellow tones
)

val LogoGradient = Brush.linearGradient(
    colors = listOf(Color(0xFF7B1FA2), Color(0xFFE91E63)) // Purple to Pink
)

val SportGradient = Brush.linearGradient(
    colors = listOf(Color(0xFF00BCD4), Color(0xFF03A9F4)) // Cyan to Blue
)

val VehiclesGradient = Brush.linearGradient(
    colors = listOf(Color(0xFF9E9E9E), Color(0xFFBDBDBD)) // Grey tones
)

val MathematicsGradient = Brush.linearGradient(
    colors = listOf(Color(0xFF3F51B5), Color(0xFF2196F3)) // Indigo to Blue
)

val MusicGradient = Brush.linearGradient(
    colors = listOf(Color(0xFF880E4F), Color(0xFFD81B60)) // Deep pink to rose
)

val ComputersGradient = Brush.linearGradient(
    colors = listOf(Color(0xFF1A237E), Color(0xFF3F51B5)) // Deep blue shades
)

val MythologyGradient = Brush.linearGradient(
    colors = listOf(Color(0xFF6A1B9A), Color(0xFFAB47BC)) // Dark to light purple
)

val WordAssociationGradient = Brush.linearGradient(
    colors = listOf(Color(0xFF00E676), Color(0xFF1DE9B6)) // Bright green to teal
)

val GuessFlagGradient = Brush.linearGradient(
    colors = listOf(Color(0xFFFF6F00), Color(0xFFFF8F00)) // Orange tones
)

val GuessLogoGradient = Brush.linearGradient(
    colors = listOf(Color(0xFF2962FF), Color(0xFF448AFF)) // Strong to soft blue
)

val PinkToOrange = Brush.linearGradient(
    colors = listOf(PinkPrimary, OrangeAccent)
)


val GradiantSemiTransparentBckg = Brush.linearGradient(
    colors = listOf(
        Color(0xFF5374E7).copy(alpha = 0.6f),
        Color(0xFFB350E7).copy(alpha = 0.4f),
        Color(0xFFE750B3).copy(alpha = 0.3f),
        Color.Transparent
    ))

val PinkBlushGradient = Brush.verticalGradient(
    colors = listOf(
        Color(0xFFFFF0F5), // LavenderBlush (very soft pinkish white)
        Color(0xFFFFC0CB), // Pastel pink
       // Color(0xFFF2075D).copy(alpha = 0.6f) // Your PinkPrimary, softly blended
    )
)
