package com.example.minstalesapp.Model

data class Story(
    var id : Int,
    var title : String,
    var description : String,
    var urlContentStory : String,
    var icon : String,
    var price : Float,
    var nbDownload : Int
)