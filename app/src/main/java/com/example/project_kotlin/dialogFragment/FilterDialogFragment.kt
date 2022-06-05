package com.example.project_kotlin.dialogFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.DialogFragment
import com.example.project_kotlin.R
import kotlinx.android.synthetic.main.search_filter.*
import kotlinx.android.synthetic.main.search_filter.view.*

class FilterDialogFragment : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var rootView: View = inflater.inflate(R.layout.search_filter, container, false)

        rootView.button_filter_search.setOnClickListener {
            val selectedID = radioFilter.checkedRadioButtonId
            val radio = rootView.findViewById<RadioButton>(selectedID)

            var ratingResult = radio.text.toString()

            dismiss()


        }

        return rootView
    }
}