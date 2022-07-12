package com.example.minstalesapp.Profile

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.minstalesapp.databinding.ProfileActivityViewBinding


class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ProfileActivityViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProfileActivityViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            this.finish()
        }

        binding.disconnectContainer.setOnClickListener {
            this.finish()
        }

        //val sharedPreferences = getSharedPreferences("minstales", MODE_PRIVATE)
        //val token = sharedPreferences.getString("user_token", null)

        //Requête API pour récup toutes les infos user
        //sharedPreferences.edit().putStringSet("user_infos", mutableSetOf("main_volumme","music_volumme","voices_volumme","app_language")).apply()


        /*
        val sharedPreferences: SharedPreferences = if (binding.usernameProfile.text.toString().isEmpty()) {
            getPreferences(MODE_PRIVATE)
        } else {
            getSharedPreferences(binding.usernameProfile.text.toString(), MODE_PRIVATE)
        }*/
        //TO DO : Faire une requete avec le token stocké dans les sharedPreference pour récupérer les infos du user
    }
}