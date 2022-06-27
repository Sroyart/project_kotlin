package com.example.project_kotlin.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project_kotlin.ProductsData
import com.example.project_kotlin.R
import com.example.project_kotlin.Recycler.RecyclerAdapter
import com.example.project_kotlin.model.ArticlesViewModel
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
    lateinit var ids: Array<Int>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        val view = inflater.inflate(R.layout.fragment_filter_search, container, false)

        val args = this.arguments
        val type = args?.get("type")
        println("type")
        println(type)
        println("type")
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageId = arrayOf()
        titles = arrayOf()
        details = arrayOf("Detail Meuble 1", "Detail Meuble 2", "Detail Meuble 3")
        prices = arrayOf()
        ids = arrayOf()


        model.loadData("")
        //model.loadPostData("")
        model.loadPostData(
            "http://10.0.2.2:8081/api/elk/fuzzy",
            "{\n" +
                    "    \"name\":\"\",\n" +
                    "    \"category\":[],\n" +
                    "    \"material\":[],\n" +
                    "    \"color\":[]\n" +
                    "}"
        )


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
                        details += arrayOf(it[i].description)
                        ids += arrayOf(i)
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
                if (it.hits.hits.isNotEmpty()) {
                    println(it.hits.hits[0])
                    titles = arrayOf()
                    for (i in it.hits.hits.indices) {
                        println(it.hits.hits[i])
                        titles += arrayOf(it.hits.hits[i]._index)
                        prices += arrayOf(10)
                        details += arrayOf("Description")
                        ids += arrayOf(it.hits.hits[i]._source.articleId)
                    }
                    getUserdata()

                } else {
                    println(it.hits.hits.size)
                }
            }

        }


    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_search, menu)
        val item = menu?.findItem(R.id.search_action)
        val searchView = item?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                //Ici j'envoie ma requete pour elasticksearch name fonctionne mais les autres ne sont pas implémenté dans l'api
                model.loadPostData(
                    "http://10.0.2.2:8081/elk/fuzzy",
                    "{\n" +
                            "    \"name\":\"$query\",\n" +
                            "    \"category\":[],\n" +
                            "    \"material\":[],\n" +
                            "    \"color\":[]\n" +
                            "}"
                )
                getUserdata()

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun getUserdata() {
        newArrayList = arrayListOf()
        tempArrayList = arrayListOf()
        for (i in titles.indices) {
            val product = ProductsData(imageId[i], titles[i], details[i], prices[i], ids[i])
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