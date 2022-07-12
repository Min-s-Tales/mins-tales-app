package com.example.minstalesapp.MainPage

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.minstalesapp.databinding.ActivityDetailStoryBinding
import com.squareup.picasso.Picasso

class DetailStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.storyTitle.text = intent.getStringExtra("itemTitle")
        binding.storyDesciption.text = intent.getStringExtra("itemDescription")
        binding.storyAuthor.text = intent.getStringExtra("itemAuthor")
        binding.storyNbDownload.text = "Downloaded ${intent.getIntExtra("itemNbDownload",0)} times"
        binding.storyPrice.text = "${intent.getFloatExtra("itemPrice", 0F)} €"
        binding.buyButton.setOnClickListener {
        }

        Picasso.get().load(intent.getStringExtra("itemIcon")).into(binding.storyIcon)

        binding.backButton.setOnClickListener {
            this.finish()
        }
    }
}
