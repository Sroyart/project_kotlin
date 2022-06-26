package com.example.project_kotlin.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.auth0.android.jwt.JWT
import com.example.project_kotlin.PaymentActivity
import com.example.project_kotlin.R
import com.example.project_kotlin.model.ArticlesViewModel
import com.example.project_kotlin.model.MEDIA_TYPE_JSON
import com.example.project_kotlin.model.client
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody


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
        val btnBasket: TextView = view.findViewById(R.id.btn_basket)
        val checkBoxFavProduct: CheckBox = view.findViewById(R.id.checkBox_fav_product)

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

        btnBasket.setOnClickListener {
            val intent = Intent(context, PaymentActivity::class.java)
            startActivity(intent)
        }





        checkBoxFavProduct.setOnClickListener {
            if (checkBoxFavProduct.isChecked)
                lifecycleScope.launch {
                    sendPost(
                        "http://localhost:8083/favorite", "{\n" +
                                "    \"customerId\": $claim,\n" +
                                "    \"boxEmb\": {\n" +
                                "        \"articleId\": $productId\n" +
                                "    }\n" +
                                "}"
                    )
                }
        }



        return view
    }

    fun sendPost(url: String, paramJson: String): String {
        println(paramJson)

        //Corps de la requête
        val body = paramJson.toRequestBody(MEDIA_TYPE_JSON)

        //Création de la requete
        val request = Request.Builder().url(url).post(body).build()

        //Execution de la requête
        return client.newCall(request).execute().use {
            //Analyse du code retour
            if (!it.isSuccessful) {
                throw Exception("Réponse du serveur incorrect :${it.code}")
            }
            //Résultat de la requete
            it.body?.string() ?: ""
        }
    }


}