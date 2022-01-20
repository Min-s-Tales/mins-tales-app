package com.example.minstalesapp

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.record_activity.*
import java.io.IOException

class RecordActivity : AppCompatActivity() {

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
    private var mStartPlaying = true


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
    }
}