package com.aldidwiki.myquizapp.ui.game

import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.lifecycle.*
import com.aldidwiki.myquizapp.data.AppRepository
import com.aldidwiki.myquizapp.data.source.local.entity.QuestionEntity
import com.aldidwiki.myquizapp.helper.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(private val appRepository: AppRepository) : ViewModel() {
    /*this is to set token that used to retrieve the question from api*/
    private val token = MutableLiveData<String>()
    fun setToken(token: String) {
        this.token.value = token
    }

    fun getQuestions() = token.switchMap { token -> appRepository.getQuestions(token).asLiveData() }

    /*this is to save the answered question to temp database, the database will be cleared
    * every time user start the quiz for the first time*/
    var questionFix = ""
    var correctAnswerFix = ""
    fun insertTempQuestion() {
        viewModelScope.launch {
            appRepository.insertQuestion(QuestionEntity(
//                    id = 0,
                    question = questionFix,
                    correctAnswer = correctAnswerFix,
                    isCorrect = isCorrect
            ))
        }
    }

    /*this is use to count the total question has already answered*/
    private val _answeredCount = MutableLiveData(0)
    val answeredCount: LiveData<Int> get() = _answeredCount
    fun updateAnsweredCount(isCorrect: Boolean) {
        _answeredCount.value = _answeredCount.value?.plus(1)
        updateScore(isCorrect)
    }

    /*this is to manage state whether app is on last question or not*/
    val onLastQuestion = Transformations.map(answeredCount) { it == Constant.QUESTION_COUNT }

    /*this is to manage the next button state whether user already answer or not*/
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
            _totalScore.value = _totalScore.value?.plus(20)
            _correctAnswer.value = _correctAnswer.value?.plus(1)
        } else {
            _totalScore.value = 0.coerceAtLeast(_totalScore.value?.minus(10)!!)
            _incorrectAnswer.value = _incorrectAnswer.value?.plus(1)
        }
    }

    /*this is to manage the state whether the time is finished or not*/
    private val _eventTimeFinished = MutableLiveData(false)
    val eventTimeFinished: LiveData<Boolean> get() = _eventTimeFinished
    fun onTimeFinished() {
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