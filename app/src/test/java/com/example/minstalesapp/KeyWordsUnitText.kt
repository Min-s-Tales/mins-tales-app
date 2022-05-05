package com.example.minstalesapp

import org.junit.Test
import kotlin.collections.ArrayList
import org.junit.Assert.*

class KeyWordsUnitText {
    private val TAG = "KeyWordsUnitText"
    private val spokenWords : ArrayList<String> = arrayListOf("Bonjour", "Test", "MEEM")
    private val neededWords : ArrayList<String> = arrayListOf("Bonjour", "save")


    /**
     * Check if all the needed key words are pronounced by the player.
     * If yes, the game will continue using a branch. Else, The player must retry.
     */
    @Test
    fun checkAllNeededWordsSpoken() {
        val commonWords = ArrayList<String>()
        for (spokenWord in spokenWords) {
            if (!commonWords.contains(spokenWord) && neededWords.contains(spokenWord)) {
                commonWords.add(spokenWord)
            }
        }
        assertEquals(commonWords.size, neededWords.size)
    }
}