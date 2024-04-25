package com.example.sysmeet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.sysmeet.databinding.FragmentGroupBinding

class GroupFragment : Fragment() {

    private var _binding: FragmentGroupBinding? = null
    private val binding get() = _binding!!

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

        // Set initial fragment
        replaceFragment(HomeMenu())

        // Handle bottom navigation item clicks
        binding.groupBottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home_menu -> {
                    replaceFragment(HomeMenu())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.schedule_menu -> {
                    replaceFragment(ScheduleMenu())
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.members_menu -> {
                    replaceFragment(MembersMenu())
                    return@setOnNavigationItemSelectedListener true
                }
                else -> false
            }
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
}
