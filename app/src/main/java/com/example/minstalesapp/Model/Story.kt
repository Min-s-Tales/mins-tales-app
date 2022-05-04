package com.example.minstalesapp.Model

data class Story (
    var id : Int,
    var titleID : String,
    var title : String,
    var description : String,
    var urlContentStory : Int,
    var icon : String,
    var price : Float,
    var nbDownload : Int
)