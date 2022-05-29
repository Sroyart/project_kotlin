package com.example.project_kotlin

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class ProductList : AppCompatActivity() {
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<ProductsData>
    private lateinit var tempArrayList: ArrayList<ProductsData>
    lateinit var imageId: Array<String>
    lateinit var titles: Array<String>
    lateinit var details: Array<String>
    lateinit var prices: Array<Int>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        imageId = arrayOf("", "", "")
        titles = arrayOf("Meuble 1", "Meuble 2", "Meuble 3")
        details = arrayOf("Detail Meuble 1", "Detail Meuble 2", "Detail Meuble 3")

        newRecyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        newRecyclerView.layoutManager = LinearLayoutManager(this@ProductList)
        newRecyclerView.setHasFixedSize(true)

        newArrayList = arrayListOf<ProductsData>()
        tempArrayList = arrayListOf<ProductsData>()
        getUserdata()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)
        val item = menu?.findItem(R.id.search_action)
        val searchView = item?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                tempArrayList.clear()
                val searchText = newText!!.toLowerCase(Locale.getDefault())
                if (searchText.isNotEmpty()) {
                    newArrayList.forEach {
                        if (it.title.toLowerCase(Locale.getDefault()).contains(searchText)) {
                            tempArrayList.add(it)
                        }
                    }

                    newRecyclerView.adapter!!.notifyDataSetChanged()
                } else {

                    tempArrayList.clear()
                    tempArrayList.addAll(newArrayList)
                    newRecyclerView.adapter!!.notifyDataSetChanged()
                }

                return false
            }
        })
        return false
    }

    private fun getUserdata() {
        for (i in imageId.indices) {
            val product = ProductsData(imageId[i], titles[i], details[i], prices[i])
            newArrayList.add(product)
        }
        tempArrayList.addAll(newArrayList)

        var adapter = RecyclerAdapter(tempArrayList, "searchView")
        newRecyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : RecyclerAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(this@ProductList, Product::class.java)
                intent.putExtra("imageId", newArrayList[position].image)
                intent.putExtra("titles", newArrayList[position].title)
                intent.putExtra("details", newArrayList[position].detail)
                startActivity(intent)
            }

        })
    }
}