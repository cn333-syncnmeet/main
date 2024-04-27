package com.example.sysmeet

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

//background
private lateinit var view: View
//divider
private lateinit var divider1: View
private lateinit var divider2: View
//text
private lateinit var textAbout: TextView
private lateinit var textWhatIs: TextView
private lateinit var textWhat1: TextView
private lateinit var textWhat2: TextView
private lateinit var textMadeBy: TextView
private lateinit var textMade1: TextView
private lateinit var textMade2: TextView
private lateinit var textMade3: TextView
private lateinit var textMade4: TextView
private lateinit var textMade5: TextView

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_about)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //get element
        getElement()

        //set color
        setColor()

        //set language
        //setLanguage()
    }

    fun getElement() {
       // = findViewById(R.id.)
        view = findViewById(R.id.main)
        divider1 = findViewById(R.id.divider1)
        divider2 = findViewById(R.id.divider2)
        textAbout = findViewById(R.id.textAbout)
        textWhat1 = findViewById(R.id.textWhat1)
        textMade1 = findViewById(R.id.textMade1)
        textMade2 = findViewById(R.id.textMade2)
        textMade3 = findViewById(R.id.textMade3)
        textMade4 = findViewById(R.id.textMade4)
        textMade5 = findViewById(R.id.textMade5)
        textMadeBy = findViewById(R.id.textMadeBy)
        textWhat2 = findViewById(R.id.textWhat2)
        textWhatIs = findViewById(R.id.textWhatIs)
    }

    fun setColor() {
        //do something similar to this for every page?

        //get prefs
        var preferences: SharedPreferences = getSharedPreferences("settingPrefs", MODE_PRIVATE)

        //get color
        var color1 = preferences.getInt("color1", Color.rgb(64, 124, 135)) //dark blue
        var color2 = preferences.getInt("color2", Color.rgb(165, 219, 221)) // blue
        var color3 = preferences.getInt("color3", Color.rgb(238, 241, 246)) // white
        var color4 = preferences.getInt("color4", Color.rgb(211, 225, 226)) // light blue

        //set color
        //view = findViewById(R.id.main)
        view.setBackgroundColor(color4) //use this function for all element? (button, divider, etc.)

        divider1.setBackgroundColor(color1)
        divider2.setBackgroundColor(color1)

        textAbout.setTextColor(color1)
        textWhat1.setTextColor(color1)
        textMade1.setTextColor(color1)
        textMade2.setTextColor(color1)
        textMade3.setTextColor(color1)
        textMade4.setTextColor(color1)
        textMade5.setTextColor(color1)
        textMadeBy.setTextColor(color1)
        textWhat2.setTextColor(color1)
        textWhatIs.setTextColor(color1)

    }
}