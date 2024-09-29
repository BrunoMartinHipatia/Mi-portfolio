package com.bruno.whatsapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bruno.R

class WhatsappActivity : AppCompatActivity() {
    private lateinit var linearLayoutWhats: LinearLayout;
    private lateinit var linearLayoutWhats2: LinearLayout;
    private lateinit var linearLayoutWhats3: LinearLayout;
    private lateinit var linearLayoutWhats4: LinearLayout;
    private lateinit var linearLayoutWhats5: LinearLayout;
    private lateinit var linearLayoutWhats6: LinearLayout;

    private lateinit var fondowhats: ImageView
    private lateinit var lupa: ImageView

    private lateinit var cerrar: ImageView
    private lateinit var searchView: SearchView
    private lateinit var textView: TextView
    private lateinit var view: View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.whatsapp_activity)
        fondowhats = findViewById(R.id.fondowhats)
        searchView = findViewById(R.id.busqueda)
        textView = findViewById(R.id.whatsapptext)
        view = findViewById(R.id.view)
        cerrar = findViewById(R.id.cerrar)
        lupa = findViewById(R.id.lupa)
        lupa.setOnClickListener {
            lupa.setVisibility(View.GONE)
            view.setVisibility(View.GONE)
            textView.setVisibility(View.GONE)
            searchView.setVisibility(View.VISIBLE)
            cerrar.setVisibility(View.VISIBLE)
        }


        val timerThread: Thread = object : Thread() {
            override fun run() {
                try {
                    sleep(2000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } finally {

                    runOnUiThread {
                        fondowhats.visibility = View.GONE
                    }
                }
            }
        }
        timerThread.start()
        cerrar.setOnClickListener {
            lupa.setVisibility(View.VISIBLE)
            view.setVisibility(View.VISIBLE)
            textView.setVisibility(View.VISIBLE)
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
        linearLayoutWhats.setOnClickListener{
            val intent= Intent(this, WhatsappBrunoActivity::class.java)
            startActivity(intent)
        }
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
