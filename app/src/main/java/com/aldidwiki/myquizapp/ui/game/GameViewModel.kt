package com.aldidwiki.myquizapp.ui.game

import androidx.lifecycle.*
import com.aldidwiki.myquizapp.data.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(private val appRepository: AppRepository) : ViewModel() {
    private val token = MutableLiveData<String>()
    fun setToken(token: String) {
        this.token.value = token
    }

    private val _answeredCount = MutableLiveData(0)
    val answeredCount: LiveData<Int> get() = _answeredCount
    fun updateAnsweredCount() {
        _answeredCount.value = _answeredCount.value?.plus(1)
    }

    private val _totalScore = MutableLiveData(0)
    val totalScore: LiveData<Int> get() = _totalScore
    private val _correctAnswer = MutableLiveData(0)
    val correctAnswer: LiveData<Int> get() = _correctAnswer
    private val _incorrectAnswer = MutableLiveData(0)
    val incorrectAnswer: LiveData<Int> get() = _incorrectAnswer
    fun updateScore(isCorrect: Boolean) {
        if (isCorrect) {
            _totalScore.value = _totalScore.value?.plus(20)
            _correctAnswer.value = _correctAnswer.value?.plus(1)
        } else {
            _totalScore.value = 0.coerceAtLeast(_totalScore.value?.minus(10)!!)
            _incorrectAnswer.value = _incorrectAnswer.value?.plus(1)
        }
    }

    fun getQuestions() = token.switchMap { appRepository.getQuestions(it).asLiveData() }
}