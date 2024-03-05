package com.example.anstest

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class D_Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_there_dactivity)
        openFilePicker()
    }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "STL/*"
        intent.type = "STEP/*"
        intent.type = "OBJ/*"
        try {
            startActivityForResult(intent, FILE_PICKER_REQUEST_CODE)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FILE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            val selectedFileUri = data?.data
        }
    }

    companion object {
        private const val FILE_PICKER_REQUEST_CODE = 123
    }
}
