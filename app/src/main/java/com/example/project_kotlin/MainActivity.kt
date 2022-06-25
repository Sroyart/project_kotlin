package com.example.project_kotlin

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.project_kotlin.fragments.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val connectionFragment = ConnectionFragment()
    private val homeFragment = HomeFragment()
    private val searchFragment = SearchFragment()
    private val userInfo = UserInfo()
    private val favoriteFragment = FavoriteFragment()
    private val basketFragment = BasketFragment()
    lateinit var toggle: ActionBarDrawerToggle
    lateinit var preferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        preferences = getSharedPreferences("JWT", Context.MODE_PRIVATE)


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(homeFragment)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)




        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_home -> replaceFragment(homeFragment)
                R.id.ic_search -> replaceFragment(searchFragment)
                R.id.ic_favorite -> isConnected(favoriteFragment)
                R.id.ic_shopping -> replaceFragment(basketFragment)
                R.id.ic_person -> isConnected(userInfo)
            }
            true
        }
    }

    private fun isConnected(myFragment: Fragment) {
        val connectedOrNot = preferences.getString("JWT", "")


        if (connectedOrNot.isNullOrEmpty()) {
            replaceFragment(connectionFragment)
        } else {
            replaceFragment(myFragment)
        }


    }

    private fun replaceFragment(fragment: Fragment) {
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }


}