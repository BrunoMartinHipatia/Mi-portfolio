package com.bruno.linkedin

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bruno.R

class LinkedinActivity : AppCompatActivity() {
    lateinit var image: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.linkedin)
        image = findViewById(R.id.chaticon)
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                )
        image.setOnClickListener{
            var intent = Intent(applicationContext, LinkedingPulsadoActivity::class.java)
            startActivity(intent)
        }
    }
}