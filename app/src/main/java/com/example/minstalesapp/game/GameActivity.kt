package com.example.minstalesapp.game

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import com.example.minstalesapp.databinding.ActivityGameBinding
import kotlinx.android.synthetic.main.activity_game.*


open class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding

    protected val RESULT_SPEECH = 1
    private var TAG = "GameActivity"
    private val model: GameActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        /*binding.recordButton.setOnClickListener {
            Log.i(TAG, "RECORDING")

            model.record( /*"$externalCacheDir/record.wav", "$externalCacheDir/songs.json"*/)
        }*/

        binding.recordButton.setOnClickListener {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US")
            try {
                getResult.launch(intent)
                //startActivityForResult(intent, RESULT_SPEECH)
                binding.tvText.setText("")
            } catch (e: ActivityNotFoundException) {
                Log.e(TAG, "onCreate: $e", )
                Toast.makeText(
                    applicationContext,
                    "Your device doesn't support Speech to Text",
                    Toast.LENGTH_SHORT
                ).show()
                e.printStackTrace()
            }
        }
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) {
            if(it.resultCode == Activity.RESULT_OK){
                val value = it.data?.getStringExtra("input")
            }
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RESULT_CANCELED -> if (resultCode == RESULT_OK && data != null) {
                Log.i(TAG, "onActivityResult: cancelled")
                val text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                binding.tvText.text = text!![0]
            }
            RESULT_FIRST_USER -> if (resultCode == RESULT_OK && data != null) {
                Log.i(TAG, "onActivityResult: first user")
                val text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                binding.tvText.text = text!![0]
            }
            RESULT_OK -> if (resultCode == RESULT_OK && data != null) {
                Log.i(TAG, "onActivityResult: ok")
                val text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                binding.tvText.text = text!![0]
            }
        }
    }
}