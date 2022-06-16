import android.util.Log
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

object ZipManager {
    private const val BUFFER_SIZE = 6 * 1024
    @Throws(IOException::class)
    fun unzip(zipFile: String?, location: String) {
        try {
            val f = File(location)
            if (!f.isDirectory()) {
                f.mkdirs()
            }
            val zin = ZipInputStream(FileInputStream(zipFile))
            try {
                var ze: ZipEntry? = null
                while (zin.getNextEntry().also { ze = it } != null) {
                    val path = location + File.separator + ze!!.getName()
                    if (ze!!.isDirectory()) {
                        val unzipFile = File(path)
                        if (!unzipFile.isDirectory()) {
                            unzipFile.mkdirs()
                        }
                    } else {
                        val fout = FileOutputStream(path, false)
                        try {
                            var c: Int = zin.read()
                            while (c != -1) {
                                fout.write(c)
                                c = zin.read()
                            }
                            zin.closeEntry()
                        } finally {
                            fout.close()
                        }
                    }
                }
            } finally {
                zin.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("UNZIPUTILS", "Unzip exception", e)
        }
    }
}