package com.example.minstalesapp.game

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.minstalesapp.R
import com.example.minstalesapp.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {

    lateinit var binding: ActivityGameBinding
    val soundManager = SoundManager()
    val gsonManager = GsonManager()

    private val TAG = "[GameActivity]"
    private val model: GameActivityViewModel by viewModels()
    var text = ""
    var gameTitle = ""
    private lateinit var jsonURI : Uri
    private var jsonPath = "start"
    private lateinit var answersMap : HashMap<String, String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        val view = binding.root
        gameTitle = intent.getStringExtra("title")!!.lowercase().replace(" ", "_").replace("é", "e").replace("è", "e").replace("à", "a")
        jsonURI = Uri.parse(getExternalFilesDir("Tales")!!.path + "/" + gameTitle + "/assets/config.json")
        val extraJsonPath = intent.getStringExtra("starter");
        if (extraJsonPath != null) {
            jsonPath = extraJsonPath
        }
        setContentView(view)
        soundManager.init()
        gsonManager.init(jsonURI)

        binding.audioTitle.text = gameTitle

        nextStep(jsonPath)

        val launcher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK && result.data != null) {
                val data = result.data
                text = data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)!![0]
                binding.speech.text = text
                for ((key, value) in answersMap) {
                    val array = value.split(" ").toTypedArray().toCollection(ArrayList())
                    if (model.checkAllNeededWordsSpoken(array, text)) {
                        nextStep(key)
                        model.saveGame(key)
                        break
                    }
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
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.pronounce_action))
                intent.putExtra(
                    RecognizerIntent.EXTRA_CALLING_PACKAGE,
                    this.packageName
                )

                try {
                    launcher.launch(intent)
                } catch (e: java.lang.Exception) {
                    // e.printStackTrace()
                    Toast.makeText(this, getString(R.string.outdated_android_version), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun nextStep(path: String) {
        answersMap = gsonManager.gsonCheckActionPath(path)
        soundManager.stopAll()
        for ((output, map) in gsonManager.gsonStartSound(this, gameTitle, path)) {
            for ((title, sound) in map) {
                soundManager.outputSounds[output]?.addSound(title, sound)
                soundManager.outputSounds[output]?.playSound(sound)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        soundManager.stopAll()
        //noter dans le Json de l'histoire le chapitre en lecture et le temps exact
    }

    override fun onResume() {
        super.onResume()
        //reprendre l'histoire exactement là où le user s'est arrêté
    }

    override fun onDestroy() {
        super.onDestroy()
        soundManager.stopAll()
        //noter dans le Json de l'histoire le chapitre en lecture
    }
}
