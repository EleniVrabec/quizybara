package com.elvr.quizybara.ui.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

// Base Brand Colors
val PinkPrimary = Color(0xFFF2075D)
val BlueAccent = Color(0xFF139DF2)
val YellowAccent = Color(0xFFF2D750)
val OrangeAccent = Color(0xFFF24607)

val PinkPrimarySoft = PinkPrimary.copy(alpha = 0.8f)


// Text Colors
val DarkText = Color(0xFF1C1B1F)
val LightText = Color(0xFFFFE4F0)

val BlueBackground = Color(0xFF3FA0F5)
//val DarkBackground = Color(0xFF020E18)
val DarkBackground = Color(0xFF121624)

val LightBackground = Color(0xFFF2F2F2)


val LightYellow = Color(0xFFFEFDED)
//val ButtonDefaultColor = Color(0xFF6A5ACD)
val ButtonDefaultColor = Color(0xFFB350E7) // SlateBlue / Indigo-style
val ButtonCorrectColor = Color(0xFF1DE9B6) // MintGreenCorrectAnswer
val ButtonIncorrectColor = Color(0xFFF24607) // OrangeIncorrectAnswer

val LightPink = Color(0xFFFFB6C1)     // Light pink
val PastelPink = Color(0xFFFFC0CB)    // Classic pastel pink
val MutedPink = PinkPrimary.copy(alpha = 0.7f) // your pink, just calmer
val AlmostWhitePink = PinkPrimary.copy(alpha = 0.3f) // for soft glow accents

val LoaderPrimaryColor = BlueAccent
val LoaderSecondaryColor = PinkPrimarySoft

val ProgressBarColor = PinkPrimary
val ProgressBarTrack = Pink80

// Gradients (just color stops, apply with Brush elsewhere)
val Gradient1Start = PinkPrimary
val Gradient1End = BlueAccent

val Gradient2Start = YellowAccent
val Gradient2End = OrangeAccent