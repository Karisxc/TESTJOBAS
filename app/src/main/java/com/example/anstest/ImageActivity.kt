
package com.example.anstest

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


class ImageActivity : AppCompatActivity() {


    companion object {
        private const val PICK_IMAGES_REQUEST = 1
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.activity_image)


        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"

        startActivityForResult(intent, PICK_IMAGES_REQUEST)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (resultCode == Activity.RESULT_OK) {

            if (requestCode == PICK_IMAGES_REQUEST) {

                val selectedImageUri: Uri? = data?.data


                val imageView: ImageView = findViewById(R.id.imageViewInImageActivity)
                imageView.setImageURI(selectedImageUri)
            }
        }
    }
}
