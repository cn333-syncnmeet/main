package com.example.sysmeet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.sysmeet.databinding.ActivityLoginBinding
import com.example.sysmeet.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.Create.setOnClickListener {
            val intent = Intent(this , SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.Login.setOnClickListener {
            val email = binding.EmailAddress.text.toString()
            val pass = binding.Password.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
<<<<<<< HEAD
                            val intent = Intent(this, MainActivity::class.java)
=======
                            val intent = Intent(this, SignUpActivity::class.java)
>>>>>>> origin/P
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, task.exception?.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
            }else{
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onStart() {
        super.onStart()
<<<<<<< HEAD
        firebaseAuth = FirebaseAuth.getInstance() // อย่าลืมกำหนด firebaseAuth ให้ตัวแปรก่อนใช้งาน

        if (firebaseAuth.currentUser != null && javaClass.name == LoginActivity::class.java.name) {
=======
        if (this::firebaseAuth.isInitialized && firebaseAuth.currentUser != null && this::javaClass.name == LoginActivity::class.java.name) {
>>>>>>> origin/P
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}
