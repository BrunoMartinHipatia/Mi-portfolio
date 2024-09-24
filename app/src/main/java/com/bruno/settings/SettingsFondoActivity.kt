package com.bruno.settings


import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.bruno.MainActivityReal
import com.bruno.R
import org.xmlpull.v1.XmlPullParserException
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.nio.file.Files
import java.util.Locale


class SettingsFondoActivity : AppCompatActivity() {
    lateinit var llfondo: LinearLayout
    lateinit var ivchangewall: ImageView
    private val PICK_IMAGE_REQUEST = 2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settingsfondo)
        llfondo = findViewById(R.id.principal)

        ivchangewall = findViewById(R.id.changewall)
        ivchangewall.setOnClickListener {
            openFilePicker()
        }
    }



    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        intent.type = "image/*"
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val selectedImageUri: Uri = data.data!!
            // Guardar la ruta del archivo en SharedPreferences
            saveImagePath(selectedImageUri)
            // Mostrar la imagen seleccionada como fondo
            displayImageFromUri(selectedImageUri)
        }
    }

    private fun displayImageFromUri(uri: Uri) {
        try {
            contentResolver.takePersistableUriPermission(uri, (Intent.FLAG_GRANT_READ_URI_PERMISSION))
            contentResolver.takePersistableUriPermission(uri, (Intent.FLAG_GRANT_WRITE_URI_PERMISSION))
            val inputStream = contentResolver.openInputStream(uri)

            val drawable = Drawable.createFromStream(inputStream, uri.toString())
            llfondo.background = drawable
            var intent = Intent(applicationContext, MainActivityReal::class.java)
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
            // Manejar el error, por ejemplo, mostrar un mensaje al usuario
        }
    }

    private fun saveImagePath(uri: Uri) {
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("imagePath", uri.toString())
        editor.apply()

    }





}