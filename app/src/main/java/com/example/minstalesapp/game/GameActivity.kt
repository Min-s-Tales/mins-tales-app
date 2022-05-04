package com.example.minstalesapp.game

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
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
import java.util.regex.Pattern


class GameActivity : AppCompatActivity() {

    lateinit var binding: ActivityGameBinding
    val soundManager = SoundManager()
    val gsonManager = GsonManager()

    private val url = "https://steelroad.fr/minstales/story1.zip"

    private val TAG = "[GameActivity]"
    private val model: GameActivityViewModel by viewModels()
    private var text = ""
    private var storyID = 0L
    var gameTitle = ""
    private lateinit var jsonURI : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        val view = binding.root
        gameTitle = intent.getStringExtra("title")!!.lowercase().replace(" ", "_").replace("é", "e").replace("è", "e").replace("à", "a")
        jsonURI = Uri.parse(getExternalFilesDir("Tales")!!.path + "/" + gameTitle + "/assets/config.json")
        setContentView(view)
        println(jsonURI.path)
        //download()
        soundManager.init()
        gsonManager.init(jsonURI)

        binding.record.isEnabled = false
        binding.audioTitle.text = gameTitle


        for ((output, map) in gsonManager.gsonStartSound(this, gameTitle, "start")) {
            for ((title, sound) in map) {
                soundManager.outputSounds[output]?.addSound(title, sound)
                soundManager.outputSounds[output]?.playSound(sound)
            }
        }
        val answersMap = gsonManager.gsonCheckActionPath("start")

        val launcher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK && result.data != null) {
                val data = result.data
                text = data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)!![0]
                binding.speech.text = text
                for ((key, value) in answersMap) {
                    val array = value.split(" ")
                    Log.i(TAG, "${key} -> ${value} : ${containsWordsPatternMatch(text, array.toTypedArray())}")
                }
            }
        }

        binding.record.setOnClickListener { view ->
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
                    //e.printStackTrace()
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
            gameTitle = URLUtil.guessFileName(url, null, null)

            request.setTitle(gameTitle)
            request.setDescription("Download File from URL, please wait...")
            try {
                val zipFile = File(this.getExternalFilesDir("Tales")!!.path + "/" + gameTitle)
                Log.v(TAG, "download existing: $zipFile.path")
                zipFile.delete()
            } catch (exeption: Exception) {
                exeption.printStackTrace()
            }

            val cookie = CookieManager.getInstance().getCookie(url)
            request.addRequestHeader("cookie", cookie)
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setDestinationInExternalFilesDir(this, "Tales", gameTitle)

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

                val zipFile = File(context.getExternalFilesDir("Tales"), gameTitle)
                Log.i(TAG, "onReceive: ${zipFile.path}")
                Toast.makeText(context, "Download done $downloadId", Toast.LENGTH_SHORT).show()

                DecompressFast().UnzipFile(zipFile, File(context.getExternalFilesDir("Tales")!!.path + "/" + gameTitle.replace(".zip", "/")), null)


                //DecompressFast(zipFile.path, context.getExternalFilesDir("Tales")!!.path + "/" + title.replace(".zip", "/")).unzip()
                //unpackZip(context.getExternalFilesDir("Tales")!!.path + "/" + title, title)
                //zipFile.delete()

                //openDownloadedAttachment(context, downloadId)
            }
        }
    }

    private fun containsWordsPatternMatch(inputString: String, words: Array<String?>): Boolean {
        val regexp = StringBuilder()
        for (word in words) {
            regexp.append("(?=.*").append(word).append(")")
        }
        val pattern: Pattern = Pattern.compile(regexp.toString())
        return pattern.matcher(inputString).find()
    }

    override fun onDestroy() {
        super.onDestroy()
        soundManager.stopAll()
    }
}