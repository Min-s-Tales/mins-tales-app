package com.example.minstalesapp.Model

import com.google.gson.Gson

data class Story(
    var id: Int,
    var title: String,
    var description: String,
    var url_folder: Int,
    var url_icon: String,
    var price: Float,
    var id_autor: Int,
    var nb_download: Int
)
