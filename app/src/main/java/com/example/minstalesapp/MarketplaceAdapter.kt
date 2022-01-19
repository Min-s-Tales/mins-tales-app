package com.example.minstalesapp

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.minstalesapp.R

class MarketplaceAdapter(
    private val context: Activity,
    private val icon: Array<Int>,
    private val title: Array<String>,
    private val price: Array<String>,
    private val desc: Array<String>,
):ArrayAdapter<String>(context, R.layout.item_marketplace_view, title) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val storyItemView = inflater.inflate(R.layout.item_marketplace_view, null, true)

        val iconView = storyItemView.findViewById(R.id.storyIcon) as ImageView
        val titleText = storyItemView.findViewById(R.id.storyTitle) as TextView
        val priceText = storyItemView.findViewById(R.id.priceText) as TextView
        val descriptionText = storyItemView.findViewById(R.id.storyDesciption) as TextView

        iconView.setImageResource(icon[position])
        priceText.text = price[position]
        titleText.text = title[position]
        descriptionText.text = desc[position]

        return storyItemView
    }
}