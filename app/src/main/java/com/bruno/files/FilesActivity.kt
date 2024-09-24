package com.bruno.files

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.bruno.R

class FilesActivity : AppCompatActivity() {
    lateinit var linearLayoutAndroid: LinearLayout
    lateinit var linearLayoutWeb: LinearLayout
    lateinit var linearLayoutWeb2: LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.files_activity)
        linearLayoutAndroid = findViewById(R.id.llAndroid)
        linearLayoutWeb = findViewById(R.id.llWeb)
        linearLayoutWeb2 = findViewById(R.id.llWeb2)
        linearLayoutAndroid.setOnClickListener{
            var intent = Intent(applicationContext, FilesPulsadoActivity::class.java)
            intent.putExtra("pulsado","android.json")
            startActivity(intent)
        }
        linearLayoutWeb.setOnClickListener{
            var intent = Intent(applicationContext, FilesPulsadoActivity::class.java)
            intent.putExtra("pulsado","web.json")
            startActivity(intent)
        }
        linearLayoutWeb2.setOnClickListener{
            var intent = Intent(applicationContext, FilesPulsadoActivity::class.java)
            intent.putExtra("pulsado","web2.json")
            startActivity(intent)
        }

    }
}