package com.example.minstalesapp.game

import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import com.example.minstalesapp.Model.Story
import com.example.minstalesapp.Stringifier
import org.json.JSONException
import org.json.JSONObject
import java.io.File

class GsonManager() {
    private val TAG = "[GsonManager]"
    private lateinit var file: File
    private lateinit var stringJson : String

    fun init(jsonURI: Uri) {
        file = File(jsonURI.path!!)
        stringJson = Stringifier().getStringFromFile(file.path)
    }

    fun gsonChecker(path: String): JSONObject? {
        try {
            val obj = JSONObject(stringJson)
            val m_jArry: JSONObject = obj.getJSONObject(path)
            Log.i(TAG, "gsonCheckerObject: m$m_jArry")
            return m_jArry
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return null
    }

    fun gsonGetSave(): String? {
        var value: String? = null
        try {
            val obj = JSONObject(stringJson)
            if (obj.has("save")) {
                value = obj.getString("save")
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return value
    }
    /**
     * Get the infos of config.json, at "start" object then prepare and returns the sounds stocked in it
     */
    fun gsonStartSound(activity: GameActivity, gameTitle: String, step: String) : HashMap<Outputs, HashMap<String, MediaPlayer>> {
        val map = HashMap<Outputs, HashMap<String, MediaPlayer>>()
        //Log.i(TAG, "gsonChecker: $stringJson")
        try {
            val obj = JSONObject(stringJson)
            var m_jArry: JSONObject = obj.getJSONObject(step)
            m_jArry = m_jArry.getJSONObject("start-sound")
            for (item in m_jArry.keys()) {
                val itemContent = m_jArry.getJSONObject(item)
                val src = itemContent.getString("src")
                val out = itemContent.getString("out")
                val loop = if (!itemContent.isNull("loop")) {
                    itemContent.getBoolean("loop")
                } else {
                    false
                }
                val output = Outputs.valueOf(out.uppercase())
                val mediaPlayerHashMap : HashMap<String, MediaPlayer>
                if (!map.containsKey(output)) {
                    mediaPlayerHashMap = HashMap()
                    map[output] = mediaPlayerHashMap
                } else {
                    mediaPlayerHashMap = map[output]!!
                }
                SoundManager().prepareSound(activity, gameTitle, src, out, loop)
                    ?.let { mediaPlayerHashMap.put(gameTitle, it) }
            }

        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return map
    }

    /**
     * Get the possible actions and their needed keywords of a path
     */
    fun gsonCheckActionPath(step: String) : HashMap<String, String> {
        val map = HashMap<String, String>()
        //Log.i(TAG, "gsonChecker: $stringJson")
        try {
            val obj = JSONObject(stringJson)
            var m_jArry: JSONObject = obj.getJSONObject(step)
            m_jArry = m_jArry.getJSONObject("actions")
            if (!m_jArry.isNull("paths")) {
                val jsonPaths = m_jArry.getJSONObject("paths")
                for (item in jsonPaths.keys()) {
                    map[item] = jsonPaths.get(item).toString()
                }
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return map
    }

    /**
     * Read the data.json of the tale, creates the story and returns it
     */
    fun dataReader(titleID: String, urlIconPath: String, jsonString: String) : Story? {
        println("title ID -> " + titleID)
        try {
            val obj = JSONObject(jsonString)
            val title = obj.getString("title")
            val author = obj.getString("author")
            val version = obj.getString("version")
            val desc = obj.getString("sypnosis")
            val themes = ArrayList<String>()
            val themesJson: JSONObject = obj.getJSONObject("themes")
            for (item in themesJson.keys()) {
                themes.add(themesJson.getString(item))
            }
            println("title display -> " + title)

            return Story(0, title, desc, "", urlIconPath, 0F, "test", 8)
        } catch (e: Exception) {
         Log.e(TAG, "The folder does not contains a valid data.json file.")
        }
        return null
    }
}