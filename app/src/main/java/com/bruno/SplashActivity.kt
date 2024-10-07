package com.bruno.bloc

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.bruno.R
import com.bruno.files.FilesActivity
import com.bruno.linkedin.LinkedinActivity
import com.bruno.pccomponentes.PccomponentesActivity
import com.bruno.settings.SettingsActivity

class SplashActivity : AppCompatActivity() {


    lateinit var constraint: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splashactivity)
        enableEdgeToEdge()
        val intent = intent.getStringExtra("splash")
        constraint = findViewById(R.id.splash)

        val intentSplash: Intent
        when (intent) {
            "bloc" -> {
                intentSplash = Intent(applicationContext, MainActivity::class.java)
                constraint.setBackgroundDrawable(resources.getDrawable(R.drawable.blocfondo))
            }
            "settings" -> {
                intentSplash = Intent(applicationContext, SettingsActivity::class.java)
                constraint.setBackgroundDrawable(resources.getDrawable(R.drawable.ajustesfondo))
            }
            "files" -> {
                intentSplash = Intent(applicationContext, FilesActivity::class.java)
                constraint.setBackgroundDrawable(resources.getDrawable(R.drawable.filesfondo))
            }
            "linkedin" -> {
                intentSplash = Intent(applicationContext, LinkedinActivity::class.java)
                constraint.setBackgroundDrawable(resources.getDrawable(R.drawable.linkedinfondo))
            }
            "pccomponentes" -> {
                intentSplash = Intent(applicationContext, PccomponentesActivity::class.java)
                constraint.setBackgroundDrawable(resources.getDrawable(R.drawable.pccomponentesfondo))
            }
            else -> {
                intentSplash = Intent(applicationContext, MainActivity::class.java) // Valor por defecto
            }
        }

        val thread: Thread = object : Thread() {
            override fun run() {
                try {
                    sleep(2000)
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    runOnUiThread {
                        startActivity(intentSplash)
                        finish()
                    }
                }
            }
        }
        thread.start()
    }
}
