package com.example.minstalesapp.game

import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.minstalesapp.databinding.ActivityGameBinding
import java.util.*


open class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding

    private var TAG = "GameActivity"
    private val model: GameActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //setContentView(R.layout.activity_test1);
        //setContentView(R.layout.activity_test1);
        val launcher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result ->
            if (result.resultCode == RESULT_OK && result.data != null) {
                val data = result.data
                binding.tvText.setText(data!!.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)!![0])
            }
        }

        binding.recordButton.setOnClickListener { view -> run {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Start speaking")

            launcher.launch(intent)
        }
            //recognitionCustom.launch(5)
        }
    }

    /*
    private val recognitionCustom = registerForActivityResult(RecognitionContract()) { result ->
        binding.tvText.setText(result)
    }
     */
}
/*
class RecognitionContract : ActivityResultContract<Int, String>() {
    override fun createIntent(context: Context, input: Int): Intent {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to Text")
        return intent
    }


    override fun parseResult(resultCode: Int, intent: Intent?): String {
        return intent?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0) ?: ""
    }
}
*/