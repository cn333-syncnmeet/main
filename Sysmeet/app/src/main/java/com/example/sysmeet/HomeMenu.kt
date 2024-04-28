package com.example.sysmeet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.sysmeet.databinding.FragmentHomeMenuBinding
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class HomeMenu : Fragment() {

    private var _binding: FragmentHomeMenuBinding? = null
    private val binding get() = _binding!!

    private lateinit var databaseReference: DatabaseReference
    private lateinit var groupName: TextView // ประกาศตัวแปร groupName ใน scope ของ Fragment

    private lateinit var database: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeMenuBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.sendGroupButton.setOnClickListener {
            val groupChat = binding.groupChatEditText.text.toString()

            // ใช้ binding เพื่อเข้าถึง textView ใน layout
            val groupName = binding.groupName.text.toString()

            val intent = Intent(requireContext(), SendActivity::class.java)
            intent.putExtra("groupChat", groupChat)
            intent.putExtra("groupName", groupName)
            startActivity(intent)
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // เข้าถึงข้อมูล textGroup ที่ถูกส่งมาผ่าน arguments
        val textGroup = arguments?.getString("textGroup")

        // กำหนดค่าให้กับ groupName เท่ากับ textGroup
        binding.groupName.text = textGroup


        firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = FirebaseAuth.getInstance().currentUser

        // อ้างอิงไปยัง "Groups" ใน Firebase Database
        val databaseReference = FirebaseDatabase.getInstance().getReference("Groups").child(binding.groupName.text.toString()).child("Chat")

        // เพิ่ม Listener สำหรับการอ่านข้อมูล
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val stringBuilder = StringBuilder()
                val stringBuilderMenber = StringBuilder()

                // วนลูปเพื่อดึงข้อมูลจากทุกๆ child ใน "Groups"
                for (groupSnapshot in dataSnapshot.children) {
                    // อ่านค่าของ message จาก child นั้น ๆ
                    val message = groupSnapshot.child("message").getValue(String::class.java)
                    val userId = groupSnapshot.child("userId").getValue(String::class.java)
                    // ตรวจสอบว่า message ไม่เท่ากับ null และไม่ว่างเปล่า
                    if (!message.isNullOrEmpty()) {
                        if(userId == currentUser!!.uid)
                        {
                            stringBuilder.append(message).append("\n").append("\n")
                            stringBuilderMenber.append("\n").append("\n")
                        }
                        // เพิ่มข้อความลงใน StringBuilder
                        else
                        {
                            stringBuilderMenber.append(message).append("\n").append("\n")
                            stringBuilder.append("\n").append("\n")
                        }
                    }
                }

                // กำหนดข้อความที่ได้ใน TextView
                binding.groupChatUser.text = stringBuilder.toString()
                binding.groupChatMember.text = stringBuilderMenber.toString()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // การดึงข้อมูลล้มเหลว
                Log.e("HomeMenu", "Failed to read value.", databaseError.toException())
            }
        })
    }
}

