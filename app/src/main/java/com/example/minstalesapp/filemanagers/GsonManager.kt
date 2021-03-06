package com.example.minstalesapp.filemanagers

import android.media.MediaPlayer
import android.net.Uri
import android.util.Log
import com.example.minstalesapp.Model.Story
import com.example.minstalesapp.Stringifier
import com.example.minstalesapp.game.GameActivity
import com.example.minstalesapp.game.Outputs
import com.example.minstalesapp.game.SoundManager
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.FileWriter
import java.io.PrintWriter


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
            return m_jArry
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return null
    }

    fun gsonGetSave(): String? {
        try {
            val obj = JSONObject(stringJson)
            if (obj.has("save")) {
                return obj.getString("save")
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return null
    }

    fun gsonGetOption(option: String): String? {
        try {
            val obj = JSONObject(stringJson)
            if (obj.has(option)) {
                return obj.getString(option)
            }
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return null
    }

    fun gsonSetSave(value: String?) : Boolean {
        val out = PrintWriter(FileWriter(file.path))
        try {
            val obj = JSONObject(stringJson)
            obj.put("save", value)

            Log.i(TAG, obj.toString())

            out.write(obj.toString()) //CORRUPTS DATA.JSON
            //Log.i(TAG, out.toString())
            return true
        } catch (e: JSONException) {
            e.printStackTrace()
        } finally {
            out.close()
        }
        return false
    }

    fun gsonSetOption(option: String?, value: String?) : Boolean {
        val out = PrintWriter(FileWriter(file.path))
        try {
            val obj = JSONObject(stringJson)
            obj.put(option, value)

            Log.i(TAG, obj.toString())

            out.write(obj.toString()) //CORRUPTS DATA.JSON
            //Log.i(TAG, out.toString())
            return true
        } catch (e: JSONException) {
            e.printStackTrace()
        } finally {
            out.close()
        }
        return false
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
    fun gsonCheckActionPath(step: String) : HashMap<String, HashMap<String, String>> {
        val map = HashMap<String, HashMap<String, String>>()
        //Log.i(TAG, "gsonChecker: $stringJson")
        try {
            val obj = JSONObject(stringJson)
            var m_jArry: JSONObject = obj.getJSONObject(step)
            m_jArry = m_jArry.getJSONObject("actions")
            if (!m_jArry.isNull("paths")) {
                val jsonPaths = m_jArry.getJSONObject("paths")
                for (item in jsonPaths.keys()) {
                    val pathValues = HashMap<String, String>()
                    val subJsonPath = jsonPaths.getJSONObject(item)
                    Log.i(TAG, "gsonCheckActionPath: $subJsonPath")
                    for (subItem in subJsonPath.keys()) {
                        pathValues[subItem] = subJsonPath.getString(subItem)
                        Log.i(TAG, "gsonCheckActionPath: ${subJsonPath.getString(subItem)}")
                    }
                    //map[item] = jsonPaths.get(item).toString()
                    map[item] = pathValues
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
            val display = obj.getString("title")
            val author = obj.getString("author")
            val version = obj.getString("version")
            val desc = obj.getString("sypnosis")
            val themes = ArrayList<String>()
            val themesJson: JSONObject = obj.getJSONObject("themes")
            for (item in themesJson.keys()) {
                themes.add(themesJson.getString(item))
            }
            println("title display -> $titleID + $display")

            return Story(0, titleID, display, desc, "", urlIconPath, 0F, "test", 8)
        } catch (e: Exception) {
         Log.e(TAG, "The folder does not contains a valid data.json file.")
        }
        return null
    }
}