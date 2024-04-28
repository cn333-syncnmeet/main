package com.example.sysmeet

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth


class LogoutActivity : AppCompatActivity() {
    //button
    private lateinit var buttonLogout: Button
    private lateinit var buttonReturn: Button

    private lateinit var firebaseAuth: FirebaseAuth

    //background
    private lateinit var view: View
    //divider
    private lateinit var divider1: View
    //text
    private lateinit var textDoYou: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_logout)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //get element
        getElement()

        //set color
        setColor()

        //logout button
        buttonLogout = findViewById(R.id.buttonLogout)
        buttonLogout.setOnClickListener {
            //sign user out
            firebaseAuth = FirebaseAuth.getInstance()
            firebaseAuth.signOut()
            //go to login page
            val logoutToLoginIntent = Intent(this, LoginActivity::class.java)
            startActivity(logoutToLoginIntent)
        }

        //return button
        buttonReturn = findViewById(R.id.buttonReturn)
        buttonReturn.setOnClickListener {
            //go to setting page
            finish()
        }


        }

    private fun getElement() {
        // = findViewById(R.id.)
        view = findViewById(R.id.main)
        divider1 = findViewById(R.id.divider1)
        textDoYou = findViewById(R.id.textDoYou)
        buttonLogout = findViewById(R.id.buttonLogout)
        buttonReturn = findViewById(R.id.buttonReturn)

    }

    private fun setColor() {
        //do something similar to this for every page?

        //get prefs
        val preferences: SharedPreferences = getSharedPreferences("settingPrefs", MODE_PRIVATE)

        //get color
        val color1 = preferences.getInt("color1", Color.rgb(64, 124, 135)) //dark blue
        val color4 = preferences.getInt("color4", Color.rgb(211, 225, 226)) // light blue

        //set color
        //.setBackgroundColor(color)
        view.setBackgroundColor(color4) //use this function for all element? (button, divider, etc.)

        divider1.setBackgroundColor(color1)

        textDoYou.setTextColor(color1)

        buttonLogout.setBackgroundColor(Color.WHITE)
        buttonReturn.setBackgroundColor(Color.RED)
    }
}