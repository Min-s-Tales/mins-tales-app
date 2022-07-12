package com.example.minstalesapp.filemanagers

import android.net.Uri
import android.os.Environment
import java.io.File

object ConfigManager {
    private val configURI: Uri = Uri.parse("${Environment.getExternalStorageDirectory()!!.path}/Android/data/com.example.minstalesapp/files/config.json")
    private val configFile = File(configURI.path!!)
    private val gsonManager = GsonManager()
    var narratorVolume = 1f
    var ambianceVolume = 1f

    init {
        println("ConfigManager class invoked.")
        if (!configFile.exists()) {
            configFile.createNewFile()
        }
        gsonManager.init(configURI)
        if (gsonManager.gsonGetOption("narratorVolume") != null) {
            narratorVolume = gsonManager.gsonGetOption("narratorVolume")!!.toFloat()
        }
        if (gsonManager.gsonGetOption("ambienceVolume") != null) {
            ambianceVolume = gsonManager.gsonGetOption("ambienceVolume")!!.toFloat()
        }
    }

    fun editNarratorVolume(vol: Float) {
        narratorVolume = vol
        gsonManager.gsonSetOption("narratorVolume", vol.toString())
    }

    fun editAmbience(vol: Float) {
        ambianceVolume = vol
        gsonManager.gsonSetOption("ambienceVolume", vol.toString())
    }
}
