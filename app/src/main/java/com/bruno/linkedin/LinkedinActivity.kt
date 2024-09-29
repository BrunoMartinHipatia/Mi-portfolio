package com.bruno.linkedin

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.bruno.R
import org.json.JSONException
import org.json.JSONObject


class LinkedinActivity : AppCompatActivity() {
    lateinit var image: ImageView
    lateinit var like: ImageView
    lateinit var rtPulsado: ImageView
    lateinit var liketext: TextView
    lateinit var rttext: TextView
    lateinit var rt: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.linkedin)
        image = findViewById(R.id.chaticon)
        rt = findViewById(R.id.rt)
        like = findViewById(R.id.like)
        rttext = findViewById(R.id.numerort)
        rtPulsado = findViewById(R.id.rtpulsado)
        liketext = findViewById(R.id.numerolikes)

        enableEdgeToEdge()




        image.setOnClickListener{
            var intent = Intent(applicationContext, LinkedingPulsadoActivity::class.java)
            startActivity(intent)
        }
        rt.setOnClickListener {
                Log.d("like", "like")
                val rotateAnimator = ObjectAnimator.ofFloat(rt, "rotation", 0f, 360f)
                rotateAnimator.setDuration(500)

                var colorAnimator =
                    ObjectAnimator.ofArgb(rt, "colorFilter", Color.TRANSPARENT, Color.BLACK)
                colorAnimator.setDuration(10)
                colorAnimator.setEvaluator(ArgbEvaluator())
                colorAnimator.startDelay = 10

                val animatorSet2 = AnimatorSet()
                animatorSet2.playTogether(rotateAnimator, colorAnimator)
                animatorSet2.start()

                colorAnimator =
                    ObjectAnimator.ofArgb(rt, "colorFilter", Color.TRANSPARENT, Color.GREEN)
                colorAnimator.setDuration(500)
                colorAnimator.setEvaluator( ArgbEvaluator())
                colorAnimator.startDelay = 0

                val animatorSet = AnimatorSet()
                animatorSet.playTogether(rotateAnimator, colorAnimator)
                animatorSet.start()

            rtPulsado.visibility = View.VISIBLE
            rttext.text = 1.toString()


        }


        liketext.text= liketext.text
        like.setOnClickListener {
            Log.d("like", "like")

            // Animación de escala
            val scaleXAnimator = ObjectAnimator.ofFloat(like, "scaleX", 1f, 1.5f, 1f)
            val scaleYAnimator = ObjectAnimator.ofFloat(like, "scaleY", 1f, 1.5f, 1f)

            // Configuración de duración
            scaleXAnimator.duration = 350
            scaleYAnimator.duration = 350

            // Crear un AnimatorSet para que las animaciones se ejecuten en conjunto
            val animatorSet = AnimatorSet()
            animatorSet.playTogether(scaleXAnimator, scaleYAnimator)

            // Cambiar la imagen durante la animación
            val valueAnimator = ValueAnimator.ofFloat(0f, 1f)
            valueAnimator.duration = 350
            valueAnimator.addUpdateListener { animation ->
                val fraction = animation.animatedFraction
                // Cambia la imagen basándote en la fracción de la animación
                if (fraction < 0.5f) {
                    like.setImageResource(R.drawable.likeicon) // Imagen original
                } else {
                    like.setImageResource(R.drawable.likeiconpulsado) // Imagen pulsada
                }
            }

            val numeroLike = 424
            liketext.text = numeroLike.toString()
            // Iniciar las animaciones
            animatorSet.start()
            valueAnimator.start()
        }


    }
}