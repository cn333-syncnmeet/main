package com.example.sysmeet


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
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
    private lateinit var firebaseAuth: FirebaseAuth


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
                        replaceFragment(HomeMenu(), binding.textGroup.text.toString())
                    }


                    return@setOnNavigationItemSelectedListener true
                }
                R.id.schedule_menu -> {

                    if (binding.textGroup.text != "Group") {
                        replaceFragment(ScheduleMenu(),binding.textGroup.text.toString())
                    }

                    return@setOnNavigationItemSelectedListener true
                }
                R.id.members_menu -> {

                    if (binding.textGroup.text != "Group") {
                        replaceFragment(MembersMenu(),binding.textGroup.text.toString())
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
        /*checkIfUserIsMemberOrAdmin("Test") { isAdmin ->
            if (isAdmin) {
                binding.Group1.visibility = View.VISIBLE
                // ผู้ใช้งานเป็น groupAdmin
                // ทำสิ่งที่คุณต้องการทำเมื่อผู้ใช้งานเป็น groupAdmin ที่นี่
                binding.Group1.setOnClickListener {
                    binding.textGroup.text = "Test"
                    replaceFragment(HomeMenu(), binding.textGroup.text.toString())
                }
            } else {
                // ผู้ใช้งานไม่ได้เป็น groupAdmin
                // ทำสิ่งที่คุณต้องการทำเมื่อผู้ใช้งานไม่ได้เป็น groupAdmin ที่นี่
                binding.Group1.visibility = View.GONE
            }
        }

        checkIfUserIsMemberOrAdmin("Test2") { isAdmin ->
            if (isAdmin) {
                binding.Group2.visibility = View.VISIBLE
                // ผู้ใช้งานเป็น groupAdmin
                // ทำสิ่งที่คุณต้องการทำเมื่อผู้ใช้งานเป็น groupAdmin ที่นี่
                binding.Group2.setOnClickListener {
                    binding.textGroup.text = "Test2"
                    replaceFragment(HomeMenu(), binding.textGroup.text.toString())
                }
            } else {
                // ผู้ใช้งานไม่ได้เป็น groupAdmin
                // ทำสิ่งที่คุณต้องการทำเมื่อผู้ใช้งานไม่ได้เป็น groupAdmin ที่นี่
                binding.Group2.visibility = View.GONE
            }
        }*/



        firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = FirebaseAuth.getInstance().currentUser

        // อ้างอิงไปยัง "Groups" ใน Firebase Database
        val databaseReference = FirebaseDatabase.getInstance().getReference("Groups")

        // เพิ่ม Listener สำหรับการอ่านข้อมูล
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val groupNames = mutableListOf<String>()
                var count = 1 // เริ่มต้น count ที่ 1

                // วนลูปเพื่อดึงข้อมูลจากทุกๆ child ใน "Groups"
                for (groupSnapshot in dataSnapshot.children) {
                    // อ่านค่าของ message จาก child นั้น ๆ

                    if (groupSnapshot.hasChildren()) {
                        // อ่านชื่อของ child นั้น ๆ แล้วเก็บไว้ในรายการ
                        groupNames.add(groupSnapshot.key!!)
                        val groupName = groupSnapshot.key!!
                    }

                    val groupName = groupSnapshot.key!!

                    checkIfUserIsMemberOrAdmin(groupName.toString()) { isAdmin ->
                        if (isAdmin) {
                            val groupCount = "Group_$count"

                            // หา ID ของอ็อบเจกต์โดยใช้ชื่อ

                            val resourceId = requireActivity().findViewById<View>(
                                resources.getIdentifier(
                                    groupCount,
                                    "id",
                                    requireContext().packageName
                                )
                            )
                            resourceId.visibility = View.VISIBLE


                            resourceId.setOnClickListener {
                                binding.textGroup.text = groupName.toString()
                                replaceFragment(HomeMenu(), binding.textGroup.text.toString())
                            }

                            count++

                        }
                    }

                }

            }

            override fun onCancelled(databaseError: DatabaseError) {
                // การดึงข้อมูลล้มเหลว
                Log.e("HomeMenu", "Failed to read value.", databaseError.toException())
            }
        })


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

    private fun replaceFragment(fragment: Fragment, textGroup: String) {
        val transaction: FragmentTransaction = childFragmentManager.beginTransaction()
        // ส่งข้อมูล textGroup ไปให้ Fragment ใหม่ที่จะถูกเปลี่ยนแทนที่ปัจจุบันด้วย bundle
        val bundle = Bundle().apply {
            putString("textGroup", textGroup)
        }
        fragment.arguments = bundle
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
        val groupsRef = database.getReference("Groups").child(groupName).child("Admin")

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

