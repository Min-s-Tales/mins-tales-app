package com.example.minstalesapp.MainPage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.minstalesapp.Model.Story
import com.example.minstalesapp.ProfileActivity
import com.example.minstalesapp.R
import com.example.minstalesapp.databinding.ActivityMainBinding
import com.example.minstalesapp.game.GameActivity


import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

/*    object ApiClient {
        private const val BASE_URL: String = "https://jsonplaceholder.typicode.com/"
        private val gson: Gson by lazy {
            GsonBuilder().setLenient().create()
        }
        private val httpClient: OkHttpClient by lazy {
            OkHttpClient.Builder().build()
        }
        private val retrofit: Retrofit by lazy {
            Retrofit.Builder().baseUrl(BASE_URL).client(httpClient)
                 .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
        val apiService: ApiService by lazy { retrofit.create(ApiService::class.java) }
    }*/

    private lateinit var binding: ActivityMainBinding
    var ownedStoryList = ArrayList<Story>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.storiesToggleButton.isChecked = true

        ownedStoryList.add(
            Story(0, "story1", "description story 1 \nblabla \nblalba", R.raw.guignol, "", 12.5F, 2)
        )
        ownedStoryList.add(
            Story(
                0,
                "story2",
                "description story 2 \nblablabla \nblalbabla",
                R.raw.cartman,
                "",
                8.35F,
                12
            )
        )
        ownedStoryList.add(
            Story(
                0,
                "story3",
                "description story 3 \nblablablablou \nblalbablablou",
                R.raw.soldier,
                "",
                57F,
                39
            )
        )

        binding.viewPager.adapter = PagerAdapterMainActivity(this, ownedStoryList)

        binding.viewPager.pageMargin = 50
        binding.viewPager.setPadding(80, 0, 80, 0);
        binding.viewPager.clipToPadding = false

        binding.headerProfileIcon.setOnClickListener {
            //val intent = Intent(this, ProfileActivity::class.java)
            //startActivity(intent)
        }
    }
}