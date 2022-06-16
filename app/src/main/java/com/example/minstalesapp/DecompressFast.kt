package com.example.minstalesapp

import android.util.Log
import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipFile
import java.util.zip.ZipInputStream
import javax.security.auth.login.LoginException

class DecompressFast() {

    interface UnzipFile_Progress {
        fun Progress(percent: Int, FileName: String?)
    }

    data class ZipIO (val entry: ZipEntry, val output: File)

    fun File.unzip(unzipLocationRoot: File? = null) {

        val rootFolder = unzipLocationRoot ?: File(parentFile.absolutePath + File.separator + nameWithoutExtension)
        if (!rootFolder.exists()) {
            rootFolder.mkdirs()
        }

        ZipFile(this).use { zip ->
            zip
                .entries()
                .asSequence()
                .map {
                    val outputFile = File(rootFolder.absolutePath + File.separator + it.name)
                    ZipIO(it, outputFile)
                }
                .map {
                    it.output.parentFile?.run{
                        if (!exists()) mkdirs()
                    }
                    it
                }
                .filter { !it.entry.isDirectory }
                .forEach { (entry, output) ->
                    zip.getInputStream(entry).use { input ->
                        output.outputStream().use { output ->
                            input.copyTo(output)
                        }
                    }
                }
        }

    }
}
