package com.example.project_kotlin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.project_kotlin.R


class AdressPaymentFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_adress_payment, container, false)
        val etAddressPaymentFirstName: EditText =
            view.findViewById(R.id.et_address_payment_firstName)

        val createBtn: Button = view.findViewById(R.id.btn_address_payment_next)
        createBtn.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("titleData", etAddressPaymentFirstName.text.toString())
            val fragment = Payment()
            fragment.arguments = bundle
            fragmentManager?.beginTransaction()?.replace(R.id.fragment_container, fragment)
                ?.commit()
        }

        return view
    }

}