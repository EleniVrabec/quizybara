package com.elvr.quizybara.model

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector

// Data class to store each category's properties
data class CategoryItem(
    val name: String,
    val id: Int,
    val icon: ImageVector,
    val gradient: Brush,
    //val color: Color,
    val isLocked: Boolean = false
)
