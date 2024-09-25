package com.bruno

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.bruno.whatsapp.WhatsappActivity
import com.bruno.bloc.SplashActivity
import com.bruno.linkedin.LinkedinActivity
import java.text.SimpleDateFormat
import java.util.Calendar

class MainActivityReal : AppCompatActivity() {

    private lateinit var widget: TextView
    private lateinit var widget2: TextView
    private val handler = Handler()
    private lateinit var linearLayoutWhats: LinearLayout
    private lateinit var linearLayoutBloc: LinearLayout
    private lateinit var linearLayoutSettings: LinearLayout
    private lateinit var linearLayoutFiles: LinearLayout
    private lateinit var linearLayout: LinearLayout
    private lateinit var linearLayoutPin: GridLayout
    private lateinit var linearLayoutMain: LinearLayout
    private lateinit var linearLayoutLinkedin: LinearLayout
    private lateinit var textView0: TextView
    private lateinit var textView1: TextView
    private lateinit var textView2: TextView
    private lateinit var textView3: TextView
    private lateinit var textView4: TextView
    private lateinit var textView5: TextView
    private lateinit var textView6: TextView
    private lateinit var textView7: TextView
    private lateinit var textView8: TextView
    private lateinit var textView9: TextView
    private lateinit var textViewTexto: TextView

    private lateinit var view: TextView
    private lateinit var textViewborrar: ImageView
    private lateinit var textViewenviar: ImageView
    private lateinit var sharedPreferencesPin: SharedPreferences
    private lateinit var pin: String

    override fun onCreate(savedInstanceState: Bundle?) {
        // Apply theme from SharedPreferences before setting content view
        applySavedTheme()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity_movil)
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                )
        // Initialize SharedPreferences for PIN
        sharedPreferencesPin = getSharedPreferences("pin_prefs", MODE_PRIVATE)
        pin = sharedPreferencesPin.getString("pin_key", "1234").toString()

        widget = findViewById(R.id.widget)
        widget2 = findViewById(R.id.widget2)
        linearLayout = findViewById(R.id.principal)
        linearLayoutPin = findViewById(R.id.pin)

        linearLayoutMain = findViewById(R.id.main_layout)
        view = findViewById(R.id.view)

        handler.post(object : Runnable {
            override fun run() {
                val timestamp = SimpleDateFormat("HH : mm").format(Calendar.getInstance().time)
                widget.text = timestamp

                handler.postDelayed(this, 1000)
                val timestamp2 = SimpleDateFormat("EEEE, d 'of' MMMM").format(Calendar.getInstance().time)
                widget2.text = timestamp2
            }
        })

        linearLayoutWhats = findViewById(R.id.llWhatsapp)
        linearLayoutWhats.setOnClickListener {
            val intent = Intent(this, WhatsappActivity::class.java)
            startActivity(intent)
        }
        linearLayoutBloc = findViewById(R.id.llBloc)
        linearLayoutBloc.setOnClickListener {
            val intent = Intent(this, SplashActivity::class.java)
            intent.putExtra("splash", "bloc")
            startActivity(intent)
        }
        linearLayoutSettings = findViewById(R.id.llSettings)
        linearLayoutSettings.setOnClickListener {
            val intent = Intent(this, SplashActivity::class.java)
            intent.putExtra("splash", "settings")
            startActivity(intent)
        }
        linearLayoutFiles = findViewById(R.id.llFiles)
        linearLayoutFiles.setOnClickListener {
            val intent = Intent(this, SplashActivity::class.java)
            intent.putExtra("splash", "files")
            startActivity(intent)
        }
        linearLayoutLinkedin = findViewById(R.id.llLinkedin)
        linearLayoutLinkedin.setOnClickListener {
            val intent = Intent(this, SplashActivity::class.java)
            intent.putExtra("splash", "linkedin")
            startActivity(intent)
        }
        textViewTexto = findViewById(R.id.texto)
        view.text = "Introduce el pin " + pin
        textView0 = findViewById(R.id.numero0)
        textView1 = findViewById(R.id.numero)
        textView2 = findViewById(R.id.numero2)
        textView3 = findViewById(R.id.numero3)
        textView4 = findViewById(R.id.numero4)
        textView5 = findViewById(R.id.numero5)
        textView6 = findViewById(R.id.numero6)
        textView7 = findViewById(R.id.numero7)
        textView8 = findViewById(R.id.numero8)
        textView9 = findViewById(R.id.numero9)
        textViewborrar = findViewById(R.id.borrar)
        textViewenviar = findViewById(R.id.enviar)

        setupTouchListeners()
    }

    private fun applySavedTheme() {
        val sharedPreferences = getSharedPreferences("theme_prefs", MODE_PRIVATE)
        val theme = sharedPreferences.getString("theme_key", "light") ?: "light"
        applyTheme(theme)
    }

    private fun applyTheme(theme: String) {
        when (theme) {
            "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun setupTouchListeners() {
        textView0.setOnTouchListener(createTouchListener("0"))
        textView1.setOnTouchListener(createTouchListener("1"))
        textView2.setOnTouchListener(createTouchListener("2"))
        textView3.setOnTouchListener(createTouchListener("3"))
        textView4.setOnTouchListener(createTouchListener("4"))
        textView5.setOnTouchListener(createTouchListener("5"))
        textView6.setOnTouchListener(createTouchListener("6"))
        textView7.setOnTouchListener(createTouchListener("7"))
        textView8.setOnTouchListener(createTouchListener("8"))
        textView9.setOnTouchListener(createTouchListener("9"))
        textViewborrar.setOnTouchListener(createTouchListener("delete"))
        textViewenviar.setOnTouchListener(createTouchListener("send"))
    }

    private fun createTouchListener(action: String): View.OnTouchListener {
        return View.OnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    v.background = resources.getDrawable(R.drawable.circlepulsado, null)
                    true
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    when (action) {
                        "delete" -> handleDelete()
                        "send" -> handleSend()
                        else -> handleNumber(action)
                    }
                    v.background = resources.getDrawable(R.drawable.circle, null)
                    true
                }
                else -> false
            }
        }
    }

    private fun handleNumber(number: String) {
        val textoConcatenado = textViewTexto.text.toString()
        textViewTexto.text = textoConcatenado + number
    }

    private fun handleDelete() {
        var textoConcatenado = textViewTexto.text.toString()
        if (textoConcatenado.isNotEmpty()) {
            textoConcatenado = textoConcatenado.substring(0, textoConcatenado.length - 1)
            textViewTexto.text = textoConcatenado
        }
    }

    private fun handleSend() {
        if (textViewTexto.text.toString() == pin) {
            Log.d("correcto", "")
            linearLayoutPin.visibility = View.GONE
            linearLayout.visibility = View.VISIBLE
            linearLayoutMain.background = resources.getDrawable(R.drawable.wallpaper, null)
            loadImageFromPreferences()
        } else {
            val animation = AnimationUtils.loadAnimation(this, R.anim.shake)
            view.text = "Pin incorrecto"
            textViewTexto.startAnimation(animation)
            Handler().postDelayed({
                view.text = "Introduce el pin "+pin
                textViewTexto.clearAnimation()
            }, 1000)
        }
    }

    private fun loadImageFromPreferences() {
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val imagePathString = sharedPreferences.getString("imagePath", null)
        Log.d("preferencias", sharedPreferences.toString())
        Log.d("preferencias", imagePathString.toString())
        imagePathString?.let {
            val imageUri = Uri.parse(it)
            Log.d("foto", imageUri.toString())
            displayImageFromUri(imageUri)
        }
    }

    private fun displayImageFromUri(uri: Uri) {
        try {
            contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            contentResolver.takePersistableUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            val inputStream = contentResolver.openInputStream(uri)
            val drawable = Drawable.createFromStream(inputStream, uri.toString())
            linearLayoutMain.background = drawable
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("hay error", e.toString())
        }
    }
}
