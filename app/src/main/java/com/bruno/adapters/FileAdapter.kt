package com.bruno.files

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bruno.R
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

data class FileItem(val name: String, val iconPath: String)

class FileAdapter(private val context: Context, private val items: List<FileItem>) : BaseAdapter() {

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): FileItem = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.item_file, parent, false)


        val textView: TextView = view.findViewById(R.id.file_name)

        val iconImageView: ImageView = view.findViewById(R.id.file_icon)
        val assetManager = context.assets
        val currentItem = getItem(position)
        val inputStream = assetManager.open(currentItem.iconPath)
        textView.text = currentItem.name
        val drawable = Drawable.createFromStream(inputStream, null)
        iconImageView.setImageDrawable(drawable)


        return view
    }
}
