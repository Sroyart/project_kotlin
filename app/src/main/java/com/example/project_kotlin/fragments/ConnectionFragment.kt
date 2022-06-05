package com.example.project_kotlin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.auth0.android.jwt.JWT
import com.example.project_kotlin.R
import com.example.project_kotlin.dialogFragment.ForgotPasswordDialogFragment
import com.example.project_kotlin.model.ArticlesViewModel
import com.example.project_kotlin.model.UserViewModel
import kotlinx.android.synthetic.main.fragment_person.*


class ConnectionFragment : Fragment() {
    val model by lazy { ViewModelProvider(this).get(ArticlesViewModel::class.java) }

    //    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    val homeFragment = HomeFragment()
    val transaction = fragmentManager?.beginTransaction()

    private lateinit var userViewModel: UserViewModel


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
//        var btnConn: Button = view.findViewById(R.id.btn_conn)
//        btnConn.setOnClickListener {
////            if (et_person_email.text.isNullOrBlank()) {
////                Toast.makeText(context, "Remplissez le champ", Toast.LENGTH_SHORT).show()
////            } else {
////
////            }
//        }


        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        userViewModel.readFromDataStore.observe(
            viewLifecycleOwner,
            { email -> tv_person_first_name.text = email })


        var btnConn: Button = view.findViewById(R.id.btn_conn)
        var tvPersonUserName: TextView = view.findViewById(R.id.et_person_email)
        var tvPersonPassword: TextView = view.findViewById(R.id.et_person_password)

        btnConn.setOnClickListener {
            if (et_person_email.text.isNullOrBlank()) {
                Toast.makeText(context, "Remplissez le champ", Toast.LENGTH_SHORT).show()
            } else {
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

            if (it != null) {
                println("model eclenché")
                val jwt = JWT(it.jwt)
                userViewModel.saveToDataStore("testDataStore")

                transaction?.replace(R.id.fragment_container, homeFragment)?.commit()

//                var claim: String? = jwt.getClaim("firstName").asString()
//                var lastName: String? = jwt.getClaim("lastName").asString()
//                println(claim)
//                tv_person_first_name.text = claim
//                tv_person_last_name.text = lastName
            }
        }
    }


}