package com.example.project_kotlin.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.project_kotlin.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_product.*


class ProductFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_product, container, false)
        val textViewTitleProduct: TextView = view.findViewById(R.id.tv_product_title)
        val textViewDetailProduct: TextView = view.findViewById(R.id.tv_product_detail)
        val textViewImageProduct: ImageView = view.findViewById(R.id.img_product_image)
        val textViewPriceProduct: TextView = view.findViewById(R.id.tv_product_price)

        val args = this.arguments
        val productTitle = args?.get("titleData")
        val productDetail = args?.get("titleDetail")
        val productImage = args?.get("titleImage") as String
        val productPrice = args?.get("titlePrice")


        textViewTitleProduct.text = productTitle.toString()
        textViewDetailProduct.text = productDetail.toString()
        Picasso.get()
            .load(productImage)
            .into(textViewImageProduct)
        textViewPriceProduct.text = productPrice.toString() + " €"

        btn_basket.setOnClickListener {
            fragmentManager?.beginTransaction()
                ?.replace(R.id.fragment_container, AdressPaymentFragment())?.commit()
        }



        return view
    }
}