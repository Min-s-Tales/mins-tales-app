package com.example.minstalesapp.MainPage

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ListView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.minstalesapp.Model.Story
import com.example.minstalesapp.Profile.ConnexionActivity
import com.example.minstalesapp.R
import com.example.minstalesapp.filemanagers.GsonManager
import java.io.File

class MainPagerAdapterMainActivity(
    private val mContext: Activity,
    private val idOfView: Array<Int>,
    private var mappedStories: Map<String, MutableList<Story>>,
    private val listOfStoryTypes: Array<String>
    ) : PagerAdapter() {

    //var mappedStories = mapOf<String, MutableList<Story>>()
    var ownedStoryList = ArrayList<Story>()
    //val listOfStoryTypes = arrayOf("Fantasy", "History", "Medieval", "Pirate", "Horror", "Science-Fiction", "Post-Apocalyptic", "Policier")

    override fun instantiateItem(parent: ViewGroup, position: Int): Any {


        Log.i("DATASTATE", "$mappedStories")

        if(idOfView[position] == R.layout.fragment_activity_main_librairie){

            // Get the view from pager page layout
            val librarieView = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_activity_main_librairie, parent,false)

            val folder = parent.context.getExternalFilesDir("Tales")
            val files = folder!!.listFiles()
            for (taleDirectory in files!!) {
                if (taleDirectory.isDirectory) {
                    val dataFile = File("${taleDirectory.path}/data.json")
                    if (dataFile.exists()) {
                        val story = GsonManager().dataReader(taleDirectory.name, taleDirectory.path+"/icon.png", dataFile.readText(Charsets.UTF_8))
                        if (story != null) {
                            ownedStoryList.add(story)
                        }
                    }
                }
            }

            // Get the widgets reference from layout
            val storiesViewPager: ViewPager = librarieView.findViewById(R.id.storiesViewPager)
            val headerProfileIcon: ImageView = librarieView.findViewById(R.id.headerProfileIcon)

            // Set content
            storiesViewPager.adapter = StoriesPagerAdapterMainActivity(ownedStoryList)

            storiesViewPager.pageMargin = 50
            storiesViewPager.setPadding(80, 0, 80, 0);
            storiesViewPager.clipToPadding = false

            headerProfileIcon.setOnClickListener {
                val intent = Intent(parent.context, ConnexionActivity::class.java)
                parent.context.startActivity(intent)
            }

            parent.addView(librarieView)
            return librarieView
        }

        else{

            // Get the view from pager page layout
            val marketplaceView = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_activity_main_marketplace, parent,false)

            // Get the widgets reference from layout
            val marketStoriesContainer: ListView = marketplaceView.findViewById(R.id.marketStoriesContainer)
            //pass data to adapter
            marketStoriesContainer.adapter = ListAdapterStoryTypeMarketPlace(mContext, listOfStoryTypes, mappedStories)

            parent.addView(marketplaceView)
            return marketplaceView
        }
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

    override fun getCount(): Int {
        return idOfView.count()
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view === obj
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val view = `object` as View
        container.removeView(view)
    }

    fun setListOfStory(map: Map<String, MutableList<Story>>){
        mappedStories = map
    }
}