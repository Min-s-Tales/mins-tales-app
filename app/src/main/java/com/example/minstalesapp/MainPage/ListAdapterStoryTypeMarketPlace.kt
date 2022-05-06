package com.example.minstalesapp.MainPage

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.minstalesapp.Model.Story
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

        //val url = "http://10.0.2.2:8000/api/story/tag?tag="+storyTypes[position]
        //var storiesByType: MutableList<Story> = mutableListOf()

        //Récupération de la liste d'histoires correspondant au filtre[position]
        /*
        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                Log.i("Tag", storyTypes[position])
                Log.i("SUCCESS", response.getJSONArray("story").toString())

                //Parsing des données récupérées
                for (i in 0 until response.getJSONArray("story").length()) {
                    val item = response.getJSONArray("story").getJSONObject(i)
                    storiesByType.add(Story(
                        item.getInt("idStory"),
                        item.getString("title"),
                        item.getString("description"),
                        item.getString("urlFolder"),
                        item.getString("urlIcon"),
                        item.getDouble("price").toFloat(),
                        item.getString("author"),
                        item.getInt("nbDownload")
                    ))
                }

                //Affectation de la liste à l'adapter et arrêt de la barre d'attente
                binding.loadingBarMarket.visibility = View.GONE
                binding.listStoriesMarket.adapter = ListAdapterPublishedStoryMarketPlace(context, storiesByType)
            },
            { error ->
                Log.i("ERROR", error.toString())
            }
        )
        queue.add(request)
         */

        binding.loadingBarMarket.visibility = View.GONE
        binding.listStoriesMarket.adapter = ListAdapterPublishedStoryMarketPlace(context, mappedStories[type]!!)

        return marketTypeStoryView
    }
}