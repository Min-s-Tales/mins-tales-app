package com.example.minstalesapp.SplashScreen

import android.app.DownloadManager
import android.app.DownloadManager.Request.VISIBILITY_HIDDEN
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.webkit.CookieManager
import android.webkit.URLUtil
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.minstalesapp.MainPage.MainActivity
import com.example.minstalesapp.databinding.ActivitySplashScreenBinding
import java.io.File

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding

    val TAG = "DOWNLOAD"
    private var storyID = 0L
    lateinit var gameTitle: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler().postDelayed(
            Runnable {
                val intent = Intent(this, MainActivity::class.java)
                // download()
                startActivity(intent)
                this.finish()
            },
            3000
        )
    }

    private fun download() {
        val url = "https://steelroad.fr/minstales/exploiteurs_du_ciel.zip"

        this.registerReceiver(
            attachmentDownloadCompleteReceive,
            IntentFilter(
                DownloadManager.ACTION_DOWNLOAD_COMPLETE
            )
        )

        try {
            val request = DownloadManager.Request(Uri.parse(url))
            gameTitle = URLUtil.guessFileName(url, null, null)

            request.setTitle(gameTitle)
            request.setDescription("Downloading story, please wait")
            request.setNotificationVisibility(VISIBILITY_HIDDEN)
            try {
                val zipFile = File(this.getExternalFilesDir("Tales")!!.path + "/" + gameTitle)

                Log.v(TAG, "download existing: $zipFile.path")
                zipFile.delete()
            } catch (exeption: Exception) {
                exeption.printStackTrace()
            }

            val cookie = CookieManager.getInstance().getCookie(url)
            request.addRequestHeader("cookie", cookie)
            // request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setDestinationInExternalFilesDir(this, "Tales", gameTitle)

            // val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            //  storyID = downloadManager.enqueue(request)

            Toast.makeText(this, "Download start", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
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

                val zipFile = File(context.getExternalFilesDir("Tales"), gameTitle)
                Toast.makeText(context, "Download done $downloadId", Toast.LENGTH_SHORT).show()

                File(getExternalFilesDir("Tales"), gameTitle.replace(".zip", "")).mkdir()
                val unzipUtil = UnzipUtil(zipFile.path, zipFile.absolutePath.replace(".zip", "/"))
                unzipUtil.unzip()
                zipFile.delete()
            }
        }
    }
}
