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
    private lateinit var testButton: Button
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
        var color1 = preferences.getInt("color1", Color.BLUE)
        //set color
        view = findViewById(R.id.main)
        view.setBackgroundColor(color1)

        testButton = findViewById(R.id.testButton)
        testButton.setOnClickListener() {
            editor.putInt("color1", Color.RED)
        }
    }
}