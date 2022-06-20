package com.example.project_kotlin.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.project_kotlin.LogoutActivity
import com.example.project_kotlin.R


class UserInfo : Fragment() {

    lateinit var preferences: SharedPreferences
    val transaction = fragmentManager?.beginTransaction()
    val homeFragment = HomeFragment()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_user_info, container, false)


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnLogout: Button = view.findViewById(R.id.btn_user_info_logout)


        preferences = activity?.getSharedPreferences("JWT", Context.MODE_PRIVATE)!!
        println("sdf")


        btnLogout.setOnClickListener {
            println("Deconnection")
            val intent = Intent(context, LogoutActivity::class.java)
            startActivity(intent)
//            val editor: SharedPreferences.Editor = preferences.edit()
//            editor.clear()
//            editor.apply()
//            replaceFragment(homeFragment)

        }


    }

    private fun replaceFragment(fragment: Fragment) {
        println(fragment)
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

}