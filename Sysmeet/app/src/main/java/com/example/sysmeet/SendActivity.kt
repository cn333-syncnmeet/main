package com.example.sysmeet

import android.os.Bundle

import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

import com.example.sysmeet.databinding.ActivitySendBinding

class SendActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySendBinding
    private lateinit var database: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySendBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase database
        database = FirebaseDatabase.getInstance().reference

        setContentView(R.layout.activity_send)

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance()

        // Get data from intent
        val groupChat = intent.getStringExtra("groupChat")
        val currentUser = firebaseAuth.currentUser
        val groupName = intent.getStringExtra("groupName")


        sendMessageToFirebase(groupChat, currentUser.toString(), groupName)
    }

    private fun sendMessageToFirebase(message: String?, user: String?, groupName: String?) {
        // Check if any of the required data is null
        if (message.isNullOrEmpty() || user.isNullOrEmpty() || groupName.isNullOrEmpty()) {
            // Handle the error
            Toast.makeText(this, "Chat send error", Toast.LENGTH_SHORT).show()
            return
        }

        // Create a unique key for the message
        val messageKey = database.child("Groups").child(groupName!!).push().key

        // Create a HashMap to store the message details
        val messageDetails = HashMap<String, Any>()
        messageDetails["message"] = message
        messageDetails["user"] = user

        // Push the message to the Firebase Realtime Database
        if (messageKey != null) {
            database.child("Groups").child(groupName).child(messageKey).setValue(messageDetails)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Chat send successfully", Toast.LENGTH_SHORT).show()
                        // Finish activity
                        finish()
                    } else {
                        // Handle the error
                        Toast.makeText(this, "Error: " + task.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
