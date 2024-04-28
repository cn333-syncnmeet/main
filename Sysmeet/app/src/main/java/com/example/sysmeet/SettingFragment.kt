package com.example.sysmeet

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
//for changing color
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment


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
    private lateinit var buttonAbout: Button
    private lateinit var buttonLogout: Button
    private lateinit var buttonColor1: Button
    private lateinit var buttonColor2: Button
    private lateinit var buttonColor3: Button
    private lateinit var buttonColor4: Button
    private lateinit var buttonNewColor: Button
    private lateinit var buttonResetColor: Button
    //text
    private lateinit var textSetting: TextView
    private lateinit var textTheme: TextView
    private lateinit var textColor: TextView
    private lateinit var textLanguage: TextView
    private lateinit var textNotification: TextView
    //edittext
    private lateinit var edittextRed: EditText
    private lateinit var edittextGreen : EditText
    private lateinit var edittextBlue : EditText
    //switch
    private lateinit var switchPost: Switch
    private lateinit var switchSchedule: Switch
    //radioGroup
    private lateinit var radioGroupLanguage: RadioGroup
    //radioButton
    private lateinit var radioButtonEnglish: RadioButton
    private lateinit var radioButtonThai: RadioButton
    //color stuff
    private var newRed = 0
    private var newGreen = 0
    private var newBlue = 0
    private var newColor = Color.rgb(newRed, newGreen, newBlue)
    private var redText = "0"
    private var greenText = "0"
    private var blueText = "0"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_setting, container, false)

        // Enable edge-to-edge display
        //enableEdgeToEdge()

        // Set padding for system bars
        ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //get element
        getElement(root)

        //set color
        setColor()

        //set language
        setLanguage()


        // Set onClickListener for buttonLogout
        buttonLogout.setOnClickListener {
            val settingToLogoutIntent = Intent(requireActivity(), LogoutActivity::class.java)
            startActivity(settingToLogoutIntent)
            //requireActivity().finish() // ปิด Activity ปัจจุบันหลังจากเปิด Activity ใหม่
        }

        // Set onClickListener for buttonLogout
        buttonAbout.setOnClickListener {
            val settingToAboutIntent = Intent(requireActivity(), AboutActivity::class.java)
            startActivity(settingToAboutIntent)
            //requireActivity().finish() // ปิด Activity ปัจจุบันหลังจากเปิด Activity ใหม่
        }

        //get the RGB edittext
        newRed = 0
        newGreen = 0
        newBlue = 0
        newColor = Color.rgb(newRed, newGreen, newBlue)
        buttonNewColor.setBackgroundColor(newColor)

        //create textWatcher for when text changed
        val watcherRed = object : TextWatcher {
            override fun afterTextChanged(editable : Editable) {
                //check if empty first
                val newString = editable.toString()
                if (newString != "") {
                    newRed = Integer.parseInt(newString)
                    //update setColorButton's color
                    updateNewColor()
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        }
        val watcherGreen = object : TextWatcher {
            override fun afterTextChanged(editable : Editable) {
                //check if empty first
                val newString = editable.toString()
                if (newString != "") {
                    newGreen = Integer.parseInt(newString)
                    //update setColorButton's color
                    updateNewColor()
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        }
        val watcherBlue = object : TextWatcher {
            override fun afterTextChanged(editable : Editable) {
                //check if empty first
                val newString = editable.toString()
                if (newString != "") {
                    newBlue = Integer.parseInt(newString)
                    //update setColorButton's color
                    updateNewColor()
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        }

        //assign textWatcher to editText
        edittextRed.addTextChangedListener(watcherRed)
        edittextGreen.addTextChangedListener(watcherGreen)
        edittextBlue.addTextChangedListener(watcherBlue)

        //toast for selecting color
//        val duration = Toast.LENGTH_SHORT
        val duration = 0
        val toastColor1 = Toast.makeText(context, "Color1 selected", duration)
        val toastColor2 = Toast.makeText(context, "Color2 selected", duration)
        val toastColor3 = Toast.makeText(context, "Color3 selected", duration)
        val toastColor4 = Toast.makeText(context, "Color4 selected", duration)
        //select color to change
        var selectedColor = 0
        buttonColor1.setOnClickListener {
            selectedColor = 1
            toastColor1.show()
        }
        buttonColor2.setOnClickListener {
            selectedColor = 2
            toastColor2.show()
        }
        buttonColor3.setOnClickListener {
            selectedColor = 3
            toastColor3.show()
        }
        buttonColor4.setOnClickListener {
            selectedColor = 4
            toastColor4.show()
        }

        //set new color: to do
        // press the set color button
        buttonNewColor.setOnClickListener {
            val preferences: SharedPreferences = requireContext().getSharedPreferences("settingPrefs", MODE_PRIVATE)
            val editor: SharedPreferences.Editor = preferences.edit()
            // change the selected color: which one?
            when(selectedColor) {
                1 -> editor.putInt("color1", newColor)
                2 -> editor.putInt("color2", newColor)
                3 -> editor.putInt("color3", newColor)
                4 -> editor.putInt("color4", newColor)
            }
            editor.apply()
            setColor()
        }

        //reset color to default value
        buttonResetColor.setOnClickListener {
            val preferences: SharedPreferences = requireContext().getSharedPreferences("settingPrefs", MODE_PRIVATE)
            val editor: SharedPreferences.Editor = preferences.edit()
            // change the selected color: which one?
            editor.putInt("color1", Color.rgb(64, 124, 135))
            editor.putInt("color2", Color.rgb(165, 219, 221))
            editor.putInt("color3", Color.rgb(238, 241, 246))
            editor.putInt("color4", Color.rgb(211, 225, 226))

            editor.apply()
            setColor()
        }


        return root
    }

    fun getElement(root: View) {
        view = root.findViewById(R.id.main)
        divider1 = root.findViewById(R.id.divider1)
        divider2 = root.findViewById(R.id.divider2)
        divider3 = root.findViewById(R.id.divider3)
        divider4 = root.findViewById(R.id.divider4)
        divider5 = root.findViewById(R.id.divider5)
        textSetting = root.findViewById(R.id.textSetting)
        textTheme = root.findViewById(R.id.textTheme)
        textColor = root.findViewById(R.id.textColor)
        textLanguage = root.findViewById(R.id.textLanguage)
        textNotification = root.findViewById(R.id.textNotification)
        switchPost = root.findViewById(R.id.switchPost)
        switchSchedule = root.findViewById(R.id.switchSchedule)
        buttonAbout = root.findViewById(R.id.buttonAbout)
        buttonLogout = root.findViewById(R.id.buttonLogout)
        buttonColor1 = root.findViewById(R.id.buttonColor1)
        buttonColor2 = root.findViewById(R.id.buttonColor2)
        buttonColor3 = root.findViewById(R.id.buttonColor3)
        buttonColor4 = root.findViewById(R.id.buttonColor4)
        buttonNewColor = root.findViewById(R.id.buttonNewColor)
        buttonResetColor = root.findViewById(R.id.buttonResetColor)
        edittextRed = root.findViewById(R.id.edittextRed)
        edittextGreen = root.findViewById(R.id.edittextGreen)
        edittextBlue = root.findViewById(R.id.edittextBlue)
    }

    private fun setColor() {
        //do something similar to this for every page?

        //get prefs
        val preferences:SharedPreferences = requireContext().getSharedPreferences("settingPrefs", MODE_PRIVATE)

        //get color
        val color1 = preferences.getInt("color1", Color.rgb(64, 124, 135)) //dark blue
        val color2 = preferences.getInt("color2", Color.rgb(165, 219, 221)) // blue
        val color3 = preferences.getInt("color3", Color.rgb(238, 241, 246)) // white
        val color4 = preferences.getInt("color4", Color.rgb(211, 225, 226)) // light blue

        //set color
        //view = findViewById(R.id.main)
        view.setBackgroundColor(color4) //use this function for all element? (button, divider, etc.)

        divider1.setBackgroundColor(color1)
        divider2.setBackgroundColor(color1)
        divider3.setBackgroundColor(color1)
        divider4.setBackgroundColor(color1)
        divider5.setBackgroundColor(color1)

        textSetting.setTextColor(color1)
        textTheme.setTextColor(color1)
        textColor.setTextColor(color1)
        textLanguage.setTextColor(color1)
        textNotification.setTextColor(color1)

        switchPost.setTextColor(color1)
        switchSchedule.setTextColor(color1)

        buttonAbout.setBackgroundColor(color2)
        buttonAbout.setTextColor(color1)
        buttonLogout.setBackgroundColor(Color.RED)
        buttonResetColor.setBackgroundColor(Color.RED)
        buttonColor1.setBackgroundColor(color1)
        buttonColor2.setBackgroundColor(color2)
        buttonColor3.setBackgroundColor(color3)
        buttonColor4.setBackgroundColor(color4)

    }

    private fun setLanguage() {

        val preferences:SharedPreferences = requireContext().getSharedPreferences("settingPrefs", MODE_PRIVATE)

        val language = preferences.getString("language", "english")

        if (language == "english") {
            textSetting.text = resources.getString(R.string.Setting_English)
            textTheme.text = getString(R.string.Theme_English)
            textColor.text = getString(R.string.Color_English)
            textLanguage.text = getString(R.string.Language_English)
            textNotification.text = getString(R.string.Notification_English)
            switchPost.text = getString(R.string.NewPost_English)
            switchSchedule.text = getString(R.string.ScheduleUpdate_English)
            buttonAbout.text = getString(R.string.About_English)
            buttonLogout.text = getString(R.string.Logout_English)
            buttonNewColor.text = getString(R.string.SetColor_English)
        } else if (language == "thai") {
            textSetting.text = getString(R.string.Setting_Thai)
            textTheme.text = getString(R.string.Theme_Thai)
            textColor.text = getString(R.string.Color_Thai)
            textLanguage.text = getString(R.string.Language_Thai)
            textNotification.text = getString(R.string.Notification_Thai)
            switchPost.text = getString(R.string.NewPost_Thai)
            switchSchedule.text = getString(R.string.ScheduleUpdate_Thai)
            buttonAbout.text = getString(R.string.About_Thai)
            buttonLogout.text = getString(R.string.Logout_Thai)
            buttonNewColor.text = getString(R.string.SetColor_Thai)
        }
    }

    fun updateNewColor() {
        newRed = 0
        newGreen = 0
        newBlue = 0
        redText = edittextRed.getText().toString()
        if (redText != "") {
            newRed = Integer.parseInt(redText)
        }
        greenText = edittextGreen.getText().toString()
        if (greenText != "") {
            newGreen = Integer.parseInt(greenText)
        }
        blueText = edittextBlue.getText().toString()
        if (blueText != "") {
            newBlue = Integer.parseInt(blueText)
        }
        newColor = Color.rgb(newRed, newGreen, newBlue)
        buttonNewColor.setBackgroundColor(newColor)
    }
}