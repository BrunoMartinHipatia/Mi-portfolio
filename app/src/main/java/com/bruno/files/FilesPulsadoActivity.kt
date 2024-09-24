package com.bruno.files

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.FileProvider
import androidx.core.os.LocaleListCompat
import com.bruno.BuildConfig
import com.bruno.R
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.nio.charset.StandardCharsets
import java.util.Locale


class FilesPulsadoActivity : AppCompatActivity() {

    lateinit var lvAndroid: ListView
    lateinit var jsonFile: String
    lateinit var listaApks: ArrayList<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.filepulsado_activity)

        jsonFile = intent.getStringExtra("pulsado").toString()
        lvAndroid = findViewById(R.id.lvProyectos)
        listaApks= arrayListOf()

        val jsonString = loadJSONFromAsset()
        if (jsonString != null) {
            try {
                val jsonObject = JSONObject(jsonString)
                val items = mutableListOf<FileItem>()
                val keys = jsonObject.keys()

                while (keys.hasNext()) {
                    val key = keys.next()
                    val item = jsonObject.getJSONObject(key)

                    // Obtener nombre y ruta del icono
                    val name = item.getString("name")
                    val ruta = item.getString("icono")
                    val apk = item.getString("apk")
                    listaApks.add(apk)
                    // Agregar el item a la lista
                    items.add(FileItem(name, ruta))
                }

                val adapter = FileAdapter(this, items)
                lvAndroid.adapter = adapter
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        lvAndroid.setOnItemClickListener { parent, view, position, id ->

            copyApkFromAssets(listaApks.get(position))

        }
    }
    private fun copyApkFromAssets(str : String) {
        // Ruta donde se copiará el archivo APK
        val apkFile = File(getExternalFilesDir(null), str)

        // Copiar el APK desde los assets
        try {
            val inputStream: InputStream = assets.open(str)
            val outputStream = FileOutputStream(apkFile)
            val buffer = ByteArray(1024)
            var length: Int
            while (inputStream.read(buffer).also { length = it } > 0) {
                outputStream.write(buffer, 0, length)
            }
            outputStream.close()
            inputStream.close()

            // Una vez copiado, iniciar el proceso de instalación
            installApk(apkFile)

        } catch (e: Exception) {
            Log.d("apk", e.printStackTrace().toString())
            e.printStackTrace()
        }
    }

    private fun installApk(apkFile: File) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (!packageManager.canRequestPackageInstalls()) {
                // Si no se tienen permisos para instalar desde fuentes desconocidas, pedirlos
                val intent = Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES).setData(
                    Uri.parse(String.format("package:%s", packageName))
                )
                startActivityForResult(intent, 1234)
            } else {
                // Instalar el APK
                launchApkInstaller(apkFile)
            }
        } else {
            // Instalar el APK para versiones anteriores
            launchApkInstaller(apkFile)
        }
    }

    private fun launchApkInstaller(apkFile: File) {
        val intent = Intent(Intent.ACTION_VIEW)
        val apkUri: Uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            FileProvider.getUriForFile(
                this,
                applicationContext.packageName + ".provider",  // Debes declarar un FileProvider en el manifest
                apkFile
            )
        } else {
            Uri.fromFile(apkFile)
        }

        intent.setDataAndType(apkUri, "application/vnd.android.package-archive")
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivity(intent)
    }
    private fun loadJSONFromAsset(): String? {
        val json: String
        try {
            val `is` = assets.open(jsonFile)
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            json = String(buffer, StandardCharsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            Log.d("no encuentro el json", "")
            return null
        }
        return json
    }
}
