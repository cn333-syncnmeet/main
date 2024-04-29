package com.example.sysmeet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sysmeet.R
import com.example.sysmeet.Upload_Profile_Activity
import com.example.sysmeet.databinding.FragmentHomeBinding
import com.example.sysmeet.databinding.FragmentHomeMenuBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeFragment : Fragment() {

    private lateinit var firebaseAuth: FirebaseAuth
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find the FloatingActionButton
        val addButton: FloatingActionButton = view.findViewById(R.id.Add)

        // Set OnClickListener
        addButton.setOnClickListener {
            // Create Intent to navigate to Upload_Profile_Activity
            val intent = Intent(activity, Upload_Profile_Activity::class.java)

            // Start the activity
            startActivity(intent)
        }

        firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = FirebaseAuth.getInstance().currentUser

        // อ้างอิงไปยัง "Groups" ใน Firebase Database
        val databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(currentUser!!.uid).child("Profile")

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                val nameText = StringBuilder()
                val genderText = StringBuilder()
                val ageText = StringBuilder()
                val otherText = StringBuilder()

                // วนลูปเพื่อดึงข้อมูลจากทุกๆ child ใน "Groups"
                for (groupSnapshot in dataSnapshot.children) {
                    val age = groupSnapshot.child("userAge").getValue(String::class.java)
                    val gender = groupSnapshot.child("userGender").getValue(String::class.java)
                    val id = groupSnapshot.child("userID").getValue(String::class.java)
                    val name = groupSnapshot.child("userName").getValue(String::class.java)
                    val other = groupSnapshot.child("userOther").getValue(String::class.java)


                    Log.d("TAG", "Age: $age, Gender: $gender, ID: $id, Name: $name, Other: $other")

                    // Check for null before using the values
                    if (name != null && gender != null && age != null && other != null) {
                        nameText.append(name)
                        genderText.append(gender)
                        ageText.append(age)
                        otherText.append(other)

                        binding.userName.text = name
                        binding.userGender.text = gender
                        binding.userAge.text = age
                        binding.editOtherInfo.text = other
                    }

                }

                // กำหนดข้อความที่ได้ใน TextView

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // การดึงข้อมูลล้มเหลว
                Log.e("HomeFragment", "Failed to read value.", databaseError.toException())
            }
        })
    }
}