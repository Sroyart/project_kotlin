package com.example.project_kotlin.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.project_kotlin.R
import com.example.project_kotlin.dialogFragment.ForgotPasswordDialogFragment
import com.example.project_kotlin.model.ArticlesViewModel
import kotlinx.android.synthetic.main.fragment_person.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class PersonFragment : Fragment() {
    val model by lazy { ViewModelProvider(this).get(ArticlesViewModel::class.java) }
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


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
        var btnConn: Button = view.findViewById(R.id.btn_conn)
        btnConn.setOnClickListener {
            if (et_user_name.text.isNullOrBlank()) {
                Toast.makeText(context, "Remplissez le champ", Toast.LENGTH_SHORT).show()
            } else {
                model.postConn(
                    "http://10.0.2.2:8082/sign-in",
                    "{\n" +
                            "    \"currentPwd\": \"jean\",\n" +
                            "    \"currentEmail\": \"arthur.iouzalen@supdevinci-edu.fr\"\n" +
                            "}"
                )
                model.dataConn.observe(viewLifecycleOwner) {
                    if (it != null) {
                        lifecycleScope.launch {
                            val result = readStore(it.jwt)
                            if (result.isNullOrEmpty()) {
//                                println(it.jwt)
//                                println(result)
                                saveStore(it.jwt, it.jwt)
                            }
                            println("store : ${readStore(it.jwt)}")
                            if (result != null) {
                                Toast.makeText(context, result.toString(), Toast.LENGTH_SHORT)
                                    .show()
                            }
                        }
                    }
                }

            }
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

    suspend fun saveStore(dataKey: String, value: String) {
        val dataStoreKey = stringPreferencesKey(dataKey)
        context?.dataStore?.edit { settings ->
            settings[dataStoreKey] = value
        }
    }

    suspend fun readStore(dataKey: String): String? {
        val dataStoreKey = stringPreferencesKey(dataKey)
        val preferences = context?.dataStore?.data?.first()
        return preferences?.get(dataStoreKey)
    }
}