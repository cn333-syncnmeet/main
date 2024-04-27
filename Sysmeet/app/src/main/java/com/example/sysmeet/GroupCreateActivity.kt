package com.example.sysmeet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.sysmeet.ui.theme.SysmeetTheme
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class GroupCreateActivity : AppCompatActivity() {

    private lateinit var groupNameEditText: EditText // เพิ่มบรรทัดนี้

    private lateinit var createGroupButton: Button
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase

    private val groupList: MutableList<String> = mutableListOf()


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_create)

        // Initialize views
        createGroupButton = findViewById(R.id.createGroupButton)
        groupNameEditText = findViewById(R.id.groupNameEditText)

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance()

        // Initialize Firebase Database
        firebaseDatabase = FirebaseDatabase.getInstance()

        // Find views
        createGroupButton = findViewById(R.id.createGroupButton)

        // Set click listener for create group button
        createGroupButton.setOnClickListener {
            createGroup()
        }
    }

    private fun createGroup() {
        val currentUser = firebaseAuth.currentUser
        val groupName = groupNameEditText.text.toString()
        val groupRef = firebaseDatabase.reference.child("Groups").push()

        // Check if user is signed in
        if (currentUser != null) {
            // Create group in database
            val groupData = hashMapOf(
                "groupName" to groupName,
                "groupAdmin" to currentUser.uid
            )
            groupRef.setValue(groupData)
                .addOnSuccessListener {
                    // Group created successfully, navigate to GroupFragment with group name
                    val fragment = GroupFragment()
                    val bundle = Bundle()
                    bundle.putString("groupName", groupName)
                    fragment.arguments = bundle

                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .commit()
                }
                .addOnFailureListener { exception ->
                    // Handle errors
                }
        }
        // Add group name to GroupList
        groupList.add(groupName)
    }
}
