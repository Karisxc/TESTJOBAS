package com.example.anstest

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var quoteTextView: TextView

    companion object {
        private const val SETTINGS_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE)
        quoteTextView = findViewById(R.id.quoteTextView)
        quoteTextView.text = sharedPreferences.getString("quote", "Цитата дня")

        setupButton(R.id.videosButton, VideoActivity::class.java)
        setupButton(R.id.imagesButton, ImageActivity::class.java)
        setupButton(R.id.settingsButton, SettingsActivity::class.java, SETTINGS_REQUEST_CODE)
        setupButton(R.id.objects3DButton, D_Activity::class.java)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SETTINGS_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val updatedQuote = data?.getStringExtra("updatedQuote")
            if (!updatedQuote.isNullOrBlank()) {
                quoteTextView.text = updatedQuote
            }
        }
    }

    private fun setupButton(buttonId: Int, destination: Class<*>, requestCode: Int? = null) {
        findViewById<Button>(buttonId).setOnClickListener {
            val intent = Intent(this, destination)
            if (requestCode != null) {
                startActivityForResult(intent, requestCode)
            } else {
                startActivity(intent)
            }
        }
    }
}
