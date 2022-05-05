package com.example.minstalesapp.MainPage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.minstalesapp.Model.Story
import com.example.minstalesapp.Profile.ConnexionActivity
import com.example.minstalesapp.Profile.ProfileActivity
import com.example.minstalesapp.R
import com.example.minstalesapp.databinding.ActivityMainBinding
import com.example.minstalesapp.game.GsonManager
import java.io.File

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

        val folder = getExternalFilesDir("Tales")
        val files = folder!!.listFiles()
        for (taleDirectory in files!!) {
            if (taleDirectory.isDirectory) {
                val dataFile = File("${taleDirectory.path}/data.json")
                if (dataFile.exists()) {
                    val story = GsonManager().dataReader(taleDirectory.name, dataFile.readText(Charsets.UTF_8))
                    if (story != null) {
                        ownedStoryList.add(story)
                    }
                }
            }
        }

        /*
        val apiStoryTest = ApiHelper.getInstance().create(ApiService::class.java)
        GlobalScope.launch {
            val result = apiStoryTest.getStory()
            Log.d("ayush: ", result.body().toString())
        }
         */

        binding.viewPager.adapter = PagerAdapterMainActivity(this, ownedStoryList)

        binding.viewPager.pageMargin = 50
        binding.viewPager.setPadding(80, 0, 80, 0);
        binding.viewPager.clipToPadding = false

        binding.marketStoriesContainer.adapter = ListAdapterStoryTypeMarketPlace(this, listOfStoryTypes)

        binding.headerProfileIcon.setOnClickListener {
            val intent = Intent(this, ConnexionActivity::class.java)
            startActivity(intent)
        }
    }
}