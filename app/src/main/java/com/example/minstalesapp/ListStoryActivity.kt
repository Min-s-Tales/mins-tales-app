package com.example.minstalesapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.list_story_view.*

class ListStoryActivity : AppCompatActivity() {

    val listIcon = arrayOf<Int>(
        R.mipmap.box1,
        R.mipmap.box2,
        R.mipmap.eye1,
        R.mipmap.eye2,
    )

    val listTitle = arrayOf<String>(
        "Checked box",
        "Unchecked box",
        "Opened eye",
        "Closed eye",
    )

    val listDesc = arrayOf<String>(
        "This is a nice checked box",
        "An unchecked box that whould appreciate to be clicked",
        "Les yeux d'un chat changent de couleur en grandissant",
        "Mes yeux quand je suis en face de Théo",
    )

    val listAudio = arrayOf<Int>(
        R.raw.guignol,
        R.raw.cartman,
        R.raw.tourdion,
        R.raw.soldier,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_story_view)

        val listStoryAdapter = ListStoryAdapter(this, listIcon, listTitle, listDesc)
        listStoryView.adapter = listStoryAdapter

        listStoryView.setOnItemClickListener { adapterView, view, position, id ->
            val intent = Intent(this, StoryPlayerActivity::class.java)
            intent.putExtra("audio", listAudio[position])
            startActivity(intent)
        }
    }
}