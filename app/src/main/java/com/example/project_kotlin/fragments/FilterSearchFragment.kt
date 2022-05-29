package com.example.project_kotlin.fragments

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project_kotlin.*
import com.example.project_kotlin.model.ArticlesViewModel
import kotlinx.android.synthetic.main.fragment_filter_search.*
import java.util.*

class FilterSearchFragment : Fragment() {

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null
    val model by lazy { ViewModelProvider(this).get(ArticlesViewModel::class.java) }

    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<ProductsData>
    private lateinit var tempArrayList: ArrayList<ProductsData>
    lateinit var imageId: Array<String>
    lateinit var titles: Array<String>
    lateinit var details: Array<String>
    lateinit var prices: Array<Int>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_filter_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageId = arrayOf()
        titles = arrayOf()
        details = arrayOf("Detail Meuble 1", "Detail Meuble 2", "Detail Meuble 3")
        prices = arrayOf()

        showMeArrayList(titles)

        model.loadData()
        //model.loadPostData("")


        newRecyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        newRecyclerView.layoutManager = LinearLayoutManager(context)
        newRecyclerView.setHasFixedSize(true)

        newArrayList = arrayListOf<ProductsData>()
        tempArrayList = arrayListOf<ProductsData>()

        //getUserdata()

        model.data.observe(viewLifecycleOwner) {
            titles = arrayOf()
            if (it?.get(0)?.name.isNullOrEmpty()) {
                println("nothing")
            } else {
                if (it != null) {
                    for (i in it.indices) {
                        println(it[i].name)
                        imageId += arrayOf(it[i].imagePath)
                        titles += arrayOf(it[i].name)
                        prices += arrayOf(it[i].price)
                    }
                }
                getUserdata()
            }

        }

        model.dataElk.observe(viewLifecycleOwner) {
            titles = arrayOf()
            if (it === null) {
                println("nothing")
            } else {
                println("something")
                if (it.hits.hits.isNotEmpty()) {
                    println(it.hits.hits[0])
                    titles = arrayOf()
                    for (i in it.hits.hits.indices) {
                        println(it.hits.hits[i])
                        titles += arrayOf(it.hits.hits[i]._index)
                        prices += arrayOf(10)
                    }
                    showMeArrayList(titles)
                    getUserdata()

                } else {
                    println("hits is equal to something")
                    println(it.hits.hits.size)
                }
//                println("hits : " + it?.hits?.hits[0])
//                if (it?.hits?.hits[0] === null) {
//                    for (i in it.hits.hits.indices) {
//                        println(it.hits.hits[i])
//                        titles += arrayOf(it.hits.hits[i]._index)
//                    }
//                }
//                println(titles)
//                getUserdata()
            }

        }

        filterButton.setOnClickListener {
            var dialog = FilterDialogFragment()

            activity?.let { it1 -> dialog.show(it1.supportFragmentManager, "customDialog") }
        }


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
        val item = menu?.findItem(R.id.search_action)
        val searchView = item?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                model.loadPostData(
                    "http://10.0.2.2:8081/api/elk/fuzzy",
                    "{\n" +
                            "    \"name\":\"$query\",\n" +
                            "    \"category\":[],\n" +
                            "    \"material\":[],\n" +
                            "    \"color\":[]\n" +
                            "}"
                )

//                tempArrayList.clear()
//                val searchText = query!!.toLowerCase(Locale.getDefault())
//                if (searchText.isNotEmpty()) {
//                    newArrayList.forEach {
//                        if (it.title.toLowerCase(Locale.getDefault()).contains(searchText)) {
//                            tempArrayList.add(it)
//                        }
//                    }
//
//                    newRecyclerView.adapter!!.notifyDataSetChanged()
//                } else {
//
//                    tempArrayList.clear()
//                    tempArrayList.addAll(newArrayList)
//                    newRecyclerView.adapter!!.notifyDataSetChanged()
//                }

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun getUserdata() {
        newArrayList = arrayListOf<ProductsData>()
        tempArrayList = arrayListOf<ProductsData>()
        for (i in titles.indices) {
            val product = ProductsData(imageId[i], titles[i], details[0], prices[i])
            newArrayList.add(product)
        }
        println(newArrayList)

        var adapter = RecyclerAdapter(newArrayList, "searchView")
        newRecyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : RecyclerAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(context, Product::class.java)
                intent.putExtra("imageId", newArrayList[position].image)
                intent.putExtra("titles", newArrayList[position].title)
                intent.putExtra("details", newArrayList[position].detail)
                startActivity(intent)
            }

        })
    }

    private fun showMeArrayList(arrayList: Array<String>) {
        for (element in arrayList) {
            println(element)
        }
    }
}