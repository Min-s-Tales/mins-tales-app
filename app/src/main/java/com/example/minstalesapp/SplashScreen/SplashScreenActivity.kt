package com.example.minstalesapp.SplashScreen

import android.app.DownloadManager
import android.app.DownloadManager.Request.VISIBILITY_HIDDEN
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.webkit.CookieManager
import android.webkit.URLUtil
import android.widget.Toast
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
