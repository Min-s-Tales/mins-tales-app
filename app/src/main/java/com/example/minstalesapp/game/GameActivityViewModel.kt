package com.example.minstalesapp.game

import android.util.Log
import androidx.lifecycle.ViewModel

sealed class GameActivityViewModelState(
    open val recordingMessage: String = "You are now recording",
    open val recordedMessage: String = "Your record is done",
    open val neutralMessage: String = "Neutral",
    open val errorMessage: String = "",
    open val json: String = "",
    open val status: Status = Status.NEUTRAL,
    open val recordButtonEnabled: Boolean = false,

    ) {
    object Loading : GameActivityViewModelState()
    data class ERROR(override val errorMessage: String) : GameActivityViewModelState()
    data class NEUTRALSTATUS(override val status: Status, override val recordButtonEnabled: Boolean) : GameActivityViewModelState()
    data class RECORDINGSTATUS(override val status: Status, override val recordButtonEnabled: Boolean) : GameActivityViewModelState()
    data class RECORDEDSTATUS(override val status: Status, override val recordButtonEnabled: Boolean) : GameActivityViewModelState()
    data class JSON(override val json: String) : GameActivityViewModelState()
}

class GameActivityViewModel : ViewModel() {

    private final var TAG = "GameActivityViewModel"

    fun record() {
        Log.i(TAG, "record: ")
    }

    fun checkAllNeededWordsSpoken(neededWords: ArrayList<String>, spoken: String) : Boolean {
        val spokenWords = spoken.split(" ")
        val commonWords = ArrayList<String>()
        for (spokenWord in spokenWords) {
            if (!commonWords.contains(spokenWord) && neededWords.contains(spokenWord)) {
                commonWords.add(spokenWord)
            }
        }
        return commonWords.size == neededWords.size
    }

    fun saveGame(step: String) {
        //save = step
    }
}