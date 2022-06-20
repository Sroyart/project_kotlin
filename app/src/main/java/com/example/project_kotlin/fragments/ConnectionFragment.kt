package com.example.project_kotlin.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.project_kotlin.R
import com.example.project_kotlin.dialogFragment.ForgotPasswordDialogFragment
import com.example.project_kotlin.model.ArticlesViewModel
import kotlinx.android.synthetic.main.fragment_person.*


class ConnectionFragment : Fragment() {
    val model by lazy { ViewModelProvider(this).get(ArticlesViewModel::class.java) }
    val homeFragment = HomeFragment()

    lateinit var sharedPreferences: SharedPreferences


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


        var btnConn: Button = view.findViewById(R.id.btn_conn)
        var tvPersonUserName: TextView = view.findViewById(R.id.et_person_email)
        var tvPersonPassword: TextView = view.findViewById(R.id.et_person_password)

        btnConn.setOnClickListener {

            if (et_person_email.text.isNullOrBlank()) {
                Toast.makeText(context, "Remplissez le champ", Toast.LENGTH_SHORT).show()
            } else {
                println("action")
                model.postConn(
                    "http://10.0.2.2:8082/sign-in",
                    "{\n" +
                            "    \"currentPwd\": \"${tvPersonPassword.text}\",\n" +
                            "    \"currentEmail\": \"${tvPersonUserName.text}\"\n" +
                            "}"
                )


            }
        }

        btn_forgot.setOnClickListener {
            activity?.let { it1 ->
                ForgotPasswordDialogFragment().show(
                    it1.supportFragmentManager,
                    "customDialog"
                )
            }
        }

        model.dataConn.observe(viewLifecycleOwner) {
            sharedPreferences = activity?.getSharedPreferences("JWT", Context.MODE_PRIVATE)!!

            println("its in")
            if (it != null) {
                println(it.jwt)

                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString("JWT", it.jwt)
                editor.apply()

                Toast.makeText(context, "Information saved", Toast.LENGTH_LONG).show()
                replaceFragment(homeFragment)
            }


        }
    }

    private fun replaceFragment(fragment: Fragment) {
        println(fragment)
        if (fragment != null) {
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }


}