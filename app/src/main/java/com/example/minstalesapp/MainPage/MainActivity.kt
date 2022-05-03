package com.example.minstalesapp.MainPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.minstalesapp.Api.ApiHelper
import com.example.minstalesapp.Api.ApiService
import com.example.minstalesapp.Model.Story
import com.example.minstalesapp.R
import com.example.minstalesapp.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.security.auth.callback.Callback

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var ownedStoryList = ArrayList<Story>()





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.storiesToggleButton.isChecked = true


        val apiStoryTest = ApiHelper.getInstance().create(ApiService::class.java)
        GlobalScope.launch {
            val result = apiStoryTest.getStory()
            Log.d("ayush: ", result.body().toString())
        }


/*        ownedStoryList.add(
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
        )*/

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