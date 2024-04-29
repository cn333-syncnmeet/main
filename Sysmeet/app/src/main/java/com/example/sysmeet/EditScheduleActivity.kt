package com.example.sysmeet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sysmeet.databinding.ActivityEditScheduleBinding
import com.example.sysmeet.databinding.FragmentHomeBinding
import com.example.sysmeet.databinding.FragmentMembersMenuBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class EditScheduleActivity : AppCompatActivity() {

    private lateinit var daySpinner: Spinner
    private lateinit var startTimeEditText: Spinner
    private lateinit var endTimeEditText: Spinner

    private lateinit var saveButton: Button
    private lateinit var firebaseAuth: FirebaseAuth

    private var _binding: ActivityEditScheduleBinding? = null
    private val binding get() = _binding!!

    private lateinit var scheduleListTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityEditScheduleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setContentView(R.layout.activity_edit_schedule)
        firebaseAuth = FirebaseAuth.getInstance()

        daySpinner = findViewById(R.id.daySpinner)
        startTimeEditText = findViewById(R.id.startTimeEditText)
        endTimeEditText = findViewById(R.id.endTimeEditText)

        saveButton = findViewById(R.id.saveButton)

        scheduleListTextView = findViewById(R.id.schedulelist)

        saveButton.setOnClickListener {

            val selectedDay = daySpinner.selectedItem.toString()
            val startTime = startTimeEditText.selectedItem.toString()
            val endTime = endTimeEditText.selectedItem.toString()

            // Validate input
            if (startTime.isNotEmpty() && endTime.isNotEmpty()) {
                // Save data to database or perform necessary action
                // Example: saveDataToDatabase(startTime, endTime)

                // Display success message
                Toast.makeText(this, "Schedule saved successfully", Toast.LENGTH_SHORT).show()
                saveDataToDatabase(selectedDay,startTime,endTime)

                // Finish activity
                finish()
            } else {
                // Display error message if input is empty
                Toast.makeText(this, "Please enter start and end time", Toast.LENGTH_SHORT).show()
            }
        }

        val currentUser = FirebaseAuth.getInstance().currentUser
        val databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(currentUser!!.uid).child("Schedule")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val stringBuilder = StringBuilder()

                // วนลูปเพื่อดึงข้อมูลจากทุกๆ child ใน "Groups"
                for (groupSnapshot in dataSnapshot.children) {
                    val date = groupSnapshot.child("date").getValue(String::class.java).toString()
                    val end = groupSnapshot.child("endTime").getValue(String::class.java).toString()
                    val start = groupSnapshot.child("startTime").getValue(String::class.java).toString()

                    stringBuilder.append(date).append(": ").append(start).append(" - ").append(end).append("\n").append("\n")
                }

                // ตั้งค่าข้อความใน TextView
                scheduleListTextView.text = stringBuilder.toString()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // การดึงข้อมูลล้มเหลว
                Log.e("ScheduleFragment", "Failed to read value.", databaseError.toException())
            }
        })
    }

    // เมธอด onDestroy จำเป็นต้องถูกเพิ่มเพื่อปิดการใช้งาน _binding เพื่อป้องกันการหลุดหน่วยความจำ
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    // Function to save data to database
    private fun saveDataToDatabase(date:String,startTime: String, endTime: String) {
        // เชื่อมต่อกับ Realtime Database
        val database: FirebaseDatabase = FirebaseDatabase.getInstance()
        val myRef: DatabaseReference = database.reference
        val currentUser = firebaseAuth.currentUser

        val dataMap = HashMap<String, Any>()
        dataMap["userID"] = currentUser!!.uid
        dataMap["date"] = date
        dataMap["startTime"] = startTime
        dataMap["endTime"] = endTime

        // บันทึกข้อมูลลงใน Realtime Database
        myRef.child("Users").child(currentUser!!.uid).child("Schedule").push().setValue(dataMap)
    }
}