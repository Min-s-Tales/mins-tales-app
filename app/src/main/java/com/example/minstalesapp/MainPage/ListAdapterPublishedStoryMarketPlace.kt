package com.example.minstalesapp.MainPage

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.minstalesapp.Model.Story
import com.example.minstalesapp.databinding.ItemStoryMarketplaceBinding
import com.squareup.picasso.Picasso

class ListAdapterPublishedStoryMarketPlace(
    private val context: Activity,
    private val onItemClick: (item: Story?) -> Unit,
    private val listOfStory: MutableList<Story>
) : RecyclerView.Adapter<ListAdapterPublishedStoryMarketPlace.ViewHolder>() {

    private lateinit var binding: ItemStoryMarketplaceBinding

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            view.setOnClickListener {
                onItemClick.invoke(listOfStory[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = context.layoutInflater
        binding = ItemStoryMarketplaceBinding.inflate(inflater)
        val marketStoriesView = binding.root

        return ViewHolder(marketStoriesView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Picasso.get().load(listOfStory[position].url_icon).into(binding.marketStoryIcon)

        binding.marketStoryTitle.text = listOfStory[position].title
    }

    override fun getItemCount(): Int {
        return listOfStory.size
    }


}
