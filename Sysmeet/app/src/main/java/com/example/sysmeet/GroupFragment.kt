package com.example.sysmeet

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.sysmeet.databinding.FragmentGroupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.*

class GroupFragment : Fragment() {

    private var _binding: FragmentGroupBinding? = null
    private val binding get() = _binding!!

    private var currentGroupName: String? = null

    private val currentUser: FirebaseUser? = FirebaseAuth.getInstance().currentUser
    private val groupRef: DatabaseReference = FirebaseDatabase.getInstance().reference.child("Groups")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGroupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Get group name from arguments
        val groupName = arguments?.getString("groupName")
        groupName?.let { setCurrentGroupName(it) }

        // Set initial fragment


        // Handle bottom navigation item clicks
        binding.groupBottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home_menu -> {

                    if (binding.textGroup.text != "Group") {
                        replaceFragment(HomeMenu())
                    }


                    return@setOnNavigationItemSelectedListener true
                }
                R.id.schedule_menu -> {

                    if (binding.textGroup.text != "Group") {
                        replaceFragment(ScheduleMenu())
                    }

                    return@setOnNavigationItemSelectedListener true
                }
                R.id.members_menu -> {

                    if (binding.textGroup.text != "Group") {
                        replaceFragment(MembersMenu())
                    }
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
        }

        binding.Add.setOnClickListener {
            // Navigate to GroupCreateActivity
            val intent = Intent(requireContext(), GroupCreateActivity::class.java)
            startActivity(intent)
        }
        /*
        // Handle FloatingActionButton clicks
        binding.Group1.setOnClickListener {
            // Handle click for Group_1
            setCurrentGroupName("Group 1") // Set the current group name
        }

        binding.Group2.setOnClickListener {
            // Handle click for Group_2
            setCurrentGroupName("Group 2") // Set the current group name
        }*/


        //val groupName = "Test" // ชื่อกลุ่มที่ต้องการตรวจสอบ
        checkIfUserIsMemberOrAdmin("Test") { isAdmin ->
            if (isAdmin) {
                // ผู้ใช้งานเป็น groupAdmin
                // ทำสิ่งที่คุณต้องการทำเมื่อผู้ใช้งานเป็น groupAdmin ที่นี่
                binding.Group1.setOnClickListener {
                    binding.textGroup.text = "Test" }
            } else {
                // ผู้ใช้งานไม่ได้เป็น groupAdmin
                // ทำสิ่งที่คุณต้องการทำเมื่อผู้ใช้งานไม่ได้เป็น groupAdmin ที่นี่
                binding.Group1.visibility = View.GONE
            }
        }

        checkIfUserIsMemberOrAdmin("Test2") { isAdmin ->
            if (isAdmin) {
                // ผู้ใช้งานเป็น groupAdmin
                // ทำสิ่งที่คุณต้องการทำเมื่อผู้ใช้งานเป็น groupAdmin ที่นี่
                binding.Group2.setOnClickListener {
                    binding.textGroup.text = "Test2"}
            } else {
                // ผู้ใช้งานไม่ได้เป็น groupAdmin
                // ทำสิ่งที่คุณต้องการทำเมื่อผู้ใช้งานไม่ได้เป็น groupAdmin ที่นี่
                binding.Group2.visibility = View.GONE
            }
        }


    }

    fun setCurrentGroupName(groupName: String) {
        currentGroupName = groupName

        // If the group fragment is already visible, update the group name immediately
        if (isVisible) {
            binding.textGroup.text = groupName
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
        transaction.replace(binding.menuContainer.id, fragment)
        transaction.commit()
    }

    private fun showGroupIcon(groupName: String) {
        when (groupName) {
            "Group 1" -> binding.Group1.visibility = View.VISIBLE
            "Group 2" -> binding.Group2.visibility = View.VISIBLE
            // Add more cases for other groups if needed
        }
    }

    fun checkIfUserIsMemberOrAdmin(groupName: String, callback: (Boolean) -> Unit) {
        // เข้าถึง Firebase Auth เพื่อรับข้อมูลผู้ใช้งานปัจจุบัน
        val currentUser = FirebaseAuth.getInstance().currentUser

        // เข้าถึง Firebase Database เพื่อตรวจสอบสถานะของผู้ใช้งานในกลุ่ม
        val database = FirebaseDatabase.getInstance()
        val groupsRef = database.getReference("Groups")

        // ดึงข้อมูลของกลุ่มที่ระบุจาก Realtime Database
        val query = groupsRef.orderByChild("groupName").equalTo(groupName)
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // ตรวจสอบว่ามีข้อมูลของกลุ่มที่ระบุหรือไม่
                if (dataSnapshot.exists()) {
                    for (snapshot in dataSnapshot.children) {
                        val group = snapshot.getValue(Group::class.java)
                        // ตรวจสอบว่า currentUser เป็น groupAdmin หรือไม่
                        if (currentUser != null && group != null && group.groupAdmin == currentUser.uid) {
                            // currentUser เป็น groupAdmin
                            callback(true)
                            return
                        }
                    }
                }
                // currentUser ไม่ได้เป็น groupAdmin หรือไม่มีข้อมูลของกลุ่มที่ระบุ
                callback(false)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // การอ่านข้อมูลผิดพลาด
                callback(false)
            }
        })
    }

    // ประกาศคลาส Model สำหรับกลุ่ม
    data class Group(
        val groupAdmin: String? = "",
        val groupName: String? = ""
    )
}

