package com.example.sysmeet

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.sysmeet.databinding.ActivityEditScheduleBinding
import com.example.sysmeet.ui.theme.SysmeetTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Upload_Profile_Activity : ComponentActivity() {

    private lateinit var userName: EditText
    private lateinit var userGender: Spinner
    private lateinit var userAge: EditText
    private lateinit var userOther: EditText

    private lateinit var saveButton: Button
    private lateinit var firebaseAuth: FirebaseAuth

    /*private var _binding: Upload_Profile_Activity? = null
    private val binding get() = _binding!!

    private lateinit var getContent: ActivityResultLauncher<String> */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_profile)

        firebaseAuth = FirebaseAuth.getInstance()

        userName = findViewById(R.id.editTextName)
        userGender = findViewById(R.id.spinnerGender)
        userAge = findViewById(R.id.editTextAge)
        userOther = findViewById(R.id.editTextOtherInfo)

        saveButton = findViewById(R.id.buttonUploadProfile)

        /*_binding = Upload_Profile_Activity.inflate(layoutInflater)
        setContentView(binding.root)

        getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let { imageUri ->
                // Set the selected image to ImageView
                binding.imageProfile.setImageURI(imageUri)
            }
        }

        binding.buttonSelectImage.setOnClickListener {
            getContent.launch("image/*")
        }

         */
        */



        saveButton.setOnClickListener {

            val userName = userName.text.toString().toStringOrNull() ?: "--"
            val userGender = userGender.selectedItem.toString().toStringOrNull() ?: "--"
            val userAge = userAge.text.toString().toStringOrNull() ?: "--"
            val userOther = userOther.text.toString().toStringOrNull() ?: "--"

            Toast.makeText(this, "Upload profile successfully", Toast.LENGTH_SHORT).show()

            saveDataToDatabase(userName,userGender,userAge,userOther)
            finish()
        }

    }

    // Function to save data to database
    private fun saveDataToDatabase(userName:String,userGender: String, userAge: String,userOther: String) {

        // เชื่อมต่อกับ Realtime Database
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef: DatabaseReference = database.reference
        val currentUser = firebaseAuth.currentUser

        val dataMap = HashMap<String, Any>()
        dataMap["userID"] = currentUser!!.uid
        dataMap["userName"] = userName
        dataMap["userGender"] = userGender
        dataMap["userAge"] = userAge
        dataMap["userOther"] = userOther

        // บันทึกข้อมูลลงใน Realtime Database
        myRef.child("Users").child(currentUser!!.uid).child("Profile").push().setValue(dataMap)
    }

    fun String?.toStringOrNull(): String? {
        return this?.toString()
    }

}