package com.bruno.settings

import android.os.Bundle
import android.widget.CompoundButton
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.bruno.R

class SettingsModoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settingsmodo)

        val switchLight = findViewById<Switch>(R.id.switch1)
        val switchDark = findViewById<Switch>(R.id.switch2)

        // Configurar los switches según el tema guardado
        val preferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val currentTheme = preferences.getString(THEME_KEY, "light")
        updateSwitches(currentTheme, switchLight, switchDark)

        switchLight.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                saveTheme("light")
                switchDark.isChecked=false
                applyTheme("light")
            }
        }

        switchDark.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                saveTheme("dark")
                switchLight.isChecked=false
                applyTheme("dark")
            }
        }
    }

    private fun applyTheme(theme: String) {
        when (theme) {
            "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun saveTheme(theme: String) {
        val editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit()
        editor.putString(THEME_KEY, theme)
        editor.apply()
    }

    private fun updateSwitches(currentTheme: String?, switchLight: Switch, switchDark: Switch) {
        switchLight.isChecked = currentTheme == "light"
        switchDark.isChecked = currentTheme == "dark"
    }

    companion object {
        private const val PREFS_NAME = "theme_prefs"
        private const val THEME_KEY = "theme_key"
    }
}
