package com.example.minstalesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        playButton.setOnClickListener {
            startActivity(Intent(this, ListStoryActivity::class.java))
        }

        marketButton.setOnClickListener {
            startActivity(Intent(this, MarketplaceActivity::class.java))
        }

        micButton.setOnClickListener {
            startActivity(Intent(this, RecordActivity::class.java))
        }
    }
}