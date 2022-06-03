package com.example.minstalesapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.minstalesapp.game.GameActivity
import kotlinx.android.synthetic.main.list_story_view.*

class ListStoryActivity : AppCompatActivity() {

    /*
    val listIcon = arrayOf<Int>(
        R.mipmap.tale,
        R.mipmap.moine,
        R.mipmap.key,
        R.mipmap.bern,
    )

    val listTitle = arrayOf<String>(
        "Les chroniqueurs de la Nalle",
        "L'habitacle du moine nymphomane",
        "La key quête",
        "L'incroyable roadtrip dans Montquc",
    )

    val listDesc = arrayOf<String>(
        "Suivez le périple des trois célèbres chroniqueurs originaire de la Nalle, à travers des histoires prenantes remplies de surprises",
        "Une étrange rencontre avec un moine aux manières suspicieuses va vous emporter dans une aventure relevée d'une série de rencontres, toutes plus cocaçe les une que les autres",
        "Jim, nouveau propriétaire du manoir Roinam est Obsédé par le mystère qui entoure l'ouverture de l'imposante porte bloquant l'entrée de la cave. Parviendrez-vous à résoudre ce mystère ?",
        "Vivez une expérience unique au côtés de Stephan Bern à travers l'incroyable roadtrip dans Montquc",
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
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("audioId", listAudio[position])
            intent.putExtra("title", listTitle[position])
            startActivity(intent)
        }
    }
     */
}