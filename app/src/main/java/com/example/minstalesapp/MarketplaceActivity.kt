package com.example.minstales

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.minstalesapp.R
import kotlinx.android.synthetic.main.activity_marketplace.*

class MarketplaceActivity : AppCompatActivity() {

    val listIcon = arrayOf(
        R.mipmap.box1,
        R.mipmap.box2,
        R.mipmap.eye1,
        R.mipmap.eye2,
    )

    val listTitle = arrayOf(
        "Checked box",
        "Unchecked box",
        "Opened eye",
        "Closed eye",
    )

    val listPrice = arrayOf(
        "$1.99",
        "$2.99",
        "$2.99",
        "$3.99",
    )

    val listDesc = arrayOf(
        "This is a nice checked box",
        "An unchecked box that whould appreciate to be clicked",
        "Les yeux d'un chat changent de couleur en grandissant",
        "Mes yeux quand je suis en face de Théo",
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marketplace)

        val marketplaceAdapter = MarketplaceAdapter(this, listIcon, listTitle, listPrice, listDesc)
        marketResults.adapter = marketplaceAdapter
    }
}