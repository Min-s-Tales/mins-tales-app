package com.example.minstalesapp.MainPage

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.minstalesapp.databinding.ActivityDetailStoryBinding
import java.net.URL
import kotlin.concurrent.thread

class DetailStoryActivity: AppCompatActivity() {

    private lateinit var binding: ActivityDetailStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.storyTitle.text =  intent.getStringExtra("itemTitle")
        binding.storyDesciption.text =  intent.getStringExtra("itemDescription")
        binding.storyAuthor.text =  intent.getStringExtra("itemAuthor")
        binding.storyNbDownload.text = "Downloaded ${intent.getIntExtra("itemNbDownload",0)} times"
        binding.storyPrice.text = "${intent.getFloatExtra("itemPrice", 0F).toString()} €"

        //initialize the story image
        var bitmapImage: Bitmap? = null

        //get the image with the url
        thread {
            bitmapImage = BitmapFactory.decodeStream(URL(intent.getStringExtra("itemIcon")).openStream())
        }.join()

        //setting the image after the previous thread finished
        this.runOnUiThread(Runnable {
            binding.storyIcon.setImageBitmap(bitmapImage)
        })
    }
}