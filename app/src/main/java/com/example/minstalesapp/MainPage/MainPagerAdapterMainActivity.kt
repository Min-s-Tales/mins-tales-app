package com.example.minstalesapp.MainPage

import android.app.Activity
import android.content.Intent
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.minstalesapp.Model.Story
import com.example.minstalesapp.Profile.ConnexionActivity
import com.example.minstalesapp.R
import com.example.minstalesapp.databinding.FragmentActivityMainLibrairieBinding
import com.example.minstalesapp.databinding.FragmentActivityMainMarketplaceBinding
import com.example.minstalesapp.game.GsonManager
import java.io.File

class MainPagerAdapterMainActivity(
    private val mContext: Activity,
    private val idOfView: Array<Int>
    ) : PagerAdapter() {

    private lateinit var librairieBinding: FragmentActivityMainLibrairieBinding
    private lateinit var marketplaceBinding: FragmentActivityMainMarketplaceBinding
    var mappedStories = mapOf<String, MutableList<Story>>()
    var ownedStoryList = ArrayList<Story>()
    val listOfStoryTypes = arrayOf("Fantasy", "History", "Medieval", "Pirate", "Horror", "Science-Fiction", "Post-Apocalyptic", "Policier")

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val inflater = mContext.layoutInflater

        Log.i("id of view to display", idOfView[position].toString())

        if(idOfView[position] == R.layout.fragment_activity_main_librairie){
            Log.i("test", "librairie")

            librairieBinding = FragmentActivityMainLibrairieBinding.inflate(inflater)
            val librarieView = librairieBinding.root

            val folder = mContext.getExternalFilesDir("Tales")
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

            librairieBinding.storiesViewPager.adapter = StoriesPagerAdapterMainActivity(mContext, ownedStoryList)

            librairieBinding.storiesViewPager.pageMargin = 50
            librairieBinding.storiesViewPager.setPadding(80, 0, 80, 0);
            librairieBinding.storiesViewPager.clipToPadding = false

            librairieBinding.headerProfileIcon.setOnClickListener {
                val intent = Intent(mContext, ConnexionActivity::class.java)
                mContext.startActivity(intent)
            }

            return librarieView
        }else{
            Log.i("test", "market place")

            marketplaceBinding = FragmentActivityMainMarketplaceBinding.inflate(inflater)
            val marketplaceView = marketplaceBinding.root

            val queue = Volley.newRequestQueue(mContext)

            mappedStories = mapOf(
                "Fantasy" to mutableListOf<Story>(),
                "History" to mutableListOf<Story>(),
                "Medieval" to mutableListOf<Story>(),
                "Pirate" to mutableListOf<Story>(),
                "Horror" to mutableListOf<Story>(),
                "Science-Fiction" to mutableListOf<Story>(),
                "Post-Apocalyptic" to mutableListOf<Story>(),
                "Policier" to mutableListOf<Story>()
            )

            listOfStoryTypes.forEach { type ->

                val url = "http://10.0.2.2:8000/api/story/tag?tag=$type"

                //Récupération de la liste d'histoires correspondant au type
                val request = JsonObjectRequest(
                    Request.Method.GET, url, null,
                    { response ->
                        //Parsing des données récupérées
                        for (i in 0 until response.getJSONArray("story").length()) {
                            val item = response.getJSONArray("story").getJSONObject(i)
                            mappedStories[type]?.add(Story(
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
                    },
                    { error ->
                        Log.i("ERROR", error.toString())
                    }
                )
                queue.add(request)
            }
            //pass data to adapter and hide the previous view
            Handler().postDelayed(Runnable {
                marketplaceBinding.marketStoriesContainer.adapter = ListAdapterStoryTypeMarketPlace(mContext, listOfStoryTypes, mappedStories)
            }, 2000)

            return marketplaceView
        }
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
}