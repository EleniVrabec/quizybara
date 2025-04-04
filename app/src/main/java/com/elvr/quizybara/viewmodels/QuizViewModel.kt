package com.elvr.quizybara.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.elvr.quizybara.model.Question
import com.elvr.quizybara.repository.QuizRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.elvr.quizybara.repository.FlagRepository
import com.elvr.quizybara.repository.LogoRepository
import android.os.Handler
import android.os.Looper
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.elvr.quizybara.R
import com.elvr.quizybara.utils.DataStoreManager
import com.google.gson.Gson
import kotlinx.coroutines.delay
import java.io.InputStreamReader

// Category ID to Points Required
/*
val categoryUnlockThresholds = mapOf(
    22 to 0,     // Geography (unlocked by default)
    9 to 0,     // General Knowledge (unlocked by default)
    23 to 0,     // History (unlocked by default)
    27 to 20,   // Animals unlocks at 100 points
    17 to 150,    // Science at 150
    25 to 200,   // Art
    21 to 250,   // Sport
    28 to 300,   // Vehicles
    19 to 350,   // Mathematics
    12 to 400,   // Music
    18 to 450,   // Computers
    20 to 500,   // Mythology
    500 to 600,  // Word Association
    999 to 700,  // Guess the Flag
    1999 to 800  // Guess the Logo
)*/
// For testing purposes:
val categoryUnlockThresholds = mapOf(
    22 to 0,     // Geography (unlocked by default)
    9 to 0,     // General Knowledge (unlocked by default)
    23 to 0,     // History (unlocked by default)
    27 to 0,   // Animals unlocks at 100 points
    17 to 70,    // Science at 150
    25 to 30,   // Art
    21 to 100,   // Sport
    28 to 1000,   // Vehicles
    19 to 1000,   // Mathematics
    12 to 100,   // Music
    18 to 1000,   // Computers
    20 to 30,   // Mythology
    500 to 10,  // Word Association
    999 to 10,  // Guess the Flag
    1999 to 1000  // Guess the Logo
)

enum class QuizMode {
    TRIVIA, FLAG,  LOGO
}
// Data classes for JSON parsing
data class WordPair(val word1: String, val word2: String)
data class WordAssociationData(val word_associations: List<WordPair>)

class QuizViewModel(
    private val dataStore: DataStoreManager,
    private val repository: QuizRepository = QuizRepository(),
    private val flagRepository: FlagRepository = FlagRepository(),
    private val logoRepository: LogoRepository = LogoRepository()
) : ViewModel() { // ✅ Provide default instance
    init {
        viewModelScope.launch {
            dataStore.totalPoints.collect { savedPoints ->
                _totalPoints.value = savedPoints
            }
        }

        viewModelScope.launch {
            dataStore.unlockedCategories.collect { savedUnlocked ->
                _unlockedCategories.value = savedUnlocked
            }
        }
        viewModelScope.launch {
            dataStore.nickname.collect { savedName ->
                _nickname.value = savedName
            }
        }
        viewModelScope.launch {
            dataStore.isFirstTime.collect { value ->
                firstTimeUser = value
            }
        }
    }

    private val _quizMode = MutableStateFlow(QuizMode.TRIVIA) // ✅ Default to Trivia
    val quizMode: StateFlow<QuizMode> = _quizMode


    private val _category = MutableStateFlow(0)
    val category: StateFlow<Int> = _category

    private val _difficulty = MutableStateFlow("easy")
    val difficulty: StateFlow<String> = _difficulty

    private val _questions = MutableStateFlow<List<Question>>(emptyList())
    val questions: StateFlow<List<Question>> = _questions

    private val _flagQuestions = MutableStateFlow<List<Question>>(emptyList())
    val flagQuestions = _flagQuestions.asStateFlow()

    private val _logoQuestions = MutableStateFlow<List<Question>>(emptyList()) // ✅ Add logo quiz questions
    val logoQuestions = _logoQuestions.asStateFlow()

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex

    // New state flow to track user answers
    private val _userAnswers = MutableStateFlow<List<String>>(emptyList())
    val userAnswers: StateFlow<List<String>> = _userAnswers

    private val _wordAssociationQuestions = MutableStateFlow<List<String>>(emptyList())
    val wordAssociationQuestions: StateFlow<List<String>> = _wordAssociationQuestions

    private lateinit var allWordPairs: List<WordPair>

    private val _correctPairsCount = MutableStateFlow(0)
    val correctPairsCount = _correctPairsCount.asStateFlow()

    private val _totalPairsCount = MutableStateFlow(0)
    val totalPairsCount = _totalPairsCount.asStateFlow()

    private val _elapsedTime = MutableStateFlow(0) // ✅ Store time taken to finish
    val elapsedTime = _elapsedTime.asStateFlow()

    private val _totalPoints = MutableStateFlow(0)
    val totalPoints: StateFlow<Int> = _totalPoints
    private val _unlockedCategories = MutableStateFlow<Set<Int>>(setOf(22, 9, 23)) // default unlocked
    val unlockedCategories: StateFlow<Set<Int>> = _unlockedCategories
    private val _justUnlocked = MutableStateFlow<List<Int>>(emptyList())
    val justUnlocked: StateFlow<List<Int>> = _justUnlocked
    private val _lastEarnedPoints = MutableStateFlow<Int?>(null)
    val lastEarnedPoints: StateFlow<Int?> = _lastEarnedPoints

    private var firstTimeUser = true

    private val _correctInARow = MutableStateFlow(0)
    val correctInARow: StateFlow<Int> = _correctInARow

    private val _showStreakBonusPopup = MutableStateFlow(false)
    val showStreakBonusPopup: StateFlow<Boolean> = _showStreakBonusPopup
    private val _hasTriggeredStreakBonus = MutableStateFlow(false)
    val hasTriggeredStreakBonus: StateFlow<Boolean> = _hasTriggeredStreakBonus

    fun resetStreakFlag() {
        _hasTriggeredStreakBonus.value = false
    }
    val _hasTriggeredEndBonus = MutableStateFlow(false)
    val hasTriggeredEndBonus: StateFlow<Boolean> = _hasTriggeredEndBonus



    fun resetEndBonusFlag() {
        _hasTriggeredEndBonus.value = false
    }

    fun triggerStreakBonusPopup() {
        _showStreakBonusPopup.value = true
    }

    fun resetStreakBonusPopup() {
        _showStreakBonusPopup.value = false
    }
    private val _showEndBonusPopup = MutableStateFlow(false)
    val showEndBonusPopup = _showEndBonusPopup.asStateFlow()

    fun triggerEndBonusPopup() {
        _showEndBonusPopup.value = true
    }
    fun resetEndBonusPopup() {
        _showEndBonusPopup.value = false
    }


    private val _nickname = MutableStateFlow<String?>(null)
    val nickname: StateFlow<String?> = _nickname
    fun saveNickname(name: String) {
        viewModelScope.launch {
            dataStore.saveNickname(name)
           // dataStore.setFirstTime(false)
            _nickname.value = name
        }
    }
    //Add Unlock Logic
    fun checkUnlocks() {
        val previouslyUnlocked = _unlockedCategories.value
        val newlyUnlocked = categoryUnlockThresholds.filter { (id, requiredPoints) ->
            _totalPoints.value >= requiredPoints && !previouslyUnlocked.contains(id)
        }.keys

        if (newlyUnlocked.isNotEmpty()) {
            _justUnlocked.value = newlyUnlocked.toList()
            _unlockedCategories.value = previouslyUnlocked + newlyUnlocked

            viewModelScope.launch {
                dataStore.saveUnlockedCategories(_unlockedCategories.value)
            }
        }

        _unlockedCategories.value = previouslyUnlocked + newlyUnlocked
    }


    fun addPoints(points: Int) {
        _totalPoints.value += points
        viewModelScope.launch {
            dataStore.saveTotalPoints(_totalPoints.value)
            checkUnlocks() // ✅ auto check unlocks after saving points
        }
    }

    fun clearJustUnlocked() {
        _justUnlocked.value = emptyList()
    }

    fun setQuizMode(mode: QuizMode) {
        _quizMode.value = mode
        _currentQuestionIndex.value = 0
        _correctAnswers.value = 0
        _userAnswers.value = emptyList()
        println("🎮 Quiz Mode Set: ${mode.name}")
    }


    fun setCategory(categoryId: Int) {
        _category.value = categoryId
        println("✅ Category set to: $categoryId")
    }

    fun setDifficulty(selectedDifficulty: String) {
        _difficulty.value = selectedDifficulty
        println("✅ Difficulty set to: $selectedDifficulty")
    }
/*
    fun fetchQuestions() {
        viewModelScope.launch {
            setQuizMode(QuizMode.TRIVIA)
            println("🚀 Fetching questions for category ${_category.value} and difficulty ${_difficulty.value}")
            try {
                val fetchedQuestions = repository.getQuestions(10, _category.value, _difficulty.value)
                _questions.value = fetchedQuestions

                // Reset user answers when new questions are fetched
                _userAnswers.value = emptyList()

                fetchedQuestions.forEachIndexed { index, question ->
                    println("Question ${index + 1}: ${question.questionText}")
                }
            } catch (e: Exception) {
                println("Error fetching questions: ${e.message}")
            }
        }
    }

 */
fun fetchQuestions() {
    viewModelScope.launch {
        setQuizMode(QuizMode.TRIVIA)
        println("🚀 Fetching questions for category ${_category.value}")

        try {
            val fetchedQuestions = repository.getQuestions(10, _category.value) // ✅ Make sure this correctly passes category

            if (fetchedQuestions.isNotEmpty()) {
                _questions.value = fetchedQuestions
                _userAnswers.value = emptyList() // ✅ Reset user answers when new questions are fetched
                println("✅ Successfully fetched ${fetchedQuestions.size} questions!")

                // Debug: Print fetched questions
                fetchedQuestions.forEachIndexed { index, question ->
                    println("Question ${index + 1}: ${question.questionText}")
                }
            } else {
                println("❌ No questions found for category ${_category.value}!")
            }
        } catch (e: Exception) {
            println("❌ Error fetching questions: ${e.message}")
        }
    }
}




    private val _correctAnswers = MutableStateFlow(0)
    val correctAnswers: StateFlow<Int> = _correctAnswers

    fun submitAnswer(selectedAnswer: String) {
        val isTriviaMode = _quizMode.value == QuizMode.TRIVIA
        val questionsList = when (_quizMode.value) {
            QuizMode.TRIVIA -> _questions.value
            QuizMode.FLAG -> _flagQuestions.value
            QuizMode.LOGO -> _logoQuestions.value
        }

        if (_currentQuestionIndex.value < questionsList.size) {
            val currentQuestion = questionsList[_currentQuestionIndex.value]

            // ✅ Store the user's answer
            val updatedAnswers = _userAnswers.value.toMutableList()

            // Ensure the list size is correct
            while (updatedAnswers.size <= _currentQuestionIndex.value) {
                updatedAnswers.add("")
            }

            // Set the user's selected answer
            updatedAnswers[_currentQuestionIndex.value] = selectedAnswer
            _userAnswers.value = updatedAnswers

            // ✅ Check if the answer is correct
            val isCorrect = selectedAnswer == currentQuestion.correctAnswer
            if (isCorrect) {
                _correctAnswers.value += 1
                _correctInARow.value += 1

                // 💥 Bonus: 5 correct in a row
                if (_correctInARow.value >= 5 && !_hasTriggeredStreakBonus.value) {
                    addBonusPoints(30)

                    triggerStreakBonusPopup()
                    _correctInARow.value = 0
                    _hasTriggeredStreakBonus.value = true

                    println("🔥 BONUS for 5 correct in a row!")
                }
            }else {
                _correctInARow.value = 0
                _lastEarnedPoints.value = 0
              //  _lastEarnedPoints.value = 0 // optional: feedback for wrong

            }

            // ✅ Debugging Output
            println("🔍 ${if (isTriviaMode) "Trivia" else if (_quizMode.value == QuizMode.FLAG) "Flag" else "Logo"} Answered: $selectedAnswer | Correct: ${currentQuestion.correctAnswer}")
            println("📊 Score Updated: ${_correctAnswers.value}")
            println("📝 Saved Answers: ${_userAnswers.value}")
        }
    }

    fun setLastEarnedPoints(points: Int) {
        _lastEarnedPoints.value = points
    }
    fun addBonusPoints(points: Int) {
        addPoints(points)
        _lastEarnedPoints.value = points
    }

    fun resetCorrectInARow() {
        _correctInARow.value = 0
    }


fun moveToNextQuestion(
    navController: NavController,
    showFinishModal: (Boolean) -> Unit, // Pass a callback to control modal visibility
    navigateToResults: () -> Unit // Separate function to navigate after modal delay
) {
    val questionsList = when (_quizMode.value) {
        QuizMode.TRIVIA -> _questions.value
        QuizMode.FLAG -> _flagQuestions.value
        QuizMode.LOGO -> _logoQuestions.value
    }

    if (_currentQuestionIndex.value < questionsList.size - 1) {
        _currentQuestionIndex.value += 1
    } else {
        // ✅ Instead of navigating immediately, show the modal
        showFinishModal(true)

        // ✅ Delay navigation after modal animation
        Handler(Looper.getMainLooper()).postDelayed({
            showFinishModal(false)
            navigateToResults()
        }, 3000) // 3 seconds delay
    }
}
    private var skipUnlockCheck = false
    fun resetQuiz() {
        _currentQuestionIndex.value = 0
        _correctAnswers.value = 0
        _userAnswers.value = emptyList()
        _lastEarnedPoints.value = null
        _justUnlocked.value = emptyList()
        skipUnlockCheck = true // ✅ mark it
        _hasTriggeredStreakBonus.value = false // 👈 reset streak bonus
        _hasTriggeredEndBonus.value = false // 👈 reset end bonus
        _showStreakBonusPopup.value = false
        _showEndBonusPopup.value = false
        checkUnlocks() // ✅ Check for newly unlocked categories
    }
    // ✅ Country Code to Country Name Mapping
    private val countryNames = mapOf(
        "US" to "United States",
        "FR" to "France",
        "DE" to "Germany",
        "IT" to "Italy",
        "JP" to "Japan",
        "IN" to "India",
        "BR" to "Brazil",
        "CA" to "Canada",
        "MX" to "Mexico",
        "AU" to "Australia"
    )

    fun fetchFlagQuestions() {
        viewModelScope.launch {
            setQuizMode(QuizMode.FLAG)

            val countryCodes = countryNames.keys.toList()
            val questions = countryCodes.shuffled().take(10).map { countryCode ->
                val flagUrl = flagRepository.getFlag(countryCode)
                val correctAnswer = countryNames[countryCode] ?: countryCode // ✅ Convert to country name
                println("🌍 Fetching flag for $correctAnswer ($countryCode) -> $flagUrl")

                // ✅ Convert answer choices to country names
                val options = (countryCodes - countryCode)
                    .shuffled()
                    .take(3)
                    .map { countryNames[it] ?: it } + correctAnswer

                Question(
                    questionText = "Which country does this flag belong to?",
                    correctAnswer = correctAnswer, // ✅ Use country name
                    options = options.shuffled(),  // ✅ Display country names
                    type = "multiple",
                    flagUrl = flagUrl ?: ""
                )
            }
            _flagQuestions.value = questions
        }
    }

    fun fetchLogoQuestions() {
        viewModelScope.launch {
            _quizMode.value = QuizMode.LOGO
            _currentQuestionIndex.value = 0
            _correctAnswers.value = 0
            _userAnswers.value = emptyList()

            val companies = listOf("Microsoft", "Google", "Apple", "Amazon", "Facebook", "Tesla", "Nike", "Samsung")
            val questions = companies.shuffled().take(10).map { companyName ->
                val logoUrl = logoRepository.getLogo(companyName)
                val options = (companies - companyName).shuffled().take(3) + companyName

                Question(
                    questionText = "Which company does this logo belong to?",
                    correctAnswer = companyName,
                    options = options.shuffled(),
                    type = "multiple",
                    flagUrl = logoUrl ?: "" // ✅ Reuse flagUrl field for logos
                )
            }
            _logoQuestions.value = questions
        }
    }





    fun setElapsedTime(totalTime: Int, timeLeft: Int) {
        _elapsedTime.value = totalTime - timeLeft // ✅ Calculate time taken
    }

    fun loadWordAssociations(context: Context) {
        viewModelScope.launch {
            try {
                val inputStream = context.resources.openRawResource(R.raw.word_association)
                val reader = InputStreamReader(inputStream)
                val jsonData = Gson().fromJson(reader, WordAssociationData::class.java)
                reader.close()

                allWordPairs = jsonData.word_associations
                val selectedPairs = allWordPairs.shuffled().take(4)
                _totalPairsCount.value = selectedPairs.size
                _wordAssociationQuestions.value = selectedPairs.flatMap { listOf(it.word1, it.word2) }.shuffled()
            } catch (e: Exception) {
                println("Error loading JSON: ${e.message}")
            }
        }
    }

    fun isCorrectPair(word1: String, word2: String): Boolean {
        val correct = allWordPairs.any { (a, b) -> (a == word1 && b == word2) || (a == word2 && b == word1) }
        if (correct) _correctPairsCount.value += 1
        return correct
    }

    fun resetGame() {
        _correctPairsCount.value = 0
        _totalPairsCount.value = 0

    }
    fun getCategoryNameById(id: Int): String {
        return when (id) {
            22 -> "Geography"
            17 -> "Science"
            23 -> "History"
            27 -> "Animals"
            9 -> "General Knowledge"
            25 -> "Art"
            21 -> "Sport"
            28 -> "Vehicles"
            19 -> "Mathematics"
            12 -> "Music"
            18 -> "Computers"
            20 -> "Mythology"
            500 -> "Word Association"
            999 -> "Guess the Flag"
            1999 -> "Guess the Logo"
            else -> "Mystery Category"
        }
    }
    fun clearLastEarnedPoints() {
        _lastEarnedPoints.value = null
    }
    fun resetAllData() {
        viewModelScope.launch {
            dataStore.saveTotalPoints(0)
            dataStore.saveNickname("") // Or remove it entirely if you use nullable nickname
            dataStore.saveUnlockedCategories(setOf(22, 9, 23)) // Default unlocked categories
        }
    }
    fun isFirstTimeUser(): Boolean = firstTimeUser
    fun markUserAsReturning() {
        viewModelScope.launch {
            dataStore.setFirstTime(false)
            firstTimeUser = false
        }
    }
    fun markEndBonusTriggered() {
        _hasTriggeredEndBonus.value = true
    }

/*
    fun fetchLocalFlagQuestions(context: Context) {
        viewModelScope.launch {
            setQuizMode(QuizMode.FLAG)
            _currentQuestionIndex.value = 0
            _correctAnswers.value = 0
            _userAnswers.value = emptyList()


            try {

                // 🔍 DEBUG: List asset files
                context.assets.list("")?.forEach {
                    println("📦 Asset file: $it")
                }


                val inputStream = context.assets.open("countries.json")
                val jsonText = inputStream.bufferedReader().use { it.readText() }
                val countryMap = Gson().fromJson(jsonText, Map::class.java) as Map<String, String>

                val shuffledCountries = countryMap.entries.shuffled().take(10)

                val questions = shuffledCountries.map { (code, name) ->
                    val correctAnswer = name
                    val fileName = "$code.svg"

                    val options = (countryMap.values - correctAnswer).shuffled().take(3) + correctAnswer
                    println("🟢 Using flag file: $fileName for country: $correctAnswer")

                    Question(
                        questionText = "Which country does this flag belong to?",
                        correctAnswer = correctAnswer,
                        options = options.shuffled(),
                        type = "multiple",
                        flagUrl = fileName // Only the filename, used by your LocalFlagQuestionCard
                    )
                }
                questions.forEach {
                    println("🔎 Question: ${it.questionText}")
                    println("✅ Correct Answer: ${it.correctAnswer}")
                    println("🎌 Flag file: ${it.flagUrl}")
                    println("🌀 Options: ${it.options}")
                }

                _flagQuestions.value = questions
            } catch (e: Exception) {
                println("❌ Error loading countries.json: ${e.message}")
            }
        }
    }
*/
fun fetchLocalFlagQuestions(context: Context) {
    viewModelScope.launch {
        setQuizMode(QuizMode.FLAG)
        _currentQuestionIndex.value = 0
        _correctAnswers.value = 0
        _userAnswers.value = emptyList()

        try {
            val inputStream = context.assets.open("countries.json")
            val jsonText = inputStream.bufferedReader().use { it.readText() }
            val countryMap = Gson().fromJson(jsonText, Map::class.java) as Map<String, String>

            // ✅ Get list of available flag files (like "ae.svg")
            val availableFlags = context.assets.list("flags")?.toSet() ?: emptySet()

            // ✅ Filter only countries that have a flag file
            val validCountries = countryMap.filter { (code, _) ->
                availableFlags.contains("$code.svg")
            }.entries.shuffled().take(10)

            val questions = validCountries.map { (code, name) ->
                val correctAnswer = name
                val fileName = "$code.svg"

                val options = (countryMap.values - correctAnswer).shuffled().take(3) + correctAnswer

                Question(
                    questionText = "Which country does this flag belong to?",
                    correctAnswer = correctAnswer,
                    options = options.shuffled(),
                    type = "multiple",
                    flagUrl = fileName
                )
            }

            _flagQuestions.value = questions
        } catch (e: Exception) {
            println("❌ Error loading countries.json: ${e.message}")
        }
    }
}

    fun fetchLocalLogoQuestions(context: Context) {
        viewModelScope.launch {
            setQuizMode(QuizMode.LOGO)
            _currentQuestionIndex.value = 0
            _correctAnswers.value = 0
            _userAnswers.value = emptyList()

            try {
                val inputStream = context.assets.open("logos.json")
                val jsonText = inputStream.bufferedReader().use { it.readText() }
                val logoMap = Gson().fromJson(jsonText, Map::class.java) as Map<String, String>

                val shuffledLogos = logoMap.entries.shuffled().take(10)

                val questions = shuffledLogos.map { (fileName, companyName) ->
                    println("📦 Adding logo question → fileName: $fileName | companyName: $companyName")

                    val correctAnswer = companyName
                    val options = (logoMap.values - correctAnswer).shuffled().take(3) + correctAnswer

                    Question(
                        questionText = "Which company does this logo belong to?",
                        correctAnswer = correctAnswer,
                        options = options.shuffled(),
                        type = "multiple",
                        flagUrl = fileName // reused field for logo file name
                    )
                }

                _logoQuestions.value = questions
            } catch (e: Exception) {
                println("❌ Error loading logos.json: ${e.message}")
            }
        }
    }

// ✅ Word Association
    private val _selectedWords = MutableStateFlow<List<String>>(emptyList())
    val selectedWords = _selectedWords.asStateFlow()

    private val _matchedWords = MutableStateFlow<Set<String>>(emptySet())
    val matchedWords = _matchedWords.asStateFlow()

    fun onWordClicked(word: String) {
        if (_matchedWords.value.contains(word)) return // already matched

        val currentSelection = _selectedWords.value

        if (currentSelection.contains(word)) return // already selected

        if (currentSelection.size == 2) return // wait for reset before selecting more

        _selectedWords.value = currentSelection + word

        if (_selectedWords.value.size == 2) {
            val (first, second) = _selectedWords.value

            if (isCorrectPair(first, second)) {
                viewModelScope.launch {
                    _matchedWords.value = _matchedWords.value + setOf(first, second)

                    // ✅ Delay the disappearance to show green briefly
                    delay(600)
                    _wordAssociationQuestions.value =
                        _wordAssociationQuestions.value.filterNot { it == first || it == second }
                }
            }

            // Reset selection after short delay
            viewModelScope.launch {
                delay(700)
                _selectedWords.value = emptyList()
            }
        }
    }


}
