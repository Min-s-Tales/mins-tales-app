package com.example.minstalesapp.MainPage

import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.minstalesapp.Model.Story
import com.example.minstalesapp.Profile.ConnexionActivity
import com.example.minstalesapp.R
import com.example.minstalesapp.databinding.ItemTypeMarketplaceBinding

class ListAdapterStoryTypeMarketPlace(
    private val context: Activity,
    private val storyTypes: Array<String>,
    private val mappedStories: Map<String, MutableList<Story>>
):ArrayAdapter<String>(context, R.layout.item_type_marketplace, storyTypes) {

    private lateinit var binding: ItemTypeMarketplaceBinding

    //val queue = Volley.newRequestQueue(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        binding = ItemTypeMarketplaceBinding.inflate(inflater)
        val marketTypeStoryView = binding.root

        val type = storyTypes[position]

        binding.typeTitle.text = type
        binding.listStoriesMarket.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.loadingBarMarket.visibility = View.GONE
        binding.listStoriesMarket.adapter = ListAdapterPublishedStoryMarketPlace(context, { item ->
            val intent = Intent(context, DetailStoryActivity::class.java)
            if (item != null) {
                intent.putExtra("itemTitle", item.title)
                intent.putExtra("itemDescription", item.description)
                intent.putExtra("itemPrice", item.price)
                intent.putExtra("itemAuthor", item.author)
                intent.putExtra("itemNbDownload", item.nb_download)
                intent.putExtra("itemIcon", item.url_icon)
            }
            context.startActivity(intent)
        }, mappedStories[type]!!)

        return marketTypeStoryView
    }
}