package com.elvr.quizybara


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.elvr.quizybara.screens.*
import com.elvr.quizybara.viewmodels.QuizViewModel

object Routes {
    const val Start = "start"
    const val ChooseGame = "choose_game"
   // const val Difficulty = "difficulty"
    const val Quiz = "quiz"
    const val Results = "results"
    const val FlagQuiz = "flag_quiz"
    const val LogoQuiz = "logo_quiz"
    const val WordAssociation = "word_association"
    const val ResultsPairWords = "results_pair_words"
    const val GameRules = "game_rules"


}

@Composable
fun Navigation(navController: NavHostController, quizViewModel: QuizViewModel) {
    NavHost(navController = navController, startDestination = Routes.Start) {
        composable(Routes.Start) { StartScreen(navController, quizViewModel) }
        composable(Routes.ChooseGame) { ChooseGameScreen(navController, quizViewModel) }
     //   composable(Routes.Difficulty) { DifficultyScreen(navController, quizViewModel) }
        composable(Routes.Quiz) { QuizScreen(navController, quizViewModel) }
        composable(Routes.Results) { ResultsScreen(navController, quizViewModel) }
        composable(Routes.FlagQuiz) { FlagQuizScreen(navController, quizViewModel) }
        composable(Routes.LogoQuiz) { LogoQuizScreen(navController, quizViewModel) }
        composable(Routes.WordAssociation) { WordAssociationScreen(navController, quizViewModel) }
    composable(Routes.ResultsPairWords) { ResultsPairWordScreen(navController, quizViewModel) }
        composable(Routes.GameRules) { GameRulesScreen(navController) }


    }
}
