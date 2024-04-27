package com.example.sysmeet

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class EditScheduleActivity : AppCompatActivity() {

    private lateinit var daySpinner: Spinner
    private lateinit var startTimeEditText: Spinner
    private lateinit var endTimeEditText: Spinner

    private lateinit var saveButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_schedule)

        daySpinner = findViewById(R.id.daySpinner)
        startTimeEditText = findViewById(R.id.startTimeEditText)
        endTimeEditText = findViewById(R.id.endTimeEditText)

        saveButton = findViewById(R.id.saveButton)

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

                // Finish activity
                finish()
            } else {
                // Display error message if input is empty
                Toast.makeText(this, "Please enter start and end time", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Function to save data to database
    private fun saveDataToDatabase(startTime: String, endTime: String) {
        // Implement your database logic here
    }
}