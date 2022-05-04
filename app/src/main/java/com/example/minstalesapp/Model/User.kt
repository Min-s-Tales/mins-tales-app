package com.example.minstalesapp.Model

import com.google.gson.Gson

data class User(
    var email : String,
    var username : String,
    var roles : Gson,
    var password : String,
)