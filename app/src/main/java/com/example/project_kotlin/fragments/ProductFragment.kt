package com.example.project_kotlin.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.project_kotlin.R
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


class ProductFragment : Fragment() {
    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_product, container, false)
        val textViewTitleProduct: TextView = view.findViewById(R.id.tv_product_title)
        val textViewDetailProduct: TextView = view.findViewById(R.id.tv_product_detail)
        val textViewImageProduct: ImageView = view.findViewById(R.id.img_product_image)
        val textViewPriceProduct: TextView = view.findViewById(R.id.tv_product_price)
        val btnBasket: TextView = view.findViewById(R.id.btn_basket)
        val checkBoxFavProduct: CheckBox = view.findViewById(R.id.checkBox_fav_product)

        val args = this.arguments
        val productTitle = args?.get("titleData")
        val productDetail = args?.get("titleDetail")
        val productImage = args?.get("titleImage") as String
        val productPrice = args?.get("titlePrice")
        val productId = args?.get("idData")



        textViewTitleProduct.text = productTitle.toString()
        textViewDetailProduct.text = productDetail.toString()
        Picasso.get()
            .load(productImage)
            .into(textViewImageProduct)
        textViewPriceProduct.text = productPrice.toString() + " €"

        btnBasket.setOnClickListener {
            //if (textViewTitleProduct.text.isNullOrBlank() && textViewDetailProduct.text.isNullOrBlank() && )
            fragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, AdressPaymentFragment())?.commit()
        }



        checkBoxFavProduct.isChecked = true;


        checkBoxFavProduct.setOnClickListener {
            Toast.makeText(context, productId.toString(), Toast.LENGTH_SHORT).show()
            lifecycleScope.launch {
                saveStore("1", 1)
                val result = readStore("1")
                println(result)
                if (result != null) {
                    Toast.makeText(context, result.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }



        return view
    }


    suspend fun saveStore(dataKey: String, value: Int) {
        val dataStoreKey = intPreferencesKey(dataKey)
        context?.dataStore?.edit { settings ->
            val currentCounterValue = settings[dataStoreKey] ?: 0
            settings[dataStoreKey] = value
        }
    }

    suspend fun readStore(dataKey: String): Int? {
        val dataStoreKey = intPreferencesKey(dataKey)
        val preferences = context?.dataStore?.data?.first()
        return preferences?.get(dataStoreKey)
    }


}