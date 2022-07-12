package com.example.minstalesapp.SplashScreen

import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

class UnzipUtil(private val zipFileString: String, private val location: String) {
    private val TAG = "UnzipUtil"
    fun unzip(): File? {
        try {
            val fin = FileInputStream(zipFileString)
            val zin = ZipInputStream(fin)
            var ze: ZipEntry?
            while (zin.nextEntry.also { ze = it } != null) {
                //Log.v("Decompress", "Unzipping " + ze!!.name)
                if (ze!!.isDirectory) {
                    dirChecker(ze!!.name)
                } else {
                    val dirLoc = StringBuilder(location)
                    for (i in 0 until ze!!.name.split("/").toTypedArray().size - 1) {
                        dirLoc.append(ze!!.name.split("/").toTypedArray()[i]).append("/")
                        val file = File(dirLoc.toString())
                        //Log.i(TAG, "unzip: " + file.absolutePath)
                        if (!file.exists()) {
                            Log.i(TAG, "create " + file.mkdir())
                        }
                    }
                    val fout = FileOutputStream(location + ze!!.name)
                    val buffer = ByteArray(8192)
                    var len: Int
                    while (zin.read(buffer).also { len = it } != -1) {
                        fout.write(buffer, 0, len)
                    }
                    fout.close()
                    zin.closeEntry()
                }
            }
            zin.close()
            return File(zipFileString.replace(".zip", "/"))
        } catch (e: Exception) {
            Log.e("Decompress", "unzip", e)
            return null
        }
    }

    private fun dirChecker(dir: String) {
        val f = File(location + dir)
        if (!f.isDirectory) {
            f.mkdirs()
        }
    }

    init {
        dirChecker("")
    }
}