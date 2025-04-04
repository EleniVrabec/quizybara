package com.elvr.quizybara.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.elvr.quizybara.viewmodels.QuizViewModel
import com.elvr.quizybara.components.CountdownTimer
import com.elvr.quizybara.components.WinPopup
import com.elvr.quizybara.components.LosePopup
import com.elvr.quizybara.components.PointsBadge
import com.elvr.quizybara.components.WordCard
import com.elvr.quizybara.ui.theme.ComputersGradient
import com.elvr.quizybara.ui.theme.LightPink
import com.elvr.quizybara.ui.theme.PinkPrimary
import kotlinx.coroutines.delay


@Composable
fun WordAssociationScreen(navController: NavController, quizViewModel: QuizViewModel) {
    val context = LocalContext.current
    val words = quizViewModel.wordAssociationQuestions.collectAsState().value
    var selectedWords by remember { mutableStateOf(emptyList<String>()) }
    var correctPairs by remember { mutableStateOf(mutableSetOf<String>()) }
    var gameStarted by remember { mutableStateOf(false) }
    var timeLeft by remember { mutableStateOf(30) }
    var gameOver by remember { mutableStateOf(false) }
    var winMessage by remember { mutableStateOf("") }
    var showWinPopup by remember { mutableStateOf(false) }
    var showLosePopup by remember { mutableStateOf(false) }
    var clearSelectionTrigger by remember { mutableStateOf(false) }
    var glowingWords by remember { mutableStateOf<List<String>>(emptyList()) }
    var glowTrigger by remember { mutableStateOf(false) }
    var wrongWords by remember { mutableStateOf<List<String>>(emptyList()) }

    // ✅ Start the game and timer
    LaunchedEffect(gameStarted) {
        if (gameStarted) {
            quizViewModel.loadWordAssociations(context)
            correctPairs.clear()
            gameOver = false // ✅ Reset game state
            showWinPopup = false
            showLosePopup = false
        }
    }
Box(modifier = Modifier.fillMaxSize()   .background(ComputersGradient)){
    Spacer(modifier = Modifier.height(16.dp))
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        Text("🧠",
            style = MaterialTheme.typography.headlineLarge,
            color = LightPink, // Pink
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .wrapContentSize(Alignment.Center)

        )
        Text("Word Association Game",
            style = MaterialTheme.typography.headlineLarge,
            color = LightPink, // Pink
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(ComputersGradient)
                .padding(12.dp)
                .wrapContentSize(Alignment.Center)

        )

        if (!gameStarted) {
            Button(
                onClick = { gameStarted = true },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    //.background(PinkPrimary)
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = PinkPrimary,
                    contentColor = Color.White // text color
                )

            ) {
                Text("🚀 Start Game")
            }
        } else {
            Text(
                text = "⏱ Time Left: $timeLeft sec",
                style = MaterialTheme.typography.titleLarge,
                color = if (timeLeft <= 10) Color.Red else Color(0xFF29B6F6), // Light blue
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
                    .wrapContentSize(Alignment.Center)

            )
            Spacer(modifier = Modifier.height(16.dp))

            // ✅ Start Countdown Timer
            CountdownTimer(
                totalTime = 30,
                onTimeUp = {
                    if (!gameOver) { // ✅ Only trigger if game isn't already over
                        gameOver = true
                        showLosePopup = true
                    }
                },
                onTick = { timeLeft = it }
            )


            if (!gameOver) {
                LaunchedEffect(clearSelectionTrigger) {
                    if (clearSelectionTrigger) {
                        delay(600)
                        selectedWords = emptyList()
                        wrongWords = emptyList()
                        clearSelectionTrigger = false
                    }
                }
                LaunchedEffect(glowTrigger) {
                    if (glowTrigger) {
                        delay(500)
                        glowingWords = emptyList()
                        selectedWords = emptyList()
                        glowTrigger = false
                    }
                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),

                            modifier = Modifier
                            .fillMaxHeight()
                        .weight(1f),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(words) { word ->
                        val isSelected = selectedWords.contains(word)
                        val isCorrect = correctPairs.contains(word)

                        // ✅ Hide matched words
                        if (!isCorrect) {
                            WordCard(
                                word = word,
                                isSelected = isSelected,
                                isMatched = isCorrect,
                                isGlowing = glowingWords.contains(word),
                                isWrong = wrongWords.contains(word),
                                onClick = {
                                    if (isCorrect) return@WordCard
                                    selectedWords = selectedWords + word
                                    if (selectedWords.size == 2) {
                                        val (w1, w2) = selectedWords
                                        val matched = quizViewModel.isCorrectPair(w1, w2)
                                        if (matched) {
                                            correctPairs.addAll(selectedWords)
                                            glowingWords = selectedWords
                                            glowTrigger = true // ✅ trigger glow


                                            if (correctPairs.size == words.size) {
                                                quizViewModel.setElapsedTime(30, timeLeft)
                                                quizViewModel.addPoints(50)
                                                gameOver = true
                                                showWinPopup = true
                                            }
                                        }else{
                                            // Add delay to let the user see the selected state before clearing
                                            wrongWords = selectedWords
                                            clearSelectionTrigger = true
                                        }


                                    }
                                }
                            )

                            /*
                            Button(
                                onClick = {
                                    if (isCorrect) return@Button
                                    selectedWords = selectedWords + word
                                    if (selectedWords.size == 2) {
                                        if (quizViewModel.isCorrectPair(
                                                selectedWords[0],
                                                selectedWords[1]
                                            )
                                        ) {
                                            correctPairs.addAll(selectedWords)
                                            // ✅ Check if all pairs are found
                                            if (correctPairs.size == words.size) {
                                                quizViewModel.setElapsedTime(30, timeLeft)
                                                quizViewModel.addPoints(50)
                                                gameOver = true
                                                showWinPopup = true
                                            }
                                        }
                                        selectedWords = emptyList()
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = when {
                                        isCorrect -> Color.Transparent // ✅ Disappear effect
                                        isSelected -> Color.Yellow
                                        else -> MaterialTheme.colorScheme.primary
                                    }
                                )
                            ) {
                                Text(
                                    word,
                                    color = if (isCorrect) Color.Transparent else Color.White
                                )
                            }//btn
                            */
                        }
                    }
                }
            }
        }
    }//column
    // 🎯 Top-right badge overlay
    Box(
        modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(top = 16.dp, end = 16.dp)
    ) {
        val points by quizViewModel.totalPoints.collectAsState()
        PointsBadge(points)
    }
}//box
    // ✅ Show "You Win" Popup
    if (showWinPopup) {
        WinPopup {
            showWinPopup = false
            navController.navigate("results_pair_words")
        }
    }

    // ✅ Show "You Lose" Popup
    if (showLosePopup) {
        LosePopup {
            showLosePopup = false
            navController.navigate("results_pair_words")
        }
    }
}


