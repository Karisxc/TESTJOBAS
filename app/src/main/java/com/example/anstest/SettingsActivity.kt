package com.example.anstest

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Button
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.io.IOException

class SettingsActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var quoteTextView: TextView
    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE)
        quoteTextView = findViewById(R.id.quoteTextView)

        val updateButton: Button = findViewById(R.id.updateButton)
        val radioGroup: RadioGroup = findViewById(R.id.radioGroup)

        apiService = Retrofit.Builder()
            .baseUrl("https://animechan.xyz")
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        updateButton.setOnClickListener { fetchQuote() }

        val savedPeriod = sharedPreferences.getInt("updatePeriod", -1)
        if (savedPeriod != -1) {
            radioGroup.check(savedPeriod)
        }

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            sharedPreferences.edit().putInt("updatePeriod", checkedId).apply()
        }
    }

    private fun fetchQuote() {
        FetchQuoteTask().execute()
    }

    private inner class FetchQuoteTask : AsyncTask<Void, Void, String?>() {
        override fun doInBackground(vararg params: Void?): String? {
            return try {
                val response = apiService.getRandomQuote().execute()
                response.body()?.quote
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try {
                if (!result.isNullOrBlank()) {
                    quoteTextView.text = result
                    sharedPreferences.edit().putString("quote", result).apply()

                    val resultIntent = Intent()
                    resultIntent.putExtra("updatedQuote", result)
                    setResult(Activity.RESULT_OK, resultIntent)
                } else {
                    quoteTextView.text = "Не удалось получить цитату."
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

interface ApiService {
    @GET("/api/random")
    fun getRandomQuote(): retrofit2.Call<QuoteResponse>
}

data class QuoteResponse(val anime: String, val character: String, val quote: String)
