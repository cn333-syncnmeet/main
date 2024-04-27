package com.example.sysmeet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.content.Intent

import android.widget.Button

class ScheduleFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set OnClickListener for the Edit button
        view.findViewById<Button>(R.id.editButton).setOnClickListener {
            // Start EditScheduleActivity
            val intent = Intent(activity, EditScheduleActivity::class.java)
            startActivity(intent)
        }

        // Update the schedule data here, display any saved data in the schedule table
        // Example: updateScheduleTable(savedData)
    }

    // Function to update the schedule table with saved data
    //private fun updateScheduleTable(savedData: List<ScheduleItem>) {
        // Implement your logic to update the schedule table here
    //}
}