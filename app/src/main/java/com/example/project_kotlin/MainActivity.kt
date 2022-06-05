package com.example.project_kotlin

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.project_kotlin.fragments.ConnectionFragment
import com.example.project_kotlin.fragments.HomeFragment
import com.example.project_kotlin.fragments.SearchFragment
import com.example.project_kotlin.fragments.UserInfo
import com.example.project_kotlin.model.UserViewModel
import com.example.project_kotlin.repository.DataStoreRepository
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val connectionFragment = ConnectionFragment()
    private val homeFragment = HomeFragment()
    private val searchFragment = SearchFragment()
    private val userInfo = UserInfo()
    lateinit var toggle: ActionBarDrawerToggle
    private lateinit var userViewModel: UserViewModel
    lateinit var dataStoreRepository: DataStoreRepository

//    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


    override fun onCreate(savedInstanceState: Bundle?) {
        dataStoreRepository = DataStoreRepository(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(homeFragment)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        var connectedOrNot = ""


//        val sideBar: NavigationView = findViewById(R.id.nav_view)

//        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
//        drawerLayout.addDrawerListener(toggle)
//        toggle.syncState()
//
//
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//        sideBar.setNavigationItemSelectedListener {
//            drawerLayout.closeDrawer(GravityCompat.START);
//            when (it.itemId) {
//                R.id.nav_commandes -> replaceFragment(connectionFragment)
//            }
//            true
//        }


        bottom_navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_home -> replaceFragment(homeFragment)
                R.id.ic_search -> replaceFragment(searchFragment)
                R.id.ic_person -> isConnected()
            }
            true
        }
    }

    private fun isConnected() {
        var connectedOrNot = ""
        dataStoreRepository.userEmailStore.asLiveData().observe(this, {
            connectedOrNot = it
        })
        println("connectedOrNot")
        println(connectedOrNot)
        println("connectedOrNot")
        println(connectedOrNot)
        println("connectedOrNot")
        println(connectedOrNot)
        if (connectedOrNot === "none") {
            replaceFragment(connectionFragment)
        } else {
            replaceFragment(userInfo)
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
    

}