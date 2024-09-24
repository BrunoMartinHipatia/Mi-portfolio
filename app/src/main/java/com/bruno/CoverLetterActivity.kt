package com.bruno

import android.content.Intent

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bruno.R
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.link.DefaultLinkHandler
import com.shockwave.pdfium.PdfDocument

class CoverLetterActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.coverletter)
        var pdfView: PDFView
        pdfView = findViewById(R.id.pdf)
        pdfView.fromAsset("Bruno_Martin.pdf").linkHandler(DefaultLinkHandler(pdfView))

            .enableAnnotationRendering(true)
            .enableSwipe(true)
            .swipeHorizontal(false)
            .enableDoubletap(true)
            .defaultPage(0)
            .load()

    }

}