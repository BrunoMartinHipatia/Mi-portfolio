package com.bruno.whatsapp

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bruno.R


class WhatsappBrunoActivity : AppCompatActivity() {

    private lateinit var textView: TextView
    private lateinit var imgTele: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = resources.getColor(R.color.whats, theme)
        }
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

                )
        setContentView(R.layout.whatsappbruno_activity)

        imgTele = findViewById(R.id.imgTele)
        imgTele.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CALL_PHONE),
                    1)

            } else {

                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "601 16 19 64"))
                startActivity(intent)

            }
        }

        textView = findViewById(R.id.enlace)
        textView.linksClickable

    }
}
