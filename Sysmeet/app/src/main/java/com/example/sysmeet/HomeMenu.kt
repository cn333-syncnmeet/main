package com.example.sysmeet

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.sysmeet.databinding.FragmentHomeMenuBinding
import android.widget.TextView
import com.google.firebase.database.*

class HomeMenu : Fragment() {

    private var _binding: FragmentHomeMenuBinding? = null
    private val binding get() = _binding!!

    private lateinit var databaseReference: DatabaseReference
    private lateinit var groupName: TextView


    private lateinit var database: DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeMenuBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.sendGroupButton.setOnClickListener {
            val groupChat = binding.groupChatEditText.text.toString()
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
}

