package com.example.sysmeet

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.sysmeet.databinding.ActivitySendBinding
import com.example.sysmeet.ui.theme.SysmeetTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AddMemberActivity : ComponentActivity() {

    private lateinit var binding: ActivitySendBinding
    private lateinit var database: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySendBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase database
        database = FirebaseDatabase.getInstance().reference


        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance()

        // Get data from intent
        val member = intent.getStringExtra("addMember")
        val currentUser = firebaseAuth.currentUser
        val groupName = intent.getStringExtra("groupName")

        val databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                // วนลูปเพื่อดึงข้อมูลจากทุกๆ child ใน "Groups"
                for (groupSnapshot in dataSnapshot.children) {
                    val name = groupSnapshot.child("Profile").child("userName")
                        .getValue(String::class.java)

                    Log.d("TAG_mem", "name: $name, mem: $member")

                    if(name.toString() == member)
                    {
                        addMember(member, currentUser!!.uid, groupName)
                    }
                }

            }
            override fun onCancelled(databaseError: DatabaseError) {
                // การดึงข้อมูลล้มเหลว
                Log.e("HomeFragment", "Failed to read value.", databaseError.toException())
            }
        })



    }

    private fun addMember(member: String?, user: String?, groupName: String?) {
        // Check if any of the required data is null
        Log.d("TAG", "member: $member, user: $user, groupName: $groupName")

        if (member.isNullOrEmpty() || user.isNullOrEmpty() || groupName.isNullOrEmpty()) {
            // Handle the error
            Toast.makeText(this, "Add Member error", Toast.LENGTH_SHORT).show()
            return
        }

        // Create a unique key for the message
        val memberKey = database.child("Groups").child(groupName!!).push().key

        // Create a HashMap to store the message details
        val messageDetails = HashMap<String, Any>()
        messageDetails["userMember"] = member

        // Push the message to the Firebase Realtime Database
        if (memberKey != null) {
            database.child("Groups").child(groupName).child("Members").child(memberKey).setValue(messageDetails)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Add Members successfully", Toast.LENGTH_SHORT).show()
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