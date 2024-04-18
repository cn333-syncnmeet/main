package com.example.sysmeet

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SettingActivity : AppCompatActivity() {
    private lateinit var view: View
    private lateinit var divider1: View
    private lateinit var divider2: View
    private lateinit var divider3: View
    private lateinit var divider4: View
    private lateinit var divider5: View
    private lateinit var testButton: Button
    private lateinit var buttonAbout: Button
    private lateinit var buttonLogout: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_setting)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //get prefs
        var preferences:SharedPreferences = getSharedPreferences("settingPrefs", MODE_PRIVATE)
        var editor:SharedPreferences.Editor = preferences.edit()

        //get color
        var color1 = preferences.getInt("color2", Color.rgb(64, 124, 135)) //dark blue
        var color2 = preferences.getInt("color2", Color.rgb(165, 219, 221)) // blue
        var color3 = preferences.getInt("color2", Color.rgb(238, 241, 246)) // white
        var color4 = preferences.getInt("color2", Color.rgb(211, 225, 226)) // light blue

        //set color
        view = findViewById(R.id.main)
        view.setBackgroundColor(color4) //use this function for all element? (button, divider, etc.)

        divider1 = findViewById(R.id.divider1)
        divider1.setBackgroundColor(color1)
        divider2 = findViewById(R.id.divider2)
        divider2.setBackgroundColor(color1)
        divider3 = findViewById(R.id.divider3)
        divider3.setBackgroundColor(color1)
        divider4 = findViewById(R.id.divider4)
        divider4.setBackgroundColor(color1)
        divider5 = findViewById(R.id.divider5)
        divider5.setBackgroundColor(color1)

        buttonAbout = findViewById(R.id.buttonAbout)
        buttonAbout.setBackgroundColor(color2)
        buttonLogout = findViewById(R.id.buttonLogout)
        buttonLogout.setBackgroundColor(Color.RED)

        testButton = findViewById(R.id.testButton)
        testButton.setOnClickListener {
            editor.putInt("color1", Color.RED)
        }
    }
}