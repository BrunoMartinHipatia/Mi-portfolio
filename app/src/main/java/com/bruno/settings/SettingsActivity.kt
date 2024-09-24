package com.bruno.settings

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.bruno.R

class SettingsActivity : AppCompatActivity() {
    lateinit var llfondo: LinearLayout
    lateinit var llIdioma: LinearLayout
    lateinit var llModo: LinearLayout
    lateinit var llPin: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings)
        llfondo = findViewById(R.id.llfondo)
        llIdioma = findViewById(R.id.llIdioma)
        llModo = findViewById(R.id.llTheme)
        llPin = findViewById(R.id.llPin)
        llfondo.setOnClickListener{
            val intent = Intent(applicationContext, SettingsFondoActivity::class.java)
            startActivity(intent)
        }
        llIdioma.setOnClickListener{
            Log.d("XDDD","");
            val intent = Intent(applicationContext, SettingsIdiomaActivity::class.java)


            startActivity(intent)
        }
        llModo.setOnClickListener{
            Log.d("XDDD","");
            val intent = Intent(applicationContext, SettingsModoActivity::class.java)


            startActivity(intent)
        }
        llPin.setOnClickListener{
            Log.d("XDDD","");
            val intent = Intent(applicationContext, SettingsPinActivity::class.java)


            startActivity(intent)
        }
    }
}