package com.example.minstalesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var loginButtonToggle: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        playButton.setOnClickListener {
            startActivity(Intent(this, ListStoryActivity::class.java))
        }

        profileButton.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        marketButton.setOnClickListener {
            startActivity(Intent(this, MarketplaceActivity::class.java))
        }

        micButton.setOnClickListener {
            startActivity(Intent(this, RecordActivity::class.java))
        }

        loginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        signUpButton.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }
}