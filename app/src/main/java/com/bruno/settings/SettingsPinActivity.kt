package com.bruno.settings

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.bruno.MainActivityReal
import com.bruno.R


class SettingsPinActivity : AppCompatActivity() {

    private lateinit var introducePin: TextView

    private lateinit var etPin: EditText
    private lateinit var etPin2: EditText
    private lateinit var etPin3: EditText
    private lateinit var buttonPin: Button
    private  var primero = false
    private  val PREFS_PIN = "pin_prefs"
    private  val PIN_KEY = "pin_key"
    private lateinit var sharedPreferencesPin: SharedPreferences
    private lateinit var pin: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settingspin)

        introducePin = findViewById(R.id.textPin)
        etPin = findViewById(R.id.password)
        etPin2 = findViewById(R.id.password2)
        etPin3 = findViewById(R.id.password3)

        buttonPin = findViewById(R.id.buttonpin)
        sharedPreferencesPin = getSharedPreferences("pin_prefs", MODE_PRIVATE)
        pin = sharedPreferencesPin.getString("pin_key", "1234").toString()
        buttonPin.setOnClickListener{
            Log.d("texto1" , etPin2.text.toString())
            Log.d("texto2" , etPin3.text.toString())
            Log.d("texto3" , primero.toString())
            if(etPin.text.toString() == pin){
                primero = true
                etPin.setVisibility(View.GONE)
                etPin2.setVisibility(View.VISIBLE)
                etPin3.setVisibility(View.VISIBLE)
                introducePin.text="Introduce el nuevo pin"
            }
            if((etPin2.text.toString() == etPin3.text.toString()) && primero && etPin3.length()==4 ){

                Toast.makeText(applicationContext, "Pin cambiado con exito", Toast.LENGTH_LONG).show()
                val editor = getSharedPreferences(PREFS_PIN, MODE_PRIVATE).edit()
                editor.putString(PIN_KEY, etPin3.text.toString())
                editor.apply()
                var intent = Intent(applicationContext, MainActivityReal::class.java)
                startActivity(intent)
            }

            }

        }

    }

