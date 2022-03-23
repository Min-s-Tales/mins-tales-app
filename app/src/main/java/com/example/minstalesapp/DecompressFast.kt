package com.example.minstalesapp

import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

class DecompressFast() {

    interface UnzipFile_Progress {
        fun Progress(percent: Int, FileName: String?)
    }

    // unzip(new File("/sdcard/pictures.zip"), new File("/sdcard"));
    //@Throws(IOException::class, FileNotFoundException::class)
    fun UnzipFile(zipFile: File, targetDirectory: File?, progress: UnzipFile_Progress?) {
        val total_len = zipFile.length()
        var total_installed_len: Long = 0
        val zis = ZipInputStream(BufferedInputStream(FileInputStream(zipFile)))
        try {
            var ze: ZipEntry
            var count: Int
            val buffer = ByteArray(1024)
            while (zis.nextEntry.also { ze = it } != null) {
                if (progress != null) {
                    total_installed_len += ze.compressedSize
                    val file_name = ze.name
                    val percent = (total_installed_len * 100 / total_len).toInt()
                    progress.Progress(percent, file_name)
                }
                val file = File(targetDirectory, ze.name)
                val dir = if (ze.isDirectory) file else file.parentFile
                if (!dir.isDirectory && !dir.mkdirs()) throw FileNotFoundException("Failed to ensure directory: " + dir.absolutePath)
                if (ze.isDirectory) continue
                val fout = FileOutputStream(file)
                try {
                    while (zis.read(buffer).also { count = it } != -1) fout.write(buffer, 0, count)
                } finally {
                    fout.close()
                }

                // if time should be restored as well
                val time = ze.time
                if (time > 0) file.setLastModified(time)
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
        } finally {
            zis.close()
        }
    }
}