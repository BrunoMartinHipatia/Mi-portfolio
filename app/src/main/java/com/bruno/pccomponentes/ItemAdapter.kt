package com.bruno.pccomponentes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bruno.R

class ItemAdapter(private val items: List<Item>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    data class Item(val imageResId: Int, val text: String)

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.itemImageView)
        val textView: TextView = itemView.findViewById(R.id.itemTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.imageView.setImageResource(item.imageResId)
        holder.textView.text = item.text
    }

    override fun getItemCount(): Int = items.size
}
