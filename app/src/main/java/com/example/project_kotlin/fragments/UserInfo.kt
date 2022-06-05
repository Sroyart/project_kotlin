package com.example.project_kotlin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.project_kotlin.R
import com.example.project_kotlin.model.UserViewModel
import kotlinx.android.synthetic.main.fragment_user_info.*


class UserInfo : Fragment() {
    //    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private lateinit var userViewModel: UserViewModel


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
        userViewModel = ViewModelProvider(this).get(
            UserViewModel::
            class.java
        )
        userViewModel.readFromDataStore.observe(
            viewLifecycleOwner,
            { email ->
                tv_user_info_email.text = email
            })
        var btnUserInfoDeco: Button = view.findViewById(R.id.btn_user_info_deco)
        btnUserInfoDeco.setOnClickListener {
            userViewModel.deleteDataStore()
        }
    }

}