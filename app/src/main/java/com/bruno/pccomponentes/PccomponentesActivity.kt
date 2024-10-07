package com.bruno.pccomponentes

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bruno.R

class PccomponentesActivity : Activity() {
    lateinit var view: View
    lateinit var view2: View
    lateinit var view3: View
    lateinit var view4: View
    lateinit var view5: View
    lateinit var tvPortatiles: TextView
    lateinit var tvOrdenadores: TextView
    lateinit var tvSmartphones: TextView
    lateinit var tvTelevisores: TextView
    lateinit var tvMonitores: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.pccomponentes_activity)
        view = findViewById(R.id.view1)
        view2 = findViewById(R.id.view2)
        view3 = findViewById(R.id.view3)
        view4 = findViewById(R.id.view4)
        view5 = findViewById(R.id.view5)

        tvPortatiles = findViewById(R.id.tvPortatiles)
        tvOrdenadores = findViewById(R.id.tvOrdenadores)
        tvSmartphones = findViewById(R.id.tvSmartphones)
        tvTelevisores = findViewById(R.id.tvTelevisores)
        tvMonitores = findViewById(R.id.tvMonitores)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewHorizontal)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val items = listOf(
            ItemAdapter.Item(R.drawable.amazon, "Item 1"),
            ItemAdapter.Item(R.drawable.linkedin, "Item 2"),
            ItemAdapter.Item(R.drawable.file, "Item 3"),
            ItemAdapter.Item(R.drawable.whatsappicono, "Item 4"),
            ItemAdapter.Item(R.drawable.webdev, "Item 5")
        )

        val adapter = ItemAdapter(items)
        recyclerView.adapter = adapter
        tvPortatiles.setOnClickListener {
            resetView()
            view.visibility= View.VISIBLE
        }
        tvOrdenadores.setOnClickListener {
            resetView()
            view2.visibility= View.VISIBLE
        }
        tvSmartphones.setOnClickListener {
            resetView()
            view3.visibility= View.VISIBLE
        }
        tvTelevisores.setOnClickListener {
            resetView()
            view4.visibility= View.VISIBLE
        }
        tvMonitores.setOnClickListener {
            resetView()
            view5.visibility= View.VISIBLE
        }

    }
    fun resetView(){
        view.visibility = View.GONE
        view2.visibility = View.GONE
        view3.visibility = View.GONE
        view4.visibility = View.GONE
        view5.visibility = View.GONE

    }
}