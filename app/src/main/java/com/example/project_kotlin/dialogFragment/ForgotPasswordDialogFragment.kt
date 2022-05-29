package com.example.project_kotlin.dialogFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.project_kotlin.R
import com.example.project_kotlin.model.ArticlesViewModel
import kotlinx.android.synthetic.main.forgot_password.*
import kotlinx.android.synthetic.main.forgot_password.view.*

class ForgotPasswordDialogFragment : DialogFragment() {
    val model by lazy { ViewModelProvider(this).get(ArticlesViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView: View = inflater.inflate(R.layout.forgot_password, container, false)

        rootView.btnCancelForgotPassword.setOnClickListener {
            dismiss()
        }

        rootView.btnSendForgotPassword.setOnClickListener {
            if (etForgotPassword.text.isNullOrBlank()) {
                Toast.makeText(context, "Remplissez le champ", Toast.LENGTH_SHORT).show()
            } else {
                model.postRegister(
                    "http://10.0.2.2:8082/send-password-forgot",
                    "{\n" +
                            "    \"emailToSend\": \"${etForgotPassword.text}\"\n" +
                            "}"
                )
            }
            dismiss()
            Toast.makeText(context, "E-mail envoyé", Toast.LENGTH_SHORT).show()
        }

        return rootView
    }
}