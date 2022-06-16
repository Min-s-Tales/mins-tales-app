package com.example.minstalesapp

import android.net.Uri
import java.io.*

class Stringifier {

    @Throws(Exception::class)
    fun convertStreamToString(`is`: InputStream): String {
        val reader = BufferedReader(InputStreamReader(`is`))
        val sb = StringBuilder()
        var line: String? = null
        while (reader.readLine().also { line = it } != null) {
            sb.append(line).append("\n")
        }
        reader.close()
        return sb.toString()
    }

    @Throws(Exception::class)
    fun getStringFromFile(filePath: String): String {
        val fl = File(filePath)
        val fin = FileInputStream(fl)
        val ret = convertStreamToString(fin)
        // Make sure you close all streams.
        fin.close()
        return ret
    }

    @Throws(Exception::class)
    fun setStringToFile(fileUri: Uri, value: String) {
        //Will add the String step to data.json
    }
}
