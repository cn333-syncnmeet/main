package com.example.sysmeet

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
//for changing color
import android.text.TextWatcher
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import java.lang.Integer
import androidx.datastore.preferences.core.Preferences
//import kotlinx.coroutines.flow.internal.NoOpContinuation.context

//private val Context.dataStore by dataStore(
//    fileName = "user_preferences",
//    serializer = MyCustomSerializer,
//)

class SettingActivity : AppCompatActivity() {
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
    private lateinit var buttonColor1: Button
    private lateinit var buttonColor2: Button
    private lateinit var buttonColor3: Button
    private lateinit var buttonColor4: Button
    private lateinit var buttonNewColor: Button
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
    //get prefs
    //private var preferences:SharedPreferences = getSharedPreferences("settingPrefs", MODE_PRIVATE)
    //private var editor:SharedPreferences.Editor = preferences.edit()

    // At the top level of your kotlin file:
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_setting)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //get prefs
        var preferences:SharedPreferences = getSharedPreferences("settingPrefs", MODE_PRIVATE)
        var editor:SharedPreferences.Editor = preferences.edit()

        //get element
        getElement()

        //set color
        setColor()

        //set language
        setLanguage()

        testButton = findViewById(R.id.testButton)
        testButton.setOnClickListener {
            editor.putInt("color1", Color.RED)
            editor.commit()
        }

        buttonLogout.setOnClickListener {
            val settingToLogoutIntent = Intent(this, LogoutActivity::class.java)
            startActivity(settingToLogoutIntent)
        }

        //get the RGB edittext
        // edittextRed = findViewById(R.id.edittextRed)
        // edittextGreen = findViewById(R.id.edittextGreen)
        // edittextBlue = findViewById(R.id.edittextBlue)
        // buttonNewColor = findViewById(R.id.buttonNewColor)
        // get the new color's RGB values: from edittext
        newRed = 0
        newGreen = 0
        newBlue = 0
//        newRed = Integer.parseInt(edittextRed.getText().toString())
//        newGreen = Integer.parseInt(edittextGreen.getText().toString())
//        newBlue = Integer.parseInt(edittextBlue.getText().toString())
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
//        val watcher = object: TextWatcher
//        edittextRed.addTextChangedListener(watcher) {
//            @override
//            public afterTextChanged(Editable editable) {
//                newRed = editable
//                //update setColorButton's color
//                updateNewColor()
//            }
//        }
//        //on any value change
//        edittextGreen.addTextChangedListener(new TextWatcher) {
//            @override
//            public afterTextChanged(Editable editable) {
//                newGreen = editable
//                //update setColorButton's color
//                updateNewColor()
//            }
//        }        //on any value change
//        edittextBlue.addTextChangedListener(new TextWatcher) {
//            @override
//            public afterTextChanged(Editable editable) {
//                newBlue = editable
//                //update setColorButton's color
//                updateNewColor()
//            }
//        }

        //select color to change
        var selectedColor = 0
        buttonColor1.setOnClickListener {
            selectedColor = 1
            //add border?
            // buttonColor1.background = ContextCompact.getDrawable(this, R.drawable.drawableName)
        }
        buttonColor2.setOnClickListener {
            selectedColor = 2
            //add border?
            // buttonColor1.background = ContextCompact.getDrawable(this, R.drawable.drawableName)
        }
        buttonColor3.setOnClickListener {
            selectedColor = 3
            //add border?
            // buttonColor1.background = ContextCompact.getDrawable(this, R.drawable.drawableName)
        }
        buttonColor4.setOnClickListener {
            selectedColor = 4
            //add border?
            // buttonColor1.background = ContextCompact.getDrawable(this, R.drawable.drawableName)
        }

        //set new color: to do
        // press the set color button
        buttonNewColor.setOnClickListener {
            // change the selected color: which one?
            when(selectedColor) {
                1 -> editor.putInt("color1", newColor)
                2 -> editor.putInt("color2", newColor)
                3 -> editor.putInt("color3", newColor)
                4 -> editor.putInt("color4", newColor)
            }
            editor.commit()
            setColor()
        }


    }

    fun getElement() {
        view = findViewById(R.id.main)
        divider1 = findViewById(R.id.divider1)
        divider2 = findViewById(R.id.divider2)
        divider3 = findViewById(R.id.divider3)
        divider4 = findViewById(R.id.divider4)
        divider5 = findViewById(R.id.divider5)
        textSetting = findViewById(R.id.textAbout)
        textTheme = findViewById(R.id.textTheme)
        textColor = findViewById(R.id.textColor)
        textLanguage = findViewById(R.id.textLanguage)
        textNotification = findViewById(R.id.textNotification)
        switchPost = findViewById(R.id.switchPost)
        switchSchedule = findViewById(R.id.switchSchedule)
        buttonAbout = findViewById(R.id.buttonAbout)
        buttonLogout = findViewById(R.id.buttonLogout)
        buttonColor1 = findViewById(R.id.buttonColor1)
        buttonColor2 = findViewById(R.id.buttonColor2)
        buttonColor3 = findViewById(R.id.buttonColor3)
        buttonColor4 = findViewById(R.id.buttonColor4)
        buttonNewColor = findViewById(R.id.buttonNewColor)
        edittextRed = findViewById((R.id.edittextRed))
        edittextGreen = findViewById((R.id.edittextGreen))
        edittextBlue = findViewById((R.id.edittextBlue))
    }

    fun setColor() {
        //do something similar to this for every page?

        //get prefs
        var preferences:SharedPreferences = getSharedPreferences("settingPrefs", MODE_PRIVATE)
        var editor:SharedPreferences.Editor = preferences.edit()

        //get color
        var color1 = preferences.getInt("color1", Color.rgb(64, 124, 135)) //dark blue
        var color2 = preferences.getInt("color2", Color.rgb(165, 219, 221)) // blue
        var color3 = preferences.getInt("color3", Color.rgb(238, 241, 246)) // white
        var color4 = preferences.getInt("color4", Color.rgb(211, 225, 226)) // light blue

//        var color1: Flow<Int> = applicationContext.dataStore.data.map {
//            preferences -> preferences[intPreferencesKey("color1")] ?: Color.rgb(64, 124, 135)
//        }
//        var color2: Flow<Int> = applicationContext.dataStore.data.map {
//                preferences -> preferences[intPreferencesKey("color2")] ?: Color.rgb(64, 124, 135)
//        }
//        var color3: Flow<Int> = applicationContext.dataStore.data.map {
//                preferences -> preferences[intPreferencesKey("color3")] ?: Color.rgb(64, 124, 135)
//        }
//        var color4: Flow<Int> = applicationContext.dataStore.data.map {
//                preferences -> preferences[intPreferencesKey("color4")] ?: Color.rgb(64, 124, 135)
//        }

        //set color
        view = findViewById(R.id.main)
        view.setBackgroundColor(color4) //use this function for all element? (button, divider, etc.)

        // divider1 = findViewById(R.id.divider1)
        divider1.setBackgroundColor(color1)
        // divider2 = findViewById(R.id.divider2)
        divider2.setBackgroundColor(color1)
        // divider3 = findViewById(R.id.divider3)
        divider3.setBackgroundColor(color1)
        // divider4 = findViewById(R.id.divider4)
        divider4.setBackgroundColor(color1)
        // divider5 = findViewById(R.id.divider5)
        divider5.setBackgroundColor(color1)

        // textSetting = findViewById(R.id.textSetting)
        textSetting.setTextColor(color1)
        // textTheme = findViewById(R.id.textTheme)
        textTheme.setTextColor(color1)
        // textColor = findViewById(R.id.textColor)
        textColor.setTextColor(color1)
        // textLanguage = findViewById(R.id.textLanguage)
        textLanguage.setTextColor(color1)
        // textNotification = findViewById(R.id.textNotification)
        textNotification.setTextColor(color1)

        // switchPost = findViewById(R.id.switchPost)
        switchPost.setTextColor(color1)
        // switchSchedule = findViewById(R.id.switchSchedule)
        switchSchedule.setTextColor(color1)

        // buttonAbout = findViewById(R.id.buttonAbout)
        buttonAbout.setBackgroundColor(color2)
        buttonAbout.setTextColor(color1)
        // buttonLogout = findViewById(R.id.buttonLogout)
        buttonLogout.setBackgroundColor(Color.RED)
        // buttonColor1 = findViewById(R.id.buttonColor1)
        buttonColor1.setBackgroundColor(color1)
        // buttonColor2 = findViewById(R.id.buttonColor2)
        buttonColor2.setBackgroundColor(color2)
        // buttonColor3 = findViewById(R.id.buttonColor3)
        buttonColor3.setBackgroundColor(color3)
        buttonColor4.setBackgroundColor(color4)

    }

    fun setLanguage() {

        var preferences:SharedPreferences = getSharedPreferences("settingPrefs", MODE_PRIVATE)

        var language = preferences.getString("language", "english")

        if (language == "english") {
            textSetting.setText(getString(R.string.Setting_English))
            textTheme.setText(getString(R.string.Theme_English))
            textColor.setText(getString(R.string.Color_English))
            textLanguage.setText(getString(R.string.Language_English))
            textNotification.setText(getString(R.string.Notification_English))
            switchPost.setText(getString(R.string.NewPost_English))
            switchSchedule.setText(getString(R.string.ScheduleUpdate_English))
            buttonAbout.setText(getString(R.string.About_English))
            buttonLogout.setText(getString(R.string.Logout_English))
            buttonNewColor.setText(getString(R.string.SetColor_English))
        } else if (language == "thai") {
            textSetting.setText(getString(R.string.Setting_Thai))
            textTheme.setText(getString(R.string.Theme_Thai))
            textColor.setText(getString(R.string.Color_Thai))
            textLanguage.setText(getString(R.string.Language_Thai))
            textNotification.setText(getString(R.string.Notification_Thai))
            switchPost.setText(getString(R.string.NewPost_Thai))
            switchSchedule.setText(getString(R.string.ScheduleUpdate_Thai))
            buttonAbout.setText(getString(R.string.About_Thai))
            buttonLogout.setText(getString(R.string.Logout_Thai))
            buttonNewColor.setText(getString(R.string.SetColor_Thai))
        }
    }

    fun updateNewColor() {
        newRed = Integer.parseInt(edittextRed.getText().toString())
        newGreen = Integer.parseInt(edittextGreen.getText().toString())
        newBlue = Integer.parseInt(edittextBlue.getText().toString())
        newColor = Color.rgb(newRed, newGreen, newBlue)
        buttonNewColor.setBackgroundColor(newColor)
    }
}