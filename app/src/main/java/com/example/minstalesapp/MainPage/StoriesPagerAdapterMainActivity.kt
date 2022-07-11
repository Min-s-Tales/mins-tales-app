package com.example.minstalesapp.MainPage

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.example.minstalesapp.Model.Story
import com.example.minstalesapp.R
import com.example.minstalesapp.game.GameActivity
import com.example.minstalesapp.filemanagers.GsonManager

class StoriesPagerAdapterMainActivity(
    //private val mContext: Activity,
    private val storyList: ArrayList<Story>
    ) : PagerAdapter() {

    lateinit var inflater: LayoutInflater
    val dataGsonManager = GsonManager()

    override fun instantiateItem(parent: ViewGroup, position: Int): Any {
        inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.fragment_activity_main_story, parent, false)

        val neutralizedGameTitle = storyList[position].title.lowercase().replace(" ", "_").replace("é", "e").replace("è", "e").replace("à", "a")

        val taleURI = Uri.parse("${Environment.getExternalStorageDirectory()!!.path}/Android/data/com.example.minstalesapp/files/Tales/$neutralizedGameTitle/")
        val dataURI =  Uri.parse(taleURI.toString() + "data.json")
        dataGsonManager.init(dataURI)
        val saveString = dataGsonManager.gsonGetSave()

        val continueButton: Button = view.findViewById(R.id.continueStoryButton)
        continueButton.isEnabled = saveString != null

        continueButton.setOnClickListener {
            val intent = Intent(parent.context, GameActivity::class.java)
            intent.putExtra("audioId", storyList[position].url_folder)
            intent.putExtra("title", neutralizedGameTitle)
            intent.putExtra("continue", true)
            view.context.startActivity(intent)
        }

        val cardTitle: TextView = view.findViewById(R.id.cardStoryTitle)
        cardTitle.text = storyList[position].title

        val cardSynopsis: TextView = view.findViewById(R.id.cardStorySynopsis)
        cardSynopsis.text = storyList[position].description

        //var cardDuration: TextView = view.findViewById(R.id.cardStoryDuration)
        //cardDuration.text = "~ 20 min"


        val cardIcon: ImageView = view.findViewById(R.id.cardStoryPicture)
        val bitmapImage: Bitmap? = BitmapFactory.decodeFile(storyList[position].url_icon)
        cardIcon.setImageBitmap(bitmapImage)

        val playButton: Button = view.findViewById(R.id.cardStoryPlayButton)
        playButton.setOnClickListener {
            val intent = Intent(parent.context, GameActivity::class.java)
            intent.putExtra("audioId", storyList[position].url_folder)
            intent.putExtra("title", neutralizedGameTitle)
            view.context.startActivity(intent)
        }

        parent.addView(view, position)
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
