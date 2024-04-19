package com.example.sysmeet

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth

class LogoutActivity : AppCompatActivity() {
    private lateinit var buttonLogout: Button
    private lateinit var buttonReturn: Button

    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_logout)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

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
            val logoutToSettingIntent = Intent(this, SettingActivity::class.java)
            startActivity(logoutToSettingIntent)
        }
    }
}