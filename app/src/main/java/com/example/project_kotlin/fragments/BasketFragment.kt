package com.example.project_kotlin.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.auth0.android.jwt.JWT
import com.example.project_kotlin.BasketData
import com.example.project_kotlin.R
import com.example.project_kotlin.Recycler.RecyclerBasketAdapter
import com.example.project_kotlin.model.ArticlesViewModel

class BasketFragment : Fragment() {

    lateinit var preferences: SharedPreferences
    val model by lazy { ViewModelProvider(this).get(ArticlesViewModel::class.java) }


    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<BasketData>
    lateinit var images: Array<String>
    lateinit var titles: Array<String>
    lateinit var details: Array<String>
    lateinit var prices: Array<Int>
    lateinit var ids: Array<Int>
    lateinit var amounts: Array<Int>

    var total: Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_basket, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        images = arrayOf()
        titles = arrayOf()
        details = arrayOf()
        prices = arrayOf()
        ids = arrayOf()
        amounts = arrayOf()

        val itemImage: TextView = view.findViewById(R.id.basket_tv_total_price)


        preferences = activity?.getSharedPreferences("JWT", Context.MODE_PRIVATE)!!

        val myJwt = preferences.getString("JWT", "")
        if (myJwt.isNullOrEmpty()) {
            println("JWT null")
        } else {
            val jwt = JWT(myJwt)
            var claim: String? = jwt.getClaim("id").asString()

            model.loadFavoritesData("http://10.0.2.2:8083/basket/15")
        }

        model.dataFavorite.observe(viewLifecycleOwner) {
            if (it != null) {
                println(it)
                for (i in it.boxElements) {
                    ids += arrayOf(i.boxEmb.articleId)
                    amounts += arrayOf(i.basketEmb.amount)
//                    println(i.boxEmb.articleId.toString())
                    model.loadOneData(i.boxEmb.articleId.toString())
                }
                getUserdata()
            }
        }

        model.dataOne.observe(viewLifecycleOwner) {
            if (it != null) {
                images += arrayOf(it.imagePath)
                details += arrayOf(it.description)
                prices += arrayOf(it.price)
                titles += arrayOf(it.name)
            }
        }

        newRecyclerView = view.findViewById(R.id.recyclerView)

        newRecyclerView.layoutManager = LinearLayoutManager(context)
        newRecyclerView.setHasFixedSize(true)

        newArrayList = arrayListOf()

        "$total €".also { itemImage.text = it }


    }

    private fun getUserdata() {
        total = 0
        newArrayList = arrayListOf()
        for (i in titles.indices) {
            val product =
                BasketData(images[i], titles[i], details[i], prices[i], ids[i], amounts[i])
            newArrayList.add(product)
            total += prices[i] * amounts[i]
        }

        var adapter = RecyclerBasketAdapter(newArrayList)
        newRecyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : RecyclerBasketAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val bundle = Bundle()
                bundle.putString("titleData", newArrayList[position].title)
                bundle.putString("titleDetail", newArrayList[position].detail)
                bundle.putString("titleImage", newArrayList[position].image)
                bundle.putInt("titlePrice", newArrayList[position].price)
                bundle.putInt("idData", newArrayList[position].id)
                val fragment = ProductFragment()
                fragment.arguments = bundle
                fragmentManager?.beginTransaction()?.replace(R.id.fragment_container, fragment)
                    ?.commit()
            }

        })
    }
}