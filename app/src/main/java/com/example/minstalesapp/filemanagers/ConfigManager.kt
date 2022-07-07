package com.example.minstalesapp.filemanagers

import android.net.Uri
import android.os.Environment
import android.util.Log
import java.io.File

object ConfigManager {
    private val configURI: Uri = Uri.parse("${Environment.getExternalStorageDirectory()!!.path}/Android/data/com.example.minstalesapp/files/config.json")
    private val configFile = File(configURI.path!!)
    private val gsonManager = GsonManager()

    init {
        println("ConfigManager class invoked.")
        if (!configFile.exists()) {
            configFile.createNewFile()
        }
    }

    fun test() {
        Log.i("ConfigManager", "test: OH AH")
    }
}