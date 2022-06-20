package com.example.project_kotlin.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project_kotlin.ProductsData
import com.example.project_kotlin.R
import com.example.project_kotlin.RecyclerAdapter
import com.example.project_kotlin.model.ArticlesViewModel

class HomeFragment : Fragment() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null
    val model by lazy { ViewModelProvider(this).get(ArticlesViewModel::class.java) }

    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<ProductsData>
    lateinit var images: Array<String>
    lateinit var titles: Array<String>
    lateinit var details: Array<String>
    lateinit var prices: Array<Int>
    lateinit var ids: Array<Int>
    lateinit var preferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        layoutManager = GridLayoutManager(context,2,RecyclerView.VERTICAL,false)
//        recyclerView.layoutManager = layoutManager
//        adapter = RecyclerAdapter()
//        recyclerView.adapter = adapter


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        images = arrayOf()
        titles = arrayOf()
        details = arrayOf()
        prices = arrayOf()
        ids = arrayOf()

        model.loadData()

        newRecyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        newRecyclerView.layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
        newRecyclerView.setHasFixedSize(true)

        newArrayList = arrayListOf<ProductsData>()

        //getUserdata()

        model.data.observe(viewLifecycleOwner) {

            println("test2")
            titles = arrayOf()

            if (it?.get(0)?.name.isNullOrEmpty()) {
                println("nothing")
            } else {
                if (it != null) {
                    println(it)
                    for (i in it.indices) {
                        images += arrayOf(it[i].imagePath)
                        titles += arrayOf(it[i].name)
                        details += arrayOf(it[i].description)
                        prices += arrayOf(it[i].price)
                        ids += arrayOf(it[i].id)
                    }
                }
                getUserdata()
            }

        }


    }

    private fun getUserdata() {
        newArrayList = arrayListOf<ProductsData>()
        for (i in titles.indices) {
            val product = ProductsData(images[i], titles[i], details[i], prices[i], ids[i])
            newArrayList.add(product)
        }

        var adapter = RecyclerAdapter(newArrayList)
        newRecyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : RecyclerAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
//                val intent = Intent(context, Product::class.java)
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
//                intent.putExtra("images", newArrayList[position].image)
//                intent.putExtra("titles", newArrayList[position].title)
//                intent.putExtra("details", newArrayList[position].detail)
//                startActivity(intent)
            }

        })
    }

    private fun showMeArrayList(arrayList: Array<String>) {
        for (element in arrayList) {
            println(element)
        }
    }
}