package com.bruno.settings

import android.os.Bundle
import android.util.Log
import android.widget.Adapter
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.bruno.R
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.util.Locale


class SettingsIdiomaActivity : AppCompatActivity() {

    lateinit var lvIdioma: ListView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settingsidioma)

        lvIdioma = findViewById(R.id.lvIdiomas)
        val adapter: ArrayAdapter<*>

      
        var jsonString = loadJSONFromAsset()
        if (jsonString != null) {
            try {
                val jsonObject = JSONObject(jsonString)

                val idiomaList = mutableListOf<String>()
                val localeList = mutableListOf<String>()
                // Iterar sobre las claves del objeto JSON
                val keys = jsonObject.keys()
                while (keys.hasNext()) {
                    val key = keys.next()
                    val item = jsonObject.getJSONObject(key)

                    // Obtener el nombre y agregarlo a la lista
                    val name = item.getString("name")

                    Log.d("idioma mio",name);
                    idiomaList.add(name+ " ("+key+")")
                    localeList.add(key)
                }
                adapter = ArrayAdapter<String>(applicationContext, android.R.layout.simple_list_item_1,idiomaList)
                lvIdioma.adapter = adapter
                lvIdioma.setOnItemClickListener { parent, view, position, id ->
                    val languageTag = localeList.get(position)
                    AppCompatDelegate.setApplicationLocales(
                        LocaleListCompat.create(Locale.forLanguageTag(languageTag))

                    )

                      }
                // Aquí puedes usar la lista idiomaList según lo necesites
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }



    }
    private fun loadJSONFromAsset(): String? {
        val json: String
        try {
            val `is` = assets.open("idiomas.json")
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            json = String(buffer, StandardCharsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            Log.d("no encuentro el json","")
            return null
        }
        return json
    }
}