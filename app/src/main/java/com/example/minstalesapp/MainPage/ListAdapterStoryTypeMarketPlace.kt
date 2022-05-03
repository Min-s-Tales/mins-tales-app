package com.example.minstalesapp.MainPage

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.minstalesapp.Model.Story
import com.example.minstalesapp.R
import com.example.minstalesapp.databinding.ItemTypeMarketplaceBinding

class ListAdapterStoryTypeMarketPlace(
    private val context: Activity,
    private val storyTypes: Array<String>
):ArrayAdapter<String>(context, R.layout.item_type_marketplace, storyTypes) {

    private lateinit var binding: ItemTypeMarketplaceBinding

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        binding = ItemTypeMarketplaceBinding.inflate(inflater)
        val marketTypeStoryView = binding.root

        //Récupération de la liste d'histoires correspondant au filtre[position]

        //Affectation de la liste à l'adapter et arrêt de la barre d'attente
        //binding.loadingBarMarket.visibility = View.GONE
        //binding.listStoriesMarket.adapter = ListAdapterPublishedStoryMarketPlace()

        binding.typeTitle.text = storyTypes[position]

        return marketTypeStoryView
    }
}