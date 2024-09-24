package com.bruno;


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager.widget.ViewPager
import com.app.atsz7.viewpagerautoscroll.extensions.autoScroll
import com.app.atsz7.viewpagerautoscroll.ui.adapters.ViewPagerAdapter
import com.google.android.material.navigation.NavigationView

class AboutMeActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var adapter: ViewPagerAdapter
    private lateinit var llLink: LinearLayout
    private lateinit var llInsta: LinearLayout
    private lateinit var llGit: LinearLayout
    private lateinit var llWhat: LinearLayout
    private lateinit var imgLink: ImageView
    private lateinit var imgInsta: ImageView
    private lateinit var imgGit: ImageView
    private lateinit var imgWhat: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about_me_activity)
        toolbar = findViewById(R.id.toolbar)
        toolbar.title = ""
        setSupportActionBar(toolbar)
        llLink = findViewById(R.id.llLinkedin)
        llInsta = findViewById(R.id.llInsta)
        llGit = findViewById(R.id.llGit)
        llWhat = findViewById(R.id.llWhatsapp)
        imgLink = findViewById(R.id.imgLinkedin)
        imgInsta = findViewById(R.id.imgInsta)
        imgGit = findViewById(R.id.imgGit)
        imgWhat = findViewById(R.id.imgTele)
        drawerLayout = findViewById(R.id.drawer_layout)
        setupNavigation(toolbar);
        setAdapter()
        setViewPager()

        llLink.setOnClickListener{
            val url = "https://www.linkedin.com/in/bruno-martin-hermoso-46b2401a0/"


            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)

            startActivity(intent)

        }
        llInsta.setOnClickListener{
            val url = "https://www.instagram.com/brunomh_2002/"


            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)

            startActivity(intent)

        }
        llGit.setOnClickListener{
            val url = "https://github.com/BrunoMartinHipatia"


            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)

            startActivity(intent)

        }
        llWhat.setOnClickListener{
                val url = "https://wa.me/601161964"


            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)

            startActivity(intent)

        }


    }
    private fun setAdapter() {

        this.adapter = ViewPagerAdapter(listOf(
            R.drawable.webdev,
            R.drawable.androiddev,
            R.drawable.javakot,

        ))
    }



    private fun setViewPager() {
        val viewPager = findViewById<ViewPager>(R.id.viewPager)
        viewPager.adapter = adapter


        viewPager.autoScroll(3000)
    }
    fun setupNavigation(toolbar: Toolbar){
        val toogle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav)
        drawerLayout.addDrawerListener(toogle)
        toogle.syncState()
        navigationView = findViewById<NavigationView>(R.id.nav_view)

        val menuItem = navigationView.menu.getItem(0)
        navigationView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.casa ->goCasa()
            }
            true
        }
        menuItem.setChecked(true)

    }
    fun goCasa(){
     //   val intent = Intent(this, MainActivity::class.java)
       // startActivity(intent)
    }

}
