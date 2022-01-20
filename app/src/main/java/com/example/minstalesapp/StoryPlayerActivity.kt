package com.example.minstalesapp

import android.media.MediaPlayer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.story_player_activity.*

class StoryPlayerActivity : AppCompatActivity() {
    lateinit var audioPlayer: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.story_player_activity)

        val idAudio = intent.getIntExtra("audioId", 0)
        val title = intent.getStringExtra("title")

        audioPlayer = MediaPlayer.create(this, idAudio)
        audioPlayer.isLooping = false
        audioPlayer.setVolume(0.5f, 0.5f)
        audioPlayer.start()

        audioTitle.text = title.toString()
    }

    override fun onDestroy() {
        super.onDestroy()
        audioPlayer.stop()
    }
}