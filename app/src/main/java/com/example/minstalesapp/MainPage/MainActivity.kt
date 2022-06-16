package com.example.minstalesapp.MainPage

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.os.Environment.getExternalStorageDirectory
import android.os.Environment.getExternalStoragePublicDirectory
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.minstalesapp.Model.Story
import com.example.minstalesapp.Profile.ConnexionActivity
import com.example.minstalesapp.R
import com.example.minstalesapp.databinding.ActivityMainBinding
import com.example.minstalesapp.game.GsonManager
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var ownedStoryList = ArrayList<Story>()
    var mappedStories = mapOf<String, MutableList<Story>>()
    val listOfStoryTypes = arrayOf("Fantasy", "History", "Medieval", "Pirate", "Horror", "Science-Fiction", "Post-Apocalyptic", "Policier")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val queue = Volley.newRequestQueue(this)

        binding.toggleSwitchButton.check(R.id.stories_toggle_button)

        binding.toggleSwitchButton.setOnCheckedChangeListener { _, optionId ->
            when (optionId) {
                // Event handler on "Librairie" button's click
                R.id.stories_toggle_button -> {
                    binding.marketPlaceContainer.visibility = View.GONE
                    binding.storiesContainer.visibility = View.VISIBLE
                }
                // Event handler on "Market Place" button's click
                R.id.marketplace_toggle_button -> {

                    mappedStories = mapOf(
                        "Fantasy" to mutableListOf<Story>(),
                        "History" to mutableListOf<Story>(),
                        "Medieval" to mutableListOf<Story>(),
                        "Pirate" to mutableListOf<Story>(),
                        "Horror" to mutableListOf<Story>(),
                        "Science-Fiction" to mutableListOf<Story>(),
                        "Post-Apocalyptic" to mutableListOf<Story>(),
                        "Policier" to mutableListOf<Story>()
                    )

                    listOfStoryTypes.forEach { type ->

                        val url = "http://10.0.2.2:8000/api/story/tag?tag=$type"

                        // Récupération de la liste d'histoires correspondant au type
                        val request = JsonObjectRequest(
                            Request.Method.GET, url, null,
                            { response ->
                                // Parsing des données récupérées
                                for (i in 0 until response.getJSONArray("story").length()) {
                                    val item = response.getJSONArray("story").getJSONObject(i)
                                    mappedStories[type]?.add(
                                        Story(
                                            item.getInt("idStory"),
                                            item.getString("title"),
                                            item.getString("description"),
                                            item.getString("urlFolder"),
                                            item.getString("urlIcon"),
                                            item.getDouble("price").toFloat(),
                                            item.getString("author"),
                                            item.getInt("nbDownload")
                                        )
                                    )
                                }
                            },
                            { error ->
                                Log.i("ERROR", error.toString())
                            }
                        )
                        queue.add(request)
                    }
                    // pass data to adapter and hide the previous view
                    Handler().postDelayed(
                        Runnable {
                            val typeStoryMarketPlaceAdapter = ListAdapterStoryTypeMarketPlace(this, listOfStoryTypes, mappedStories)
                            binding.marketStoriesContainer.adapter = typeStoryMarketPlaceAdapter
                            binding.storiesContainer.visibility = View.GONE
                            binding.marketPlaceContainer.visibility = View.VISIBLE
                        },
                        2000
                    )
                }
            }
        }

        val talesFolder = getExternalFilesDir("Tales")
        val files = talesFolder?.listFiles()
        for (taleDirectory in files!!) {
            if (taleDirectory.isDirectory) {
                val dataFile = File("${taleDirectory.path}/data.json")
                if (dataFile.exists()) {
                    val story = GsonManager().dataReader(taleDirectory.name, taleDirectory.path + "/icon.png", dataFile.readText(Charsets.UTF_8))
                    if (story != null) {
                        ownedStoryList.add(story)
                    }
                }
            }
        }

        binding.viewPager.adapter = PagerAdapterMainActivity(this, ownedStoryList)

        binding.viewPager.pageMargin = 50
        binding.viewPager.setPadding(80, 0, 80, 0)
        binding.viewPager.clipToPadding = false

        // binding.marketStoriesContainer.adapter = ListAdapterStoryTypeMarketPlace(this, listOfStoryTypes)

        binding.headerProfileIcon.setOnClickListener {
            val intent = Intent(this, ConnexionActivity::class.java)
            startActivity(intent)
        }
    }
}
