package com.example.project_kotlin.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.project_kotlin.R


class FavoriteFragment : Fragment() {
    lateinit var preferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preferences = activity?.getSharedPreferences("JWT", Context.MODE_PRIVATE)!!

        val myJwt = preferences.getString("JWT", "")
        println(myJwt)
        

    }


}
