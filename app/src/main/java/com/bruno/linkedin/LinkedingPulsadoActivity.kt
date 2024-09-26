package com.bruno.linkedin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bruno.R
import com.bruno.whatsapp.WhatsappBrunoActivity

class LinkedingPulsadoActivity : AppCompatActivity() {
    private lateinit var linearLayoutWhats: LinearLayout;
    private lateinit var linearLayoutWhats2: LinearLayout;
    private lateinit var linearLayoutWhats3: LinearLayout;
    private lateinit var linearLayoutWhats4: LinearLayout;
    private lateinit var linearLayoutWhats5: LinearLayout;
    private lateinit var linearLayoutWhats6: LinearLayout;




    private lateinit var cerrar: ImageView
    private lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = (
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        or View.SYSTEM_UI_FLAG_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY

                )
        setContentView(R.layout.linkedinchats_activity)

        searchView = findViewById(R.id.busqueda)

        cerrar = findViewById(R.id.cerrar)
        linearLayoutWhats = findViewById(R.id.llBruno)
        linearLayoutWhats.setOnClickListener{
            val intent= Intent(this, LinkedinBrunoActivity::class.java)
            startActivity(intent)
        }



        cerrar.setOnClickListener {


            searchView.setVisibility(View.GONE)
            cerrar.setVisibility(View.GONE)
        }
        linearLayoutWhats = findViewById(R.id.llBruno)
        linearLayoutWhats2 = findViewById(R.id.llCarlos)
        linearLayoutWhats3 = findViewById(R.id.llJorge)
        linearLayoutWhats4 = findViewById(R.id.llFernando)
        linearLayoutWhats5 = findViewById(R.id.llRuben)
        linearLayoutWhats6 = findViewById(R.id.llSilvia)
        linearLayoutWhats.setTag("Bruno")
        linearLayoutWhats2.setTag("Carlos")
        linearLayoutWhats3.setTag("Jorge")
        linearLayoutWhats4.setTag("Fernando")
        linearLayoutWhats5.setTag("Ruben")
        linearLayoutWhats6.setTag("Silvia")

        val list = arrayListOf<LinearLayout>()
        list.add(linearLayoutWhats)
        list.add(linearLayoutWhats2)
        list.add(linearLayoutWhats3)
        list.add(linearLayoutWhats4)
        list.add(linearLayoutWhats5)
        list.add(linearLayoutWhats6)

        searchView.setOnQueryTextListener(object :
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {

                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                val lowerCaseNewText = newText.toLowerCase()
                for (linear in list.indices) {
                    val nombre = list[linear].getTag().toString().toLowerCase()
                    Log.d("nombre", nombre)
                    if (!nombre.contains(lowerCaseNewText)) {
                        list[linear].visibility = View.GONE
                        Log.d("lista", list[linear].getTag().toString())
                    } else {
                        list[linear].visibility = View.VISIBLE
                    }
                }
                return true
            }

        });
    }

}
