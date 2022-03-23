package com.example.minstalesapp.game

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.speech.RecognizerIntent
import android.util.Log
import android.webkit.CookieManager
import android.webkit.URLUtil
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.minstalesapp.DecompressFast
import com.example.minstalesapp.databinding.ActivityGameBinding
import java.io.*
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream


class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding

    private val url = "https://cdn.discordapp.com/attachments/900297093867008052/955809234958843914/demo.zip"

    private val TAG = "[GameActivity]"
    private val model: GameActivityViewModel by viewModels()
    private var text = ""
    private var storyID = 0L
    private var title = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        download()

        val launcher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK && result.data != null) {
                val data = result.data
                text = data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)!![0]
                binding.tvText.setText(text)

                if (text.lowercase().contains("bonjour")) {
                    Log.i(TAG, "onCreate: BONJOUR PASSÉ AVEC SUCCES")
                }
            }
        }

        binding.recordButton.setOnClickListener { view ->
            run {
                val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                intent.putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                )
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "$TAG Start speaking")

                try {
                    launcher.launch(intent)
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                    Toast.makeText(this, "Android version too old.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    /**
     * This function must be moved to the marketplace/adventures screen once finalized.
     */
    private fun download() {
        this.registerReceiver(attachmentDownloadCompleteReceive, IntentFilter(
                DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        )

        try{
            val request = DownloadManager.Request(Uri.parse(url))
            title = URLUtil.guessFileName(url, null, null)

            Log.i(TAG, "download: $title")
            request.setTitle(title)
            request.setDescription("Download File from URL, please wait...")
            try {
                val zipFile = File(this.getExternalFilesDir("Tales")!!.path + "/" + title)
                Log.v(TAG, "download existing: $zipFile.path")
                zipFile.delete()
            } catch (exeption: Exception) {
                exeption.printStackTrace()
            }

            val cookie = CookieManager.getInstance().getCookie(url)
            request.addRequestHeader("cookie", cookie)
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setDestinationInExternalFilesDir(this, "Tales", title)

            val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            storyID = downloadManager.enqueue(request)

            Toast.makeText(this, "Download start", Toast.LENGTH_SHORT).show()
            Log.i(TAG, "downloading")
        } catch (e: Exception) {
            Log.e(TAG, "download: failed")
            e.printStackTrace()
        }
    }

    /**
     * Attachment download complete receiver.
     *
     *
     * 1. Receiver gets called once attachment download completed.
     * 2. Open the downloaded file.
     */
    private var attachmentDownloadCompleteReceive: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (DownloadManager.ACTION_DOWNLOAD_COMPLETE == action) {
                val downloadId = intent.getLongExtra(
                    DownloadManager.EXTRA_DOWNLOAD_ID, 0
                )

                val zipFile = File(context.getExternalFilesDir("Tales"), title)
                Log.i(TAG, "onReceive: ${zipFile.path}")
                Toast.makeText(context, "Download done $downloadId", Toast.LENGTH_SHORT).show()

                DecompressFast().UnzipFile(zipFile, File(context.getExternalFilesDir("Tales")!!.path + "/" + title.replace(".zip", "/")), null)

                //DecompressFast(zipFile.path, context.getExternalFilesDir("Tales")!!.path + "/" + title.replace(".zip", "/")).unzip()
                //unpackZip(context.getExternalFilesDir("Tales")!!.path + "/" + title, title)
                //zipFile.delete()

                //openDownloadedAttachment(context, downloadId)
            }
        }
    }

    private fun unpackZip(path: String, zipname: String): Boolean {
        Log.i(TAG, "unpackZip: $path$zipname")
        val inputStream: InputStream
        val zis: ZipInputStream
        try {
            var filename: String
            inputStream = FileInputStream("$path/$zipname")
            zis = ZipInputStream(BufferedInputStream(inputStream))
            var ze: ZipEntry
            val buffer = ByteArray(1024)
            var count: Int
            while (zis.getNextEntry().also { ze = it } != null) {
                filename = ze.getName()

                // Need to create directories if not exists, or
                // it will generate an Exception...
                if (ze.isDirectory()) {
                    val fmd = File(path + filename)
                    fmd.mkdirs()
                    continue
                }
                val fout = FileOutputStream(path + filename)
                while (zis.read(buffer).also { count = it } != -1) {
                    fout.write(buffer, 0, count)
                }
                fout.close()
                zis.closeEntry()
            }
            zis.close()
        } catch (e: IOException) {
            e.printStackTrace()
            return false
        }
        return true
    }
}