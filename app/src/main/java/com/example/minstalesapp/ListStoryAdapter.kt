package com.example.minstalesapp

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.item_story_view.view.*

class ListStoryAdapter(
    private val context: Activity,
    private val icon: Array<Int>,
    private val title: Array<String>,
    private val desc: Array<String>,
):ArrayAdapter<String>(context, R.layout.item_story_view, title) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val storyItemView = inflater.inflate(R.layout.item_story_view, null, true)

        val iconView = storyItemView.findViewById(R.id.storyIcon) as ImageView
        val titleText = storyItemView.findViewById(R.id.storyTitle) as TextView
        val descriptionText = storyItemView.findViewById(R.id.storyDesciption) as TextView

        iconView.setImageResource(icon[position])
        titleText.text = title[position]
        descriptionText.text = desc[position]

        return storyItemView
    }
}