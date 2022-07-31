package com.aldidwiki.myquizapp.presentation.ui.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.lifecycle.*
import com.aldidwiki.myquizapp.data.model.QuestionParameter
import com.aldidwiki.myquizapp.data.source.local.entity.QuestionEntity
import com.aldidwiki.myquizapp.domain.model.UserDomainModel
import com.aldidwiki.myquizapp.domain.usecase.AppUseCase
import com.aldidwiki.myquizapp.helper.Constant
import com.aldidwiki.myquizapp.helper.MapKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(private val appUseCase: AppUseCase) : ViewModel() {
    var userName = ""

    /*this is use to input api getQuestions params*/
    private val questionParams = MutableLiveData<QuestionParameter>()
    fun setQuestionParams(questionParameter: QuestionParameter) {
        this.questionParams.value = questionParameter
    }

    fun getQuestions() = questionParams.switchMap {
        appUseCase.getQuestions(it.token, it.categoryId, it.difficulty).asLiveData()
    }

    /*this is to save the answered question to temp database, the database will be cleared
    * every time user start the quiz for the first time*/
    val insertQuestionParams = mutableMapOf<MapKey, String>()
    fun insertTempQuestion() {
        viewModelScope.launch {
            appUseCase.insertQuestion(QuestionEntity(
                    question = insertQuestionParams[MapKey.QUESTION] ?: "",
                    correctAnswer = insertQuestionParams[MapKey.CORRECT_ANSWER] ?: "",
                    isCorrect = isCorrect
            ))
        }
    }

    /*this is to observe the score, answered count, etc for achievement fragment*/
    private val result = mutableMapOf<MapKey, Int>()
    private val _user = MediatorLiveData<UserDomainModel>()
    val user: LiveData<UserDomainModel> get() = _user
    private fun initUser() {
        _user.addSource(answeredCount) { totalAnswered -> result[MapKey.TOTAL_ANSWERED] = totalAnswered }
        _user.addSource(totalScore) { totalScore -> result[MapKey.TOTAL_SCORE] = totalScore }
        _user.addSource(correctAnswer) { totalCorrect -> result[MapKey.TOTAL_CORRECT] = totalCorrect }
        _user.addSource(incorrectAnswer) { totalIncorrect -> result[MapKey.TOTAL_INCORRECT] = totalIncorrect }
    }

    /*this is use to count the total question has already answered*/
    private val _answeredCount = MutableLiveData(0)
    val answeredCount: LiveData<Int> get() = _answeredCount
    fun updateAnsweredCount(isCorrect: Boolean) {
        _answeredCount.value = _answeredCount.value?.plus(1)
        updateScore(isCorrect)

        _user.value = UserDomainModel(
                id = 0,
                name = userName,
                totalAnswered = result[MapKey.TOTAL_ANSWERED] ?: 0,
                totalScore = result[MapKey.TOTAL_SCORE] ?: 0,
                totalCorrect = result[MapKey.TOTAL_CORRECT] ?: 0,
                totalIncorrect = result[MapKey.TOTAL_INCORRECT] ?: 0
        )
    }

    /*this is to manage state whether app is on last question or not*/
    val onLastQuestion = Transformations.map(answeredCount) { it == Constant.QUESTION_COUNT }

    /*this is to manage the next button state whether user already answered or not*/
    private val _hasAnswered = MutableLiveData(false)
    val hasAnswered: LiveData<Boolean> get() = _hasAnswered
    fun setHasAnswered(hasAnswered: Boolean) {
        _hasAnswered.value = hasAnswered
    }

    /*use this to know whether the answer is correct or not to manage update score func,
    * also for communicating between question fragment and game fragment*/
    var isCorrect = false
    fun setIsCorrect(isCorrect: Boolean) {
        this.isCorrect = isCorrect
    }

    /*this is to manage total score, correct and incorrect answers*/
    private val _totalScore = MutableLiveData(0)
    val totalScore: LiveData<Int> get() = _totalScore
    private val _correctAnswer = MutableLiveData(0)
    val correctAnswer: LiveData<Int> get() = _correctAnswer
    private val _incorrectAnswer = MutableLiveData(0)
    val incorrectAnswer: LiveData<Int> get() = _incorrectAnswer
    private fun updateScore(isCorrect: Boolean) {
        if (isCorrect) {
            _totalScore.value = _totalScore.value?.plus(Constant.CORRECT_POINT)
            _correctAnswer.value = _correctAnswer.value?.plus(1)
        } else {
            _totalScore.value = 0.coerceAtLeast(_totalScore.value?.minus(Constant.INCORRECT_POINT)!!)
            _incorrectAnswer.value = _incorrectAnswer.value?.plus(1)
        }
    }

    /*this is to manage the state whether the time is finished or not*/
    private val _eventTimeFinished = MutableLiveData(false)
    val eventTimeFinished: LiveData<Boolean> get() = _eventTimeFinished
    private fun onTimeFinished() {
        _eventTimeFinished.value = true
    }

    /*setup the countdown timer*/
    private var timer: CountDownTimer? = null
    private val _currentTime = MutableLiveData<Long>()
    private val currentTime: LiveData<Long> get() = _currentTime
    val currentTimeString = Transformations.map(currentTime) { time ->
        DateUtils.formatElapsedTime(time)
    }

    init {
        initUser()

        timer = object : CountDownTimer(ONE_MINUTE, ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = millisUntilFinished / ONE_SECOND
            }

            override fun onFinish() {
                _currentTime.value = TIME_FINISHED
                onTimeFinished()
            }
        }.start()
    }

    override fun onCleared() {
        super.onCleared()
        //remove the timer to avoid memory leak
        timer?.cancel()
    }

    companion object {
        private const val TIME_FINISHED = 0L
        private const val ONE_MINUTE = 60_000L
        private const val ONE_SECOND = 1000L
    }
}