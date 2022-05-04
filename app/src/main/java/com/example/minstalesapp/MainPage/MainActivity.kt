package com.example.minstalesapp.MainPage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.minstalesapp.Model.Story
import com.example.minstalesapp.ProfileActivity
import com.example.minstalesapp.R
import com.example.minstalesapp.databinding.ActivityMainBinding
import com.example.minstalesapp.game.GsonManager
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var ownedStoryList = ArrayList<Story>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.storiesToggleButton.isChecked = true

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

        binding.viewPager.adapter = PagerAdapterMainActivity(this, ownedStoryList)

        binding.viewPager.pageMargin = 50
        binding.viewPager.setPadding(80, 0, 80, 0);
        binding.viewPager.clipToPadding = false

        binding.headerProfileIcon.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }
}