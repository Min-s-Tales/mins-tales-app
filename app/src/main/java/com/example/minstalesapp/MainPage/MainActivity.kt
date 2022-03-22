package com.example.minstalesapp.MainPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.minstalesapp.Model.Story
import com.example.minstalesapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var ownedStoryList = ArrayList<Story>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.storiesToggleButton.isChecked = true

        ownedStoryList.add(
            Story(0, "story 1", "description story 1 \nblabla \nblalba", "", "", 12.5F, 2)
        )
        ownedStoryList.add(
            Story(0, "story 2", "description story 2 \nblablabla \nblalbabla", "", "", 8.35F, 12)
        )
        ownedStoryList.add(
            Story(0, "story 3", "description story 3 \nblablablablou \nblalbablablou", "", "", 57F, 39)
        )

        binding.viewPager.adapter = PagerAdapterMainActivity(this, ownedStoryList)

        binding.viewPager.pageMargin = 50
        binding.viewPager.setPadding(80, 0, 80, 0);
        binding.viewPager.clipToPadding = false
    }
}