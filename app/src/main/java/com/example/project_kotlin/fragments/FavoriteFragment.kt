package com.example.project_kotlin.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.auth0.android.jwt.JWT
import com.example.project_kotlin.ProductsData
import com.example.project_kotlin.R
import com.example.project_kotlin.RecyclerAdapter
import com.example.project_kotlin.model.ArticlesViewModel


class FavoriteFragment : Fragment() {
    lateinit var preferences: SharedPreferences
    val model by lazy { ViewModelProvider(this).get(ArticlesViewModel::class.java) }

    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<ProductsData>
    lateinit var images: Array<String>
    lateinit var titles: Array<String>
    lateinit var details: Array<String>
    lateinit var prices: Array<Int>
    lateinit var ids: Array<Int>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preferences = activity?.getSharedPreferences("JWT", Context.MODE_PRIVATE)!!

        val myJwt = preferences.getString("JWT", "")
        println(myJwt)
        if (myJwt.isNullOrEmpty()) {
            println("JWT null")
        } else {
            val jwt = JWT(myJwt)
            var claim: String? = jwt.getClaim("id").asString()
            println(claim)

            model.loadFavoritesData("http://10.0.2.2:8083/favorite/15")
        }

        model.dataFavorite.observe(viewLifecycleOwner) {
            if (it != null) {
                for (i in it.boxElements) {
                    ids += arrayOf(i.boxEmb.articleId)
                }
            }
        }

        images = arrayOf(
            "https://via.placeholder.com/350x150",
            "https://via.placeholder.com/350x150",
            "https://via.placeholder.com/350x150"
        )
        titles = arrayOf("titles", "titles", "titles")
        details = arrayOf("details", "details", "details")
        prices = arrayOf(1, 2, 3)
        ids = arrayOf(1, 2, 3)

        newRecyclerView = view.findViewById(R.id.recyclerView)

        newRecyclerView.layoutManager = LinearLayoutManager(context)
        newRecyclerView.setHasFixedSize(true)

        newArrayList = arrayListOf()

        getUserdata()

    }

    private fun getUserdata() {
        newArrayList = arrayListOf()
        for (i in titles.indices) {
            val product = ProductsData(images[i], titles[i], details[i], prices[i], ids[i])
            newArrayList.add(product)
        }

        var adapter = RecyclerAdapter(newArrayList, "searchView")
        newRecyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : RecyclerAdapter.onItemClickListener {
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
