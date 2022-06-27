package com.example.project_kotlin.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project_kotlin.R
import com.example.project_kotlin.TypeAdapter
import com.example.project_kotlin.TypeData
import com.example.project_kotlin.model.ArticlesViewModel
import java.util.*


class SearchFragment : Fragment() {
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<TypeData>
    private lateinit var tempArrayList: ArrayList<TypeData>
    lateinit var id: Array<Int>
    lateinit var type: Array<String>
    private val filterSearchFragment = FilterSearchFragment()
    val model by lazy { ViewModelProvider(this).get(ArticlesViewModel::class.java) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        return inflater.inflate(R.layout.fragment_search, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newRecyclerView = view.findViewById(R.id.recyclerView)

        newRecyclerView.layoutManager = LinearLayoutManager(context)
        newRecyclerView.setHasFixedSize(true)

        newArrayList = arrayListOf()
        tempArrayList = arrayListOf()
        id = arrayOf(0)
        type = arrayOf("Tous")

        model.loadCategories("http://10.0.2.2:80/api/categories")

        model.dataCategories.observe(viewLifecycleOwner) {
            if (it != null) {
                if (model.threadOneCategoriesRunning.value == false) {

                    for (i in it) {
                        id += arrayOf(i.id)
                        type += arrayOf(i.room)
                    }
                    getUserdata()

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
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                tempArrayList.clear()
                val searchText = newText!!.toLowerCase(Locale.getDefault())
                if (searchText.isNotEmpty()) {
                    newArrayList.forEach {
                        if (it.type.toLowerCase(Locale.getDefault()).contains(searchText)) {
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
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun getUserdata() {
        for (i in type.indices) {
            val product = TypeData(id[i], type[i])
            newArrayList.add(product)
        }
        tempArrayList.addAll(newArrayList)

        var adapter = TypeAdapter(tempArrayList)
        newRecyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : TypeAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                //Grace au bundle j'envoie les filtres au fragment qui utilise elasticksearch
                val bundle = Bundle()
                bundle.putString("type", newArrayList[position].type)
                filterSearchFragment.arguments = bundle
                fragmentManager?.beginTransaction()
                    ?.replace(R.id.fragment_container, filterSearchFragment)
                    ?.commit()


            }

        })

    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.fragment_container, fragment)
        transaction?.commit()
    }
}