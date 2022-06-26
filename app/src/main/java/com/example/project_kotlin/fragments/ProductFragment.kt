package com.example.project_kotlin.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.auth0.android.jwt.JWT
import com.example.project_kotlin.PaymentActivity
import com.example.project_kotlin.R
import com.example.project_kotlin.model.ArticlesViewModel
import com.squareup.picasso.Picasso


class ProductFragment : Fragment() {
    lateinit var preferences: SharedPreferences
    val model by lazy { ViewModelProvider(this).get(ArticlesViewModel::class.java) }
    var claim = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_product, container, false)
        val textViewTitleProduct: TextView = view.findViewById(R.id.tv_product_title)
        val textViewDetailProduct: TextView = view.findViewById(R.id.tv_product_detail)
        val textViewImageProduct: ImageView = view.findViewById(R.id.img_product_image)
        val textViewPriceProduct: TextView = view.findViewById(R.id.tv_product_price)
        val btnPayment: TextView = view.findViewById(R.id.btn_payment)
        val checkBoxFavProduct: CheckBox = view.findViewById(R.id.checkBox_fav_product)
        val btn_add_basket: Button = view.findViewById(R.id.btn_add_basket)
        val etProductNumber: EditText = view.findViewById(R.id.et_product_number)

        val args = this.arguments
        val productTitle = args?.get("titleData")
        val productDetail = args?.get("titleDetail")
        val productImage = args?.get("titleImage") as String
        val productPrice = args?.get("titlePrice")
        val productId = args?.get("idData")

        preferences = activity?.getSharedPreferences("JWT", Context.MODE_PRIVATE)!!

        val myJwt = preferences.getString("JWT", "")
        if (myJwt.isNullOrEmpty()) {
            println("JWT null")
        } else {
            val jwt = JWT(myJwt)
            claim = jwt.getClaim("id").asString().toString()

            model.loadFavoritesData("http://10.0.2.2:8083/favorite/$claim")
        }

        model.dataFavorite.observe(viewLifecycleOwner) {
            if (it != null) {
                for (i in it.boxElements) {
                    if (productId == i.boxEmb.articleId) {
                        checkBoxFavProduct.isChecked = true;
                    }


                }
            }

        }




        textViewTitleProduct.text = productTitle.toString()
        textViewDetailProduct.text = productDetail.toString()
        Picasso.get()
            .load(productImage)
            .into(textViewImageProduct)
        textViewPriceProduct.text = productPrice.toString() + " €"

        btn_add_basket.setOnClickListener {
            model.loadPostData(
                "http://10.0.2.2:8083/basket", "{\n" +
                        "    \"customerId\": $claim,\n" +
                        "    \"basketEmb\": {\n" +
                        "        \"amount\": ${etProductNumber.text}\n" +
                        "    },\n" +
                        "    \"boxEmb\": {\n" +
                        "        \"articleId\": $productId\n" +
                        "    }\n" +
                        "}"
            )


        }

        btnPayment.setOnClickListener {
            val intent = Intent(context, PaymentActivity::class.java)
            startActivity(intent)
        }

        model.dataElk.observe(viewLifecycleOwner) {
            Toast.makeText(context, "Ajouté au panier", Toast.LENGTH_SHORT).show()
        }





        checkBoxFavProduct.setOnClickListener {
            if (checkBoxFavProduct.isChecked) {
                model.loadPostData(

                    "http://10.0.2.2:8083/favorite", "{\n" +
                            "    \"customerId\": $claim,\n" +
                            "    \"boxEmb\": {\n" +
                            "        \"articleId\": $productId\n" +
                            "    }\n" +
                            "}"
                )


            } else {
                model.loadPostData(

                    "http://10.0.2.2:8083/favorite/remove", "{\n" +
                            "    \"customerId\": $claim,\n" +
                            "    \"articleId\": $productId\n" +
                            "}"
                )
                println(checkBoxFavProduct.isChecked)

            }

        }



        return view
    }


}