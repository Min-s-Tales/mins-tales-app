package com.example.minstalesapp.MainPage

import android.app.Activity
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.minstalesapp.Model.Story
import com.example.minstalesapp.R
import com.example.minstalesapp.databinding.ItemStoryMarketplaceBinding
import java.net.URL

class ListAdapterPublishedStoryMarketPlace(
    private val context: Activity,
    private val listOfStory: Array<Story>
):ArrayAdapter<Story>(context, R.layout.item_story_marketplace, listOfStory) {

    private lateinit var binding: ItemStoryMarketplaceBinding

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        binding = ItemStoryMarketplaceBinding.inflate(inflater)
        val marketStoriesView = binding.root

        val bitmapImage = BitmapFactory.decodeStream(URL(listOfStory[position].url_icon).openConnection().getInputStream())
        binding.marketStoryIcon.background = BitmapDrawable(bitmapImage)
        binding.marketStoryTitle.text = listOfStory[position].title

        return marketStoriesView
    }
}