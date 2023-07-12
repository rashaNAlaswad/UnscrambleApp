package com.rns.unscrambleapplication

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    private var _score = MutableLiveData(0)
    val score: LiveData<Int> get() = _score

    private var _wordCount = MutableLiveData(0)
    val wordCount: LiveData<Int> get() = _wordCount

    private var _scrambleWord = MutableLiveData<String>()
    val scrambleWord: LiveData<String> get() = _scrambleWord

    private var newGameList = mutableListOf<String>()
    private lateinit var currentWord: String

    init {
        getNextWord()
    }

    private fun getNextWord() {
        currentWord = allWordsList.random()
        val temp = currentWord.toCharArray()
        temp.shuffle()

        if (newGameList.contains(currentWord)) {
            getNextWord()
        } else {
            _wordCount.value = _wordCount.value?.inc()
            _scrambleWord.value = String(temp)
            Log.d("getNextWord", "getNextWord: $currentWord")
            newGameList.add(currentWord)
        }
    }

    private fun increaseScore() {
        _score.value =_score.value!!.plus(SCORE_INCREASE)
    }

    fun isWordCorrect(word: String): Boolean {
        return if (word.equals(currentWord, true)) {
            increaseScore()
            true
        } else false
    }

    fun isThereNextWord(): Boolean {
        return if (_wordCount.value!! < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }

    fun reinitializeData() {
        _score.value = 0
        _wordCount.value = 0
        newGameList.clear()
        getNextWord()
    }
}