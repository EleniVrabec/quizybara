package com.elvr.quizybara.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.ColorLens
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.History // Now available
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.Science
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Memory
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.QuestionAnswer
import androidx.compose.material.icons.filled.SportsSoccer
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.elvr.quizybara.components.CategoryCard
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.elvr.quizybara.components.PointsHeaderAnimated
import com.elvr.quizybara.components.UnlockedDialog
import com.elvr.quizybara.model.CategoryItem
import com.elvr.quizybara.ui.theme.GeographyGradient
import com.elvr.quizybara.ui.theme.HistoryGradient
import com.elvr.quizybara.ui.theme.*
import com.elvr.quizybara.viewmodels.QuizViewModel

@Composable
fun ChooseGameScreen(navController: NavController, quizViewModel: QuizViewModel) {
    val justUnlocked by quizViewModel.justUnlocked.collectAsState()
    var showUnlockDialog by remember { mutableStateOf(false) }

    LaunchedEffect(justUnlocked) {
        if (justUnlocked.isNotEmpty()) {
            showUnlockDialog = true
        }
    }

    val context = LocalContext.current
    val unlockedCategories by quizViewModel.unlockedCategories.collectAsState()
    val categories = listOf(
        CategoryItem("Geography", 22,  Icons.Default.Public, GeographyGradient),
        CategoryItem("General Knowledge",9, Icons.Default.Lightbulb, LogoGradient),
        CategoryItem("History",23, Icons.Default.AutoStories, HistoryGradient),
        CategoryItem("Animals", 27, Icons.Default.Pets, AnimalsGradient),
        CategoryItem("Science", 17,  Icons.Default.Science, ScienceGradient),
        CategoryItem("Art",25, Icons.Default.ColorLens, ArtGradient),
        CategoryItem("Sport",21, Icons.Default.SportsSoccer, SportGradient),
        CategoryItem("Vehicles",28, Icons.Default.DirectionsCar, VehiclesGradient),
        CategoryItem("Mathematics",19, Icons.Default.Calculate, MathematicsGradient),
        CategoryItem("Music",12, Icons.Default.MusicNote, MusicGradient),
        CategoryItem("Computers",18, Icons.Default.Memory, ComputersGradient),
        CategoryItem("Mythology",20, Icons.Default.AutoAwesome, MythologyGradient),
        CategoryItem("Word Match", 500, Icons.Default.QuestionAnswer, WordAssociationGradient),
        CategoryItem("Flags Quiz", 999, Icons.Default.Flag, GuessFlagGradient),
        CategoryItem("Logos Quiz", 1999, Icons.Default.Business,GuessLogoGradient)
    ).map {
        it.copy(isLocked = !unlockedCategories.contains(it.id)) // ✅ Mark locked
    }
    var expanded by remember { mutableStateOf(false) }
    val totalPoints by quizViewModel.totalPoints.collectAsState()

    Box(modifier = Modifier
        .background(DarkBackground)
        .fillMaxSize()
        .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column( //modifier = Modifier
           // .fillMaxSize()
            //.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
        ) {
           // Spacer(modifier = Modifier.height(26.dp))
            Text(text = "✨ Choose Your Challenge ✨",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = YellowAccent,)
            // 🟩 2. Add your animated points component here
          //  PointsHeaderAnimated(targetPoints = totalPoints)

            Spacer(modifier = Modifier.height(16.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(3), // Display 2 cards per row
                modifier = Modifier
                   // .weight(1f)
                    .fillMaxWidth()
                    .heightIn(min = 400.dp),

                contentPadding = PaddingValues(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
              //  modifier = Modifier.fillMaxSize()
            ) {
              //  val displayedCategories = if (expanded) categories else categories.take(4)
             //   items(displayedCategories) { category ->
                items(categories) { category ->
                    Box(
                        modifier = Modifier
                           // .fillMaxWidth()
                            .aspectRatio(1f) // Keeps the card square-shaped (important for grids!)
                    ) {
                    CategoryCard(
                        icon = category.icon,  // Each category has its own icon
                        text = category.name,
                        backgroundGradient = category.gradient,
                        onClick = {
                            if (category.isLocked) {
                                // Optional: show a Toast or Snackbar here
                                println("🔒 Category locked!")
                                return@CategoryCard
                            }
                            when (category.name) {
                                "Flags Quiz" -> {
                                    quizViewModel.fetchLocalFlagQuestions(context)
                                    navController.navigate("flag_quiz")
                                }
                                "Logos Quiz" -> {
                                    quizViewModel.fetchLocalLogoQuestions(context)
                                    navController.navigate("logo_quiz")
                                }
                                "Word Match" -> { // 👈 NEW NAVIGATION
                                    quizViewModel.loadWordAssociations(context)
                                    navController.navigate("word_association")
                                }
                                else -> {
                                    quizViewModel.setCategory(category.id)
                                    quizViewModel.fetchQuestions()
                                    navController.navigate("quiz")
                                   // navController.navigate("difficulty")

                                }

                            }
                        },
                        isLocked = category.isLocked,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                             //   modifier = Modifier.fillMaxSize()
                    )//categorycard
                        // 🔒 Overlay for locked categories
                      /*  if (category.isLocked) {
                            Box(
                                modifier = Modifier
                                    .matchParentSize()
                                    .background(Color.Black.copy(alpha = 0.4f)), // dim effect
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Lock,
                                    contentDescription = "Locked",
                                    tint = Color.White
                                )
                            }
                        }*/
                    }
                }
            }
            // Spacer(modifier = Modifier.height(16.dp))
         /*   Button(
                onClick = { expanded = !expanded },
                modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth()
            ) {
                Text(if (expanded) "Show Less" else "Show More")
            }*/
        }//column
        if (showUnlockDialog) {
            UnlockedDialog(
                justUnlocked = justUnlocked,
                onDismiss = {
                    showUnlockDialog = false
                    quizViewModel.clearJustUnlocked()  },
                getCategoryNameById = { id -> quizViewModel.getCategoryNameById(id) }
            )
        }//if unlock popup window
    }//box
}

// Data class to store each category's properties
/*data class CategoryItem(
    val name: String,
    val id: Int,
    val icon: ImageVector,
    val gradient: Brush,
    //val color: Color,
    val isLocked: Boolean = false
    )*/

@Preview(showBackground = true)
@Composable
fun ChooseGameScreenPreview() {
   // ChooseGameScreen(navController = NavController(LocalContext.current) , quizViewModel = QuizViewModel())
}
