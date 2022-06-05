package com.example.project_kotlin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.project_kotlin.R
import com.example.project_kotlin.model.ArticlesViewModel
import kotlinx.android.synthetic.main.fragment_create_account.*


class CreateAccountFragment : Fragment() {
    val model by lazy { ViewModelProvider(this).get(ArticlesViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_create_account, container, false)
        val connBtn: Button = view.findViewById(R.id.btn_conn_account)
        connBtn.setOnClickListener {
            val fragment = ConnectionFragment()
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.fragment_container, fragment)?.commit()
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_submit_create_account.setOnClickListener {
            if (et_create_firstName.text.isNullOrBlank() && et_create_lastName.text.isNullOrBlank() && et_create_email.text.isNullOrBlank() && et_create_password.text.isNullOrBlank()) {
                Toast.makeText(context, "Remplissez tous les champs", Toast.LENGTH_SHORT).show()
            } else {
                model.postRegister(
                    "http://10.0.2.2:8082/register",
                    "{\n" +
                            "    \"password\": \"${et_create_password.text}\",\n" +
                            "    \"email\":\"${et_create_email.text}\",\n" +
                            "    \"firstName\": \"${et_create_firstName.text}\",\n" +
                            "    \"lastName\":\"${et_create_lastName.text}\"\n" +
                            "}"
                )
            }
        }

    }
}