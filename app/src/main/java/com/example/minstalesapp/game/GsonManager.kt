package com.example.minstalesapp.game

import android.net.Uri
import android.util.Log
import com.example.minstalesapp.Stringifier
import org.json.JSONException
import org.json.JSONObject
import java.io.File

class GsonManager(gameActivity: GameActivity) {
    private val gameActivity : GameActivity = gameActivity
    private val TAG = "[GsonManager]"
    private lateinit var file: File
    private lateinit var stringJson : String

    fun init(jsonURI: Uri) {
        file = File(jsonURI.path!!)
        stringJson = Stringifier().getStringFromFile(file.path)
    }

    fun gsonChecker(path: String): JSONObject? {
        //Log.i(TAG, "gsonChecker: $stringJson")
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

    fun gsonStartSound(step: String) {
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
                gameActivity.soundManager.prepareSound(gameActivity.gameTitle, src, out, loop)
                Log.i(TAG, "$src $out $loop")
            }

        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

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
}