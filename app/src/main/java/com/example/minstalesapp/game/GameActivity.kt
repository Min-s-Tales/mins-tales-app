package com.example.minstalesapp.game

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment.getExternalStorageDirectory
import android.speech.RecognizerIntent
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.Debug
import com.example.minstalesapp.R
import com.example.minstalesapp.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {

    lateinit var binding: ActivityGameBinding
    val soundManager = SoundManager()
    val dataGsonManager = GsonManager()
    val configGsonManager = GsonManager()

    private val TAG = "[GameActivity]"
    private val model: GameActivityViewModel by viewModels()
    var text = ""
    var gameTitle = ""

    private lateinit var taleURI : Uri
    private lateinit var dataURI : Uri
    private lateinit var configURI : Uri

    private var jsonPath = "start"
    private lateinit var answersMap : HashMap<String, String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        val view = binding.root
        gameTitle = intent.getStringExtra("title")!!
        taleURI = Uri.parse("${getExternalStorageDirectory()!!.path}/Android/data/com.example.minstalesapp/files/Tales/$gameTitle/")
        dataURI =  Uri.parse(taleURI.toString() + "data.json")
        configURI =  Uri.parse(taleURI.toString() + "assets/config.json")

        dataGsonManager.init(dataURI)
        val saveString = dataGsonManager.gsonGetSave()
        if (saveString != null) {
            jsonPath = saveString.toString()
        }

        setContentView(view)
        soundManager.init()
        configGsonManager.init(configURI)


        binding.audioTitle.text = gameTitle.replace("_", " ")

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
                        val result = dataGsonManager.gsonSetSave(key)
                        Log.i(TAG, "Save is $result")
                        nextStep(key)
                        //model.saveGame(configURI, key)
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
        answersMap = configGsonManager.gsonCheckActionPath(path)
        soundManager.stopAll()
        for ((output, map) in configGsonManager.gsonStartSound(this, gameTitle, path)) {
            for ((title, sound) in map) {
                soundManager.outputSounds[output]?.addSound(title, sound)
                soundManager.outputSounds[output]?.playSound(sound)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        //soundManager.stopAll()
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
