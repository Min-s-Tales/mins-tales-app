package com.example.minstalesapp.Api

import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiHelper {

    const val baseUrl =
        //"https://127.0.0.1:8000/"
        "http://10.0.2.2:8000/"

    fun getInstance(): Retrofit {
        Log.i("ERROR COUCOU", "ntm")
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}