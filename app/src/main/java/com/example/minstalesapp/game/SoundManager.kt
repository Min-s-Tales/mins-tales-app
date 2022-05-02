package com.example.minstalesapp.game

import android.media.MediaPlayer

class SoundManager {
    var outputSounds = HashMap<String, OutputSoundManager>()

    class OutputSoundManager() {
        var sounds = HashMap<String, MediaPlayer>()

        fun addSong(title: String, mediaPlayer: MediaPlayer) {
            if (sounds.containsKey(title)) {
                stopSound(title)
                removeSound(title)
                println("Replaced $title")
            } else {
                println("Added $title")
            }
            sounds[title] = mediaPlayer
            playSound(title)
        }

        fun playSound(title: String) {
            println("Playing $title")
            sounds[title]!!.start()
        }

        fun removeSound(title: String) {
            sounds.remove(title)
        }

        fun stopSound(title: String) {
            val mediaPlayer = sounds[title]
            if (mediaPlayer!!.isPlaying) {
                mediaPlayer.stop()
            }
            println("stopsound $title")
        }
    }

     fun init() {
        outputSounds["music"] = OutputSoundManager()
        outputSounds["narrator"] = OutputSoundManager()
    }

    fun stopAll() {
        for ((key, outputSoundManager) in outputSounds) {
            for ((key, sound) in outputSoundManager.sounds) {
                outputSoundManager.stopSound(key)
                outputSoundManager.removeSound(key)
            }
        }
    }
}