package com.example.minstalesapp.game

import android.Manifest
import android.R
import android.content.ActivityNotFoundException
import android.content.Intent
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.record_activity.*


class TestActivity : AppCompatActivity() {

    /*private lateinit var binding: ActivityRecordBinding
    private val LOG_TAG = "AudioRecordTest"
    private val REQUEST_RECORD_AUDIO_PERMISSION = 200
    private var fileName: String? = null

    private var recordButton: Button? = null
    private var recorder: MediaRecorder? = null

    private var playButton: Button? = null
    private var player: MediaPlayer? = null

    // Requesting permission to RECORD_AUDIO
    private var permissionToRecordAccepted = false
    private val permissions = arrayOf(Manifest.permission.RECORD_AUDIO);

    private var mStartRecording = true
    private var mStartPlaying = true*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*setContentView(R.layout.record_activity)
        tvText = findViewById<View>(R.id.tvText)
        recordButton = findViewById<View>(R.id.btnSpeak)
        recordButton.setOnClickListener(object : OnClickListener() {
            fun onClick(view: View?) {
                val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                intent.putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                )
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US")
                try {
                    startActivityForResult(intent, RESULT_SPEECH)
                    tvText.setText("")
                } catch (e: ActivityNotFoundException) {
                    Toast.makeText(
                        applicationContext,
                        "Your device doesn't support Speech to Text",
                        Toast.LENGTH_SHORT
                    ).show()
                    e.printStackTrace()
                }
            }
        })*/
    }

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RESULT_SPEECH -> if (resultCode == RESULT_OK && data != null) {
                val text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                tvText.setText(text!![0])
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.record_activity)

        startRecordButton.setOnClickListener {
            onRecord(mStartRecording)
            mStartRecording = !mStartRecording
        }

        playRecordButton.setOnClickListener {
            onPlay(mStartPlaying)
            mStartPlaying = !mStartPlaying
        }
        // Record to the external cache directory for visibility
        fileName = externalCacheDir!!.absolutePath
        fileName += "/audiorecordtest.3gp"
        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_RECORD_AUDIO_PERMISSION -> permissionToRecordAccepted =
                grantResults[0] == PackageManager.PERMISSION_GRANTED
        }
        if (!permissionToRecordAccepted) finish()
    }

    private fun onRecord(start: Boolean) {
        if (start) {
            startRecording()
        } else {
            stopRecording()
        }
    }

    private fun onPlay(start: Boolean) {
        if (start) {
            startPlaying()
        } else {
            stopPlaying()
        }
    }

    private fun startPlaying() {
        player = MediaPlayer()
        try {
            player!!.setDataSource(fileName)
            player!!.prepare()
            player!!.start()
        } catch (e: IOException) {
            Log.e(LOG_TAG, "prepare() failed")
        }
    }

    private fun stopPlaying() {
        player!!.release()
        player = null
    }

    private fun startRecording() {
        recorder = MediaRecorder()
        recorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
        recorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        recorder!!.setOutputFile(fileName)
        recorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        try {
            recorder!!.prepare()
        } catch (e: IOException) {
            Log.e(LOG_TAG, "prepare() failed")
        }
        recorder!!.start()
    }

    private fun stopRecording() {
        recorder!!.stop()
        recorder!!.release()
        recorder = null
    }

    /*internal class RecordButton(ctx: Context?) :
        AppCompatButton(ctx!!) {
        var mStartRecording = true
        var clicker = OnClickListener { v: View? ->
            onRecord(mStartRecording)
            text = if (mStartRecording) {
                "Stop recording"
            } else {
                "Start recording"
            }
            mStartRecording = !mStartRecording
        }

        init {
            text = "Start recording"
            setOnClickListener(clicker)
        }
    }*/

    /*internal class PlayButton(ctx: Context?) : AppCompatButton(
        ctx!!
    ) {
        var mStartPlaying = true
        var clicker = OnClickListener {
            onPlay(mStartPlaying)
            text = if (mStartPlaying) {
                "Stop playing"
            } else {
                "Start playing"
            }
            mStartPlaying = !mStartPlaying
        }

        init {
            text = "Start playing"
            setOnClickListener(clicker)
        }
    }*/

    override fun onStop() {
        super.onStop()
        if (recorder != null) {
            recorder!!.release()
            recorder = null
        }
        if (player != null) {
            player!!.release()
            player = null
        }
    }*/
}