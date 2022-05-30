package com.example.project_kotlin

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.project_kotlin.fragments.HomeFragment
import com.example.project_kotlin.fragments.PersonFragment
import com.example.project_kotlin.fragments.SearchFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val personFragment = PersonFragment()
    private val homeFragment = HomeFragment()
    private val searchFragment = SearchFragment()
    lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(homeFragment)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val sideBar: NavigationView = findViewById(R.id.nav_view)

        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        sideBar.setNavigationItemSelectedListener {
            drawerLayout.closeDrawer(GravityCompat.START);
            when (it.itemId) {
                R.id.nav_commandes -> replaceFragment(personFragment)
            }
            true
        }



        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_home -> replaceFragment(homeFragment)
                R.id.ic_search -> replaceFragment(searchFragment)
                R.id.ic_person -> replaceFragment(personFragment)
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        println(fragment)
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

}