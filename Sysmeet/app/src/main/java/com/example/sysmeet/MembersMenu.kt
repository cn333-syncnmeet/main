package com.example.sysmeet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.sysmeet.databinding.FragmentHomeMenuBinding
import com.example.sysmeet.databinding.FragmentMembersMenuBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MembersMenu.newInstance] factory method to
 * create an instance of this fragment.
 */
class MembersMenu : Fragment() {

    private var _binding: FragmentMembersMenuBinding? = null
    private val binding get() = _binding!!

    private lateinit var databaseReference: DatabaseReference
    private lateinit var groupName: TextView // ประกาศตัวแปร groupName ใน scope ของ Fragment

    private lateinit var database: DatabaseReference
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMembersMenuBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.addMember.setOnClickListener {
            val addMember = binding.memberEditText.text.toString()
            val textGroup = arguments?.getString("textGroup")

            // กำหนดค่าให้กับ groupName เท่ากับ textGroup
            binding.groupMember.text = textGroup

            // ใช้ binding เพื่อเข้าถึง textView ใน layout
            val groupName = binding.groupMember.text.toString()

            val intent = Intent(requireContext(), AddMemberActivity::class.java)
            intent.putExtra("addMember", addMember)
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
        binding.groupMember.text = textGroup

        firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = FirebaseAuth.getInstance().currentUser

        // อ้างอิงไปยัง "Groups" ใน Firebase Database
        val databaseReference = FirebaseDatabase.getInstance().getReference("Groups").child(binding.groupMember.text.toString()).child("Members")

        // เพิ่ม Listener สำหรับการอ่านข้อมูล
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val stringBuilder = StringBuilder()
                stringBuilder.append("You").append("\n").append("\n")

                // วนลูปเพื่อดึงข้อมูลจากทุกๆ child ใน "Groups"
                for (groupSnapshot in dataSnapshot.children) {
                    // อ่านค่าของ message จาก child นั้น ๆ
                    val member = groupSnapshot.child("userMember").getValue(String::class.java)
                    // ตรวจสอบว่า message ไม่เท่ากับ null และไม่ว่างเปล่า
                    if (!member.isNullOrEmpty()) {
                        stringBuilder.append(member).append("\n").append("\n")
                        // เพิ่มข้อความลงใน StringBuilder
                    }
                }

                // กำหนดข้อความที่ได้ใน TextView
                binding.members.text = stringBuilder.toString()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // การดึงข้อมูลล้มเหลว
                Log.e("MembersMenu", "Failed to read value.", databaseError.toException())
            }
        })
    }
}