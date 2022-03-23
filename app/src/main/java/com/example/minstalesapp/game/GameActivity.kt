package com.example.minstalesapp.game

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.minstalesapp.databinding.ActivityGameBinding
import java.util.*


open class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding

    private val TAG = "[GameActivity]"
    private val model: GameActivityViewModel by viewModels()
    private var text = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val launcher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK && result.data != null) {
                val data = result.data
                text = data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)!![0]
                binding.tvText.text = text

                if (text.toLowerCase().contains("bonjour")) {
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

                launcher.launch(intent)
            }
        }
    }
}