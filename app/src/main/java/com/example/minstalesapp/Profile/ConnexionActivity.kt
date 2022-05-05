package com.example.minstalesapp.Profile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.minstalesapp.databinding.ActivityConnexionBinding

class ConnexionActivity: AppCompatActivity() {

    private lateinit var binding: ActivityConnexionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConnexionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginContainer.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        binding.registerContainer.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        binding.registerLater.setOnClickListener {
            this.finish()
        }
    }
}