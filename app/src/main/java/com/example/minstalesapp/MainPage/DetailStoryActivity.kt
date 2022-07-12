package com.example.minstalesapp.MainPage

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.webkit.CookieManager
import android.webkit.URLUtil
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.minstalesapp.SplashScreen.UnzipUtil
import com.example.minstalesapp.databinding.ActivityDetailStoryBinding
import com.squareup.picasso.Picasso
import java.io.File

class DetailStoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailStoryBinding
    private var storyID = 0L
    lateinit var gameTitle: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.storyTitle.text = intent.getStringExtra("itemTitle")
        binding.storyDesciption.text = intent.getStringExtra("itemDescription")
        binding.storyAuthor.text = intent.getStringExtra("itemAuthor")
        binding.storyNbDownload.text = "Downloaded ${intent.getIntExtra("itemNbDownload",0)} times"
        binding.storyPrice.text = "${intent.getFloatExtra("itemPrice", 0F)} €"
        binding.buyButton.setOnClickListener {
            intent.getStringExtra("itemUrlFolder")?.let { it1 -> download(it1) }
        }

        Picasso.get().load(intent.getStringExtra("itemIcon")).into(binding.storyIcon)

        binding.backButton.setOnClickListener {
            this.finish()
        }
    }

    private fun download(url: String) {

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
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_HIDDEN)
            try {
                val zipFile = File(this.getExternalFilesDir("Tales")!!.path + "/" + gameTitle)

                Log.v("DetailStoryActivity", "download existing: $zipFile.path")
                zipFile.delete()
            } catch (exeption: Exception) {
                exeption.printStackTrace()
            }

            val cookie = CookieManager.getInstance().getCookie(url)
            request.addRequestHeader("cookie", cookie)
            // request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setDestinationInExternalFilesDir(this, "Tales", gameTitle)

            val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            storyID = downloadManager.enqueue(request)

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
