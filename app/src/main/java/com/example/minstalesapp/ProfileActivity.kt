package com.example.minstalesapp

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.minstalesapp.databinding.ProfileActivityViewBinding
import kotlinx.android.synthetic.main.profile_activity_view.*


class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ProfileActivityViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProfileActivityViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences: SharedPreferences = if (binding.usernameProfile.text.toString().isEmpty()) {
            getPreferences(MODE_PRIVATE)
        } else {
            getSharedPreferences(binding.usernameProfile.text.toString(), MODE_PRIVATE)
        }
        //TO DO : Faire une requete avec le token stocké dans les sharedPreference pour récupérer les infos du user
    }
}