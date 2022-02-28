package com.example.minstalesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.minstalesapp.game.GameActivity
import com.example.minstalesapp.game.TestActivity
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
            startActivity(Intent(this, GameActivity::class.java))
        }

        loginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            loginButton.visibility = View.INVISIBLE
            signUpButton.visibility = View.VISIBLE
        }

        signUpButton.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
            signUpButton.visibility = View.INVISIBLE
            loginButton.visibility = View.VISIBLE
        }
    }
}