package com.bruno.bloc

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.bruno.R
import com.bruno.files.FilesActivity
import com.bruno.linkedin.LinkedinActivity
import com.bruno.settings.SettingsActivity

class SplashActivity : AppCompatActivity() {


    lateinit var constraint: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splashactivity)

        val intent = intent.getStringExtra("splash")
        constraint = findViewById(R.id.splash)

        var intentSplash = Intent(applicationContext, MainActivity::class.java)
        if (intent == "bloc") {
            intentSplash = Intent(applicationContext, MainActivity::class.java)

            constraint.setBackgroundDrawable(resources.getDrawable(R.drawable.blocfondo))
        }
        if (intent == "settings") {
            intentSplash = Intent(applicationContext, SettingsActivity::class.java)

            constraint.setBackgroundDrawable(resources.getDrawable(R.drawable.ajustesfondo))
        }
        if (intent == "files") {
            intentSplash = Intent(applicationContext, FilesActivity::class.java)

            constraint.setBackgroundDrawable(resources.getDrawable(R.drawable.filesfondo))
        }
        if (intent == "linkedin") {
            intentSplash = Intent(applicationContext, LinkedinActivity::class.java)

            constraint.setBackgroundDrawable(resources.getDrawable(R.drawable.linkedinfondo))
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
