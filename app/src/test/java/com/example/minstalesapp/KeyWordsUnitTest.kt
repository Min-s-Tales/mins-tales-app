package com.example.minstalesapp

import kotlin.collections.ArrayList
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

/**
 * Test class that test the handling of the advancement of player depending of the possible choices
 * and the player orders
 */
class KeyWordsUnitTest {
    private val TAG = "KeyWordsUnitTest"
    private val neededWords : ArrayList<String> = arrayListOf("bonjour", "tous")


    /**
     * Check if all the needed key words are pronounced by the player.
     * If yes, the game will continue using a branch. Else, The player must retry.
     */
    @Test
    fun checkAllNeededWordsSpoken(spoken: String) : Boolean {
        val spokenWords = spoken.split(" ")
        val commonWords = ArrayList<String>()
        for (spokenWord in spokenWords) {
            if (!commonWords.contains(spokenWord) && neededWords.contains(spokenWord)) {
                commonWords.add(spokenWord)
            }
        }
        return commonWords.size == neededWords.size
    }

    internal class CommonValidatorTest() {
        private val validator = KeyWordsUnitTest()

        @ParameterizedTest(name = "given \"{0}\", when validating the sentence, then it should return {1}")
        @MethodSource("spokenArguments")
        fun `given input string, when validating it, then is should return if it is valid`(
            spoken: String,
            expected: Boolean
        ) {
            val actual = validator.checkAllNeededWordsSpoken(spoken)
            assert(actual == expected)
        }

        private companion object {
            @JvmStatic
            fun spokenArguments() = Stream.of(
                Arguments.of("bonjour test", false),
                Arguments.of("bonsoir à tous", false),
                Arguments.of("bonjour à tous les gens", true),
                Arguments.of("tous les gens me disent bonjour", true),
                Arguments.of("bonjour messieurs", false),
                Arguments.of("tout est silencieux", false)
            )
        }
    }
}