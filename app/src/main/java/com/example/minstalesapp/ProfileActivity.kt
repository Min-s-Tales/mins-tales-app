package com.example.minstalesapp

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.profile_activity_view.*


class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_activity_view)

        val sharedPreferences: SharedPreferences = if (profileUsernameText.text.toString().isEmpty()) {
            getPreferences(MODE_PRIVATE)
        } else {
            getSharedPreferences(profileUsernameText.text.toString(), MODE_PRIVATE)
        }
        //TO DO : Faire une requete avec le token stocké dans les sharedPreference pour récupérer les infos du user
    }
}