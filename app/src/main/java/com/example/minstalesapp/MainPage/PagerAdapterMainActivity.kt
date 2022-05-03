package com.example.minstalesapp.MainPage

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.example.minstalesapp.Model.Story
import com.example.minstalesapp.R
import com.example.minstalesapp.game.GameActivity

class PagerAdapterMainActivity(private val mContext: Context, private val storyList: ArrayList<Story>) : PagerAdapter() {

    lateinit var inflater: LayoutInflater

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        inflater = LayoutInflater.from(mContext)
        val view = inflater.inflate(R.layout.fragment_activity_main, container, false)

        var cardTitle : TextView = view.findViewById(R.id.cardStoryTitle)
        cardTitle.text = storyList[position].title

        var cardSynopsis : TextView = view.findViewById(R.id.cardStorySynopsis)
        cardSynopsis.text = storyList[position].description

        var cardDuration : TextView = view.findViewById(R.id.cardStoryDuration)
        cardDuration.text = "~ 20 min"

        val playButton : ImageView = view.findViewById(R.id.cardStoryPlayButton)
        playButton.setOnClickListener {
            val intent = Intent(mContext, GameActivity::class.java)
            intent.putExtra("audioId", storyList[position].url_folder)
            intent.putExtra("title", storyList[position].title)
            view.context.startActivity(intent)
        }

        container.addView(view, position)
        return view
    }

    override fun getCount(): Int {
        return storyList.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view === obj
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val view = `object` as View
        container.removeView(view)
    }
}