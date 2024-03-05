package com.example.anstest

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class VideoActivity : AppCompatActivity() {

    companion object {
        private const val PICK_VIDEO_REQUEST = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "video/*"
        startActivityForResult(intent, PICK_VIDEO_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_VIDEO_REQUEST) {
                val selectedVideoUri: Uri? = data?.data
                try {
                    val videoIntent = Intent(Intent.ACTION_VIEW)
                    videoIntent.setDataAndType(selectedVideoUri, "video/*")
                    startActivity(videoIntent)
                    finish()
                } catch (e: ActivityNotFoundException) {
                    Log.e("VideoActivity", "Error starting video playback", e)
                }
            }
        }
    }
}
