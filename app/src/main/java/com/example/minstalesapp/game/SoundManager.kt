package com.example.minstalesapp.game

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.nfc.Tag
import android.util.Log
import java.io.File
import java.io.IOException
import java.util.*
import kotlin.collections.HashMap

class SoundManager(gameActivity: GameActivity) {
    private val TAG = "[SoundManager]"
    private val gameActivity : GameActivity = gameActivity
    var outputSounds = HashMap<String, OutputSoundManager>()

    class OutputSoundManager() {
        var sounds = HashMap<String, MediaPlayer>()


        /**
         * Add the sonf to the manager with specified ID
         */
        fun addSong(title: String, sound: MediaPlayer) {
            if (sounds.containsKey(title)) {
                stopSound(sound)
                removeSound(title)
                println("Replaced $title")
            } else {
                println("Added $title")
            }
            sounds[title] = sound
            //playSound(sound)
        }

        /**
         * Play sound if not playing
         */
        fun playSound(sound: MediaPlayer) {
            if (sound.isPlaying) {
                sound.start()
            }
        }

        /**
         * Removes the sound from list
         * Must be used only on scene change or replace
         */
        fun removeSound(title: String) {
            sounds.remove(title)
        }

        /**
         * Stops the sound if playing.
         */
        fun stopSound(sound: MediaPlayer) {
            if (sound.isPlaying) {
                sound.stop()
            }
            println("stopsound")
        }
    }

    /**
     * Init the different possible outputs
     */
    fun init() {
        outputSounds["music"] = OutputSoundManager()
        outputSounds["narrator"] = OutputSoundManager()
    }

    /**
     * Stops and remove every sounds from list
     */
    fun stopAll() {
        for ((key, outputSoundManager) in outputSounds) {
            for ((key, sound) in outputSoundManager.sounds) {
                outputSoundManager.stopSound(sound)
                outputSoundManager.removeSound(key)
            }
        }
    }

    fun stopAll(out: String) {
        val outputSound = outputSounds[out]
        for ((key, sound) in outputSound!!.sounds) {
            outputSound.stopSound(sound)
            outputSound.removeSound(key)
        }
    }

    fun prepareSound(gameTitle: String, titlePath: String, out: String, loop: Boolean) {
        try {
            val sound = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )

                val soundURI =
                    Uri.parse(gameActivity.getExternalFilesDir("Tales")!!.path + "/$gameTitle/assets/sounds/$titlePath")

                Log.i(TAG, "prepareAudio: $soundURI")
                gameActivity.binding.audioTitle.text = File(soundURI.toString()).name
                setDataSource(gameActivity.applicationContext, soundURI)
                prepare()
                start()
            }
            sound.isLooping = loop
            sound.setVolume(0.5f, 0.5f)
            if (out == Outputs.NARRATOR.toString().lowercase()) {
                sound.setOnCompletionListener {
                    gameActivity.binding.record.isEnabled = true
                }
            }
            val outputSounds = outputSounds[out]
            outputSounds?.addSong(titlePath, sound)
            outputSounds?.playSound(sound)
        } catch (e: IOException) {
            Log.e(TAG,  "Unavailable file.")
        }
    }
}