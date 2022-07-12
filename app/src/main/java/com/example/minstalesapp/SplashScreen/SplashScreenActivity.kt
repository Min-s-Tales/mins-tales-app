package com.example.minstalesapp.SplashScreen

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.minstalesapp.MainPage.MainActivity
import com.example.minstalesapp.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler().postDelayed(
            Runnable {
                val intent = Intent(this, MainActivity::class.java)
                // download()
                startActivity(intent)
                this.finish()
            },
            3000
        )
    }
}