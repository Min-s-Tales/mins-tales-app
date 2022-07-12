package com.example.minstalesapp.Api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiHelper {

    private const val baseUrl = "http://51.38.38.39:8000/"

    fun getStoriesFromTypes(type: String): String {
        return "${baseUrl}api/story/tag?tag=$type"
    }

    const val logUser = "${baseUrl}api/user/login"
    const val registerUser = "${baseUrl}user/register"

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
