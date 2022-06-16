package com.example.minstalesapp.game

import android.app.Activity
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.nfc.Tag
import android.opengl.Visibility
import android.util.Log
import android.view.View
import java.io.File
import java.io.IOException
import java.util.*
import kotlin.collections.HashMap

class SoundManager() {
    private val TAG = "[SoundManager]"
    //private val gameActivity : GameActivity = gameActivity
    var outputSounds = HashMap<Outputs, OutputSoundManager>()

    class OutputSoundManager() {
        var sounds = HashMap<String, MediaPlayer>()


        /**
         * Add the sonf to the manager with specified ID
         */
        fun addSound(title: String, sound: MediaPlayer) {
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
        outputSounds[Outputs.MUSIC] = OutputSoundManager()
        outputSounds[Outputs.NARRATOR] = OutputSoundManager()
    }

    /**
     * Pause every sounds from list
     */
    fun pauseAll() {
        for ((key, outputSoundManager) in outputSounds) {
            for ((key, sound) in outputSoundManager.sounds) {
                outputSoundManager.stopSound(sound)
                outputSoundManager.removeSound(key)
            }
        }
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

    fun stopAll(output: Outputs) {
        val outputSound = outputSounds[output]
        for ((key, sound) in outputSound!!.sounds) {
            outputSound.stopSound(sound)
            outputSound.removeSound(key)
        }
    }

    fun prepareSound(activity: GameActivity, gameTitle: String, titlePath: String, out: String, loop: Boolean) : MediaPlayer? {
        try {
            val sound = MediaPlayer().apply {
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build()
                )

                val soundURI =
                    Uri.parse("${activity.getExternalFilesDir("Tales")!!.path}/$gameTitle/assets/sounds/$titlePath")
                activity.binding.audioTitle.text = File(soundURI.toString()).name
                setDataSource(activity.applicationContext, soundURI)
                prepare()
                start()
            }
            sound.isLooping = loop
            sound.setVolume(0.5f, 0.5f)
            if (out == Outputs.NARRATOR.toString().lowercase()) {
                sound.setOnCompletionListener {
                    activity.binding.record.visibility = View.VISIBLE
                }
            }
            return sound
        } catch (e: IOException) {
            Log.e(TAG,  "Unavailable file : $titlePath, unlocking the record button.")
            if (out == Outputs.NARRATOR.toString().lowercase()) {
                activity.binding.record.visibility = View.VISIBLE
            }
        }
        return null
    }
}