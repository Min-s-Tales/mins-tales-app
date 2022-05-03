package com.example.minstalesapp.MainPage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.minstalesapp.Model.Story
import com.example.minstalesapp.ProfileActivity
import com.example.minstalesapp.R
import com.example.minstalesapp.databinding.ActivityMainBinding
import com.example.minstalesapp.game.GameActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var ownedStoryList = ArrayList<Story>()
    val listOfStoryTypes = arrayOf("Fantasy", "History", "Medieval", "Pirate", "Horror", "Science-Fiction", "Post-Apocalyptic")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toggleSwitchButton.check(R.id.stories_toggle_button)

        binding.toggleSwitchButton.setOnCheckedChangeListener { _, optionId ->
            when (optionId) {
                R.id.stories_toggle_button -> {
                    binding.marketPlaceContainer.visibility = View.GONE
                    binding.storiesContainer.visibility = View.VISIBLE
                }
                R.id.marketplace_toggle_button -> {
                    binding.storiesContainer.visibility = View.GONE
                    binding.marketPlaceContainer.visibility = View.VISIBLE
                }
            }
        }

        ownedStoryList.add(
            Story(0, "story1", "description story 1 \nblabla \nblalba", R.raw.guignol, "", 12.5F, 2, arrayOf("Fantasy", "Science-Fiction"))
        )
        ownedStoryList.add(
            Story(1, "story2", "description story 2 \nblablabla \nblalbabla", R.raw.cartman, "", 8.35F, 12, arrayOf("History", "Medieval"))
        )
        ownedStoryList.add(
            Story(2, "story3", "description story 3 \nblablablablou \nblalbablablou", R.raw.soldier, "", 57F, 39, arrayOf("History", "Pirate", "Post-Apocalyptic"))
        )

        binding.viewPager.adapter = PagerAdapterMainActivity(this, ownedStoryList)

        binding.viewPager.pageMargin = 50
        binding.viewPager.setPadding(80, 0, 80, 0);
        binding.viewPager.clipToPadding = false

        binding.marketStoriesContainer.adapter = ListAdapterStoryTypeMarketPlace(this, listOfStoryTypes)

        binding.headerProfileIcon.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }
}