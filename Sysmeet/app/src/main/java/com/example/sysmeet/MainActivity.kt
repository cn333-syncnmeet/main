package com.example.sysmeet

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

//background
private lateinit var view: View
//nav bar
private lateinit var bottom_navigation: BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val homeFragment = HomeFragment()
    private val scheduleFragment = ScheduleFragment()
    private val groupFragment = GroupFragment()
    private val settingFragment = SettingFragment()

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    setCurrentFragment(homeFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.schedule -> {
                    setCurrentFragment(scheduleFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.group -> {
                    setCurrentFragment(groupFragment)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.setting -> {
                    setCurrentFragment(settingFragment)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        setCurrentFragment(homeFragment)

        //get element
        getElement()

        //set color
        setColor()
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            commit()
        }
    }

    fun getElement() {
        // = findViewById(R.id.)
        view = findViewById(R.id.main)
        bottom_navigation = findViewById(R.id.bottom_navigation)
    }

    fun setColor() {
        //do something similar to this for every page?

        //get prefs
        var preferences: SharedPreferences = getSharedPreferences("settingPrefs", MODE_PRIVATE)

        //get color
        var color1 = preferences.getInt("color1", Color.rgb(64, 124, 135)) //dark blue
        var color2 = preferences.getInt("color2", Color.rgb(165, 219, 221)) // blue
        var color3 = preferences.getInt("color3", Color.rgb(238, 241, 246)) // white
        var color4 = preferences.getInt("color4", Color.rgb(211, 225, 226)) // light blue

        //set color
        //view = findViewById(R.id.main)
        view.setBackgroundColor(color4) //use this function for all element? (button, divider, etc.)

        bottom_navigation.setBackgroundColor(color2)

    }
}