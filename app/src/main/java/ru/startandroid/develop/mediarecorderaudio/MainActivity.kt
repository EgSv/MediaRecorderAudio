package ru.startandroid.develop.mediarecorderaudio

import android.app.Activity
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.view.View
import java.io.File

class MainActivity : Activity() {
    private var mediaRecorder: MediaRecorder? = null
    private var mediaPlayer: MediaPlayer? = null
    private var fileName: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fileName = Environment.getExternalStorageDirectory().toString() + "/record.3gpp"
    }

    fun recordStart(v: View?) {
        try {
            releaseRecorder()
            val outFile = File(fileName)
            if (outFile.exists()) {
                outFile.delete()
            }
            mediaRecorder = MediaRecorder()
            mediaRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
            mediaRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            mediaRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            mediaRecorder!!.setOutputFile(fileName)
            mediaRecorder!!.prepare()
            mediaRecorder!!.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun recordStop(v: View?) {
        if (mediaRecorder != null ) {
            mediaRecorder!!.stop()
        }
    }

    fun playStart(v: View?) {
        try {
            releasePlayer()
            mediaPlayer = MediaPlayer()
            mediaPlayer!!.setDataSource(fileName)
            mediaPlayer!!.prepare()
            mediaPlayer!!.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun playStop(v: View?) {
        if (mediaPlayer != null) {
            mediaPlayer!!.stop()
        }
    }

    private fun releaseRecorder() {
        if (mediaRecorder != null) {
            mediaRecorder!!.release()
            mediaRecorder = null
        }
    }

    private fun releasePlayer() {
        if (mediaPlayer != null) {
            mediaPlayer!!.release()
            mediaPlayer = null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        releasePlayer()
        releaseRecorder()
    }
}