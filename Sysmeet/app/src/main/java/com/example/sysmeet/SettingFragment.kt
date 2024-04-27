package com.example.sysmeet

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class SettingFragment : Fragment() {
    //background
    private lateinit var view: View
    //divider
    private lateinit var divider1: View
    private lateinit var divider2: View
    private lateinit var divider3: View
    private lateinit var divider4: View
    private lateinit var divider5: View
    //button
    private lateinit var testButton: Button
    private lateinit var buttonAbout: Button
    private lateinit var buttonLogout: Button
    //text
    private lateinit var textSetting: TextView
    private lateinit var textTheme: TextView
    private lateinit var textColor: TextView
    private lateinit var textLanguage: TextView
    private lateinit var textNotification: TextView
    //switch
    private lateinit var switchPost: Switch
    private lateinit var switchSchedule: Switch

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_setting, container, false)

        // Enable edge-to-edge display
        enableEdgeToEdge(root)

        // Set padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Set color
        setColor(root)

        // Set onClickListener for testButton
        testButton = root.findViewById(R.id.testButton)
        testButton.setOnClickListener {
            val preferences: SharedPreferences = requireContext().getSharedPreferences("settingPrefs", MODE_PRIVATE)
            val editor: SharedPreferences.Editor = preferences.edit()
            editor.putInt("color1", Color.RED)
            editor.apply()
        }

        // Set onClickListener for buttonLogout
        buttonLogout.setOnClickListener {
            val settingToLogoutIntent = Intent(requireActivity(), LogoutActivity::class.java)
            startActivity(settingToLogoutIntent)
            requireActivity().finish() // ปิด Activity ปัจจุบันหลังจากเปิด Activity ใหม่
        }

        return root
    }

    private fun enableEdgeToEdge(view: View) {
        // Implement edge-to-edge display here
    }

    private fun setColor(root: View) {
        // Get prefs
        val preferences: SharedPreferences = requireContext().getSharedPreferences("settingPrefs", MODE_PRIVATE)
        val editor: SharedPreferences.Editor = preferences.edit()

        // Get colors
        val color1 = preferences.getInt("color1", Color.rgb(64, 124, 135)) // dark blue
        val color2 = preferences.getInt("color2", Color.rgb(165, 219, 221)) // blue
        val color3 = preferences.getInt("color3", Color.rgb(238, 241, 246)) // white
        val color4 = preferences.getInt("color4", Color.rgb(211, 225, 226)) // light blue

        // Set color
        view = root.findViewById(R.id.main)
        view.setBackgroundColor(color4)

        divider1 = root.findViewById(R.id.divider1)
        divider1.setBackgroundColor(color1)
        divider2 = root.findViewById(R.id.divider2)
        divider2.setBackgroundColor(color1)
        divider3 = root.findViewById(R.id.divider3)
        divider3.setBackgroundColor(color1)
        divider4 = root.findViewById(R.id.divider4)
        divider4.setBackgroundColor(color1)
        divider5 = root.findViewById(R.id.divider5)
        divider5.setBackgroundColor(color1)

        textSetting = root.findViewById(R.id.textSetting)
        textSetting.setTextColor(color1)
        textTheme = root.findViewById(R.id.textTheme)
        textTheme.setTextColor(color1)
        textColor = root.findViewById(R.id.textColor)
        textColor.setTextColor(color1)
        textLanguage = root.findViewById(R.id.textLanguage)
        textLanguage.setTextColor(color1)
        textNotification = root.findViewById(R.id.textNotification)
        textNotification.setTextColor(color1)

        switchPost = root.findViewById(R.id.switchPost)
        switchPost.setTextColor(color1)
        switchSchedule = root.findViewById(R.id.switchSchedule)
        switchSchedule.setTextColor(color1)

        buttonAbout = root.findViewById(R.id.buttonAbout)
        buttonAbout.setBackgroundColor(color2)
        buttonAbout.setTextColor(color1)
        buttonLogout = root.findViewById(R.id.buttonLogout)
        buttonLogout.setBackgroundColor(Color.RED)
    }
}
