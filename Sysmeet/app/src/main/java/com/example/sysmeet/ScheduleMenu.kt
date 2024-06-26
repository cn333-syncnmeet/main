package com.example.sysmeet

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.sysmeet.databinding.FragmentScheduleBinding
import com.example.sysmeet.databinding.FragmentScheduleMenuBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ScheduleMenu.newInstance] factory method to
 * create an instance of this fragment.
 */
class ScheduleMenu : Fragment() {

    private var _binding: FragmentScheduleMenuBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = FirebaseAuth.getInstance().currentUser

        /*val textGroup = arguments?.getString("textGroup")

        // กำหนดค่าให้กับ groupName เท่ากับ textGroup
        binding.groupSchedule.text = textGroup
        val groupName = binding.groupSchedule.text.toString()*/

        val databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(currentUser!!.uid).child("Schedule")
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                // วนลูปเพื่อดึงข้อมูลจากทุกๆ child ใน "Groups"
                for (groupSnapshot in dataSnapshot.children) {
                    val date = groupSnapshot.child("date").getValue(String::class.java)
                    val end = groupSnapshot.child("endTime").getValue(String::class.java)
                    val start = groupSnapshot.child("startTime").getValue(String::class.java)
                    val startHour = start?.split(":")?.get(0)?.toIntOrNull() ?: 0
                    val endHour = end?.split(":")?.get(0)?.toIntOrNull() ?: 0

                    checkAndModifyViews(date.toString(),startHour.toString(),endHour.toString())
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // การดึงข้อมูลล้มเหลว
                Log.e("ScheduleFragment", "Failed to read value.", databaseError.toException())
            }
        })

        view.findViewById<Button>(R.id.editButton).setOnClickListener {
            // Start EditScheduleActivity
            val intent = Intent(activity, EditScheduleActivity::class.java)
            startActivity(intent)
        }

        // Update the schedule data here, display any saved data in the schedule table
        // Example: updateScheduleTable(savedData)
    }

    fun checkAndModifyViews(date: String, startTime: String, endTime: String) {
        val start = startTime.toIntOrNull() ?: 0
        val end = endTime.toIntOrNull() ?: 0

        if (start <= end) {
            // เลขเริ่มต้นไม่มีค่า 0 และเลขเริ่มต้นน้อยกว่าหรือเท่ากับเลขสิ้นสุด
            // สามารถใช้ลูปเพื่อเปลี่ยนแปลงการวิวตามเลขเริ่มต้นและสิ้นสุดที่ให้มาได้
            for (i in start until end + 1) {
                // หาวันอาทิตย์ ถ้าใช่จะแสดง line_1a เท่านั้น ถ้าไม่ใช่จะแสดงเป็น GONE
                if (date == "Sun") {
                    val viewID = "line_"+ (i + 1).toString() +"a"
                    val textView =  requireActivity().findViewById<TextView>(resources.getIdentifier(viewID, "id", requireContext().packageName))
                    textView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red))

                } else if (date == "Mon") {
                    val viewID = "line_${i + 1}b"
                    val textView = requireActivity().findViewById<TextView>(resources.getIdentifier(viewID, "id", requireContext().packageName))
                    textView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red))

                } else if (date == "Tue") {
                    val viewID = "line_${i + 1}c"
                    val textView = requireActivity().findViewById<TextView>(resources.getIdentifier(viewID, "id", requireContext().packageName))
                    textView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red))

                } else if (date == "Wed") {
                    val viewID = "line_${i + 1}d"
                    val textView = requireActivity().findViewById<TextView>(resources.getIdentifier(viewID, "id", requireContext().packageName))
                    textView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red))

                } else if (date == "Thu") {
                    val viewID = "line_${i + 1}e"
                    val textView = requireActivity().findViewById<TextView>(resources.getIdentifier(viewID, "id", requireContext().packageName))
                    textView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red))

                } else if (date == "Fri") {
                    val viewID = "line_${i + 1}f"
                    val textView = requireActivity().findViewById<TextView>(resources.getIdentifier(viewID, "id", requireContext().packageName))
                    textView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red))

                } else if (date == "Sat") {
                    val viewID = "line_${i + 1}g"
                    val textView = requireActivity().findViewById<TextView>(resources.getIdentifier(viewID, "id", requireContext().packageName))
                    textView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red))
                }
            }
        } else {

            for (i in end until start + 1) {
                // หาวันอาทิตย์ ถ้าใช่จะแสดง line_1a เท่านั้น ถ้าไม่ใช่จะแสดงเป็น GONE
                if (date == "Sun") {
                    val viewID = "line_${i + 1}a"
                    val textView = binding.root.findViewById<TextView>(resources.getIdentifier(viewID, "id", binding.root.context.packageName))
                    textView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red))

                } else if (date == "Mon") {
                    val viewID = "line_${i + 1}b"
                    val textView = binding.root.findViewById<TextView>(resources.getIdentifier(viewID, "id", binding.root.context.packageName))
                    textView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red))

                } else if (date == "Tue") {
                    val viewID = "line_${i + 1}c"
                    val textView = binding.root.findViewById<TextView>(resources.getIdentifier(viewID, "id", binding.root.context.packageName))
                    textView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red))

                } else if (date == "Wed") {
                    val viewID = "line_${i + 1}d"
                    val textView = binding.root.findViewById<TextView>(resources.getIdentifier(viewID, "id", binding.root.context.packageName))
                    textView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red))

                } else if (date == "Thu") {
                    val viewID = "line_${i + 1}e"
                    val textView = binding.root.findViewById<TextView>(resources.getIdentifier(viewID, "id", binding.root.context.packageName))
                    textView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red))

                } else if (date == "Fri") {
                    val viewID = "line_${i + 1}f"
                    val textView = binding.root.findViewById<TextView>(resources.getIdentifier(viewID, "id", binding.root.context.packageName))
                    textView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red))

                } else if (date == "Sat") {
                    val viewID = "line_${i + 1}g"
                    val textView = binding.root.findViewById<TextView>(resources.getIdentifier(viewID, "id", binding.root.context.packageName))
                    textView.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.red))
                }
            }
        }
    }
}