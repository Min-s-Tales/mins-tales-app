package com.example.minstalesapp.game

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.minstalesapp.databinding.ActivityGameBinding
import java.util.regex.Pattern


class GameActivity : AppCompatActivity() {

    lateinit var binding: ActivityGameBinding
    val soundManager = SoundManager()
    val gsonManager = GsonManager()

    private val TAG = "[GameActivity]"
    private val model: GameActivityViewModel by viewModels()
    private var text = ""
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
                    Log.i(TAG, "$key -> $value : ${containsWordsPatternMatch(text, array.toTypedArray())}")
                }
            }
        }

        binding.record.setOnClickListener { view ->
            run {
                val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                intent.putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM,
                )
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "$TAG Start speaking")
                intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                    this.packageName
                )

                try {
                    launcher.launch(intent)
                } catch (e: java.lang.Exception) {
                    //e.printStackTrace()
                    Toast.makeText(this, "Android version too old.", Toast.LENGTH_SHORT).show()
                }
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