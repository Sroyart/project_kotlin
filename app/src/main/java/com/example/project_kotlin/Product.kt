package com.example.project_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class Product : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        val titleProduct: TextView = findViewById(R.id.product_title)
        val detailProduct: TextView = findViewById(R.id.product_detail)
        val imageProduct: ImageView = findViewById(R.id.product_image)

        val bundle : Bundle? = intent.extras
        val title = bundle!!.getString("titles")
        val detail = bundle.getString("details")
        val image = bundle.getInt("imageId")

        println("title")
        println(title)

        titleProduct.text = title
        detailProduct.text = detail
        imageProduct.setImageResource(image)

    }
}