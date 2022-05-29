package com.example.project_kotlin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.project_kotlin.R
import com.example.project_kotlin.dialogFragment.ForgotPasswordDialogFragment
import kotlinx.android.synthetic.main.fragment_person.*

class PersonFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_person, container, false)
        val createBtn: Button = view.findViewById(R.id.btn_create_account)
        createBtn.setOnClickListener {
            val fragment = CreateAccountFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container, fragment)?.commit()
        }


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_forgot.setOnClickListener {
            activity?.let { it1 ->
                ForgotPasswordDialogFragment().show(
                    it1.supportFragmentManager,
                    "customDialog"
                )
            }
        }
    }
}