package com.example.minstalesapp.filemanagers

import android.net.Uri
import android.os.Environment
import android.util.Log
import com.example.minstalesapp.Model.Story
import java.io.File

object StoryManager {
    private val talesDirectoryURI: Uri = Uri.parse("${Environment.getExternalStorageDirectory()!!.path}/Android/data/com.example.minstalesapp/files/Tales/")
    private val talesDirectory = File(talesDirectoryURI.path!!)
    val storyList = ArrayList<Story>()
    private val TAG = "StoryManager"

    init {
        Log.i(TAG, ": Init Story Manager")
        if (!talesDirectory.exists()) {
            talesDirectory.mkdir()
        }

        talesDirectory.listFiles()?.forEach { file ->
            run {
                addStoryToList(file)
            }
        }
    }

    fun addStoryToList(taleDirectory: File) {
        if (taleDirectory.isDirectory) {
            val dataFile = File("${taleDirectory.path}/data.json")
            if (dataFile.exists()) {
                val story = GsonManager().dataReader(taleDirectory.name, taleDirectory.path+"/icon.png", dataFile.readText(Charsets.UTF_8))
                if (story != null) {
                    storyList.add(story)
                    Log.i(TAG, "New story : ${story.title}")
                }
            }
        }
    }
}