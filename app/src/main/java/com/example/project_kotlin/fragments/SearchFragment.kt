package com.example.project_kotlin.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project_kotlin.R
import com.example.project_kotlin.TypeAdapter
import com.example.project_kotlin.TypeData
import java.util.*


class SearchFragment : Fragment() {
    private lateinit var newRecyclerView: RecyclerView
    private lateinit var newArrayList: ArrayList<TypeData>
    private lateinit var tempArrayList: ArrayList<TypeData>
    lateinit var id: Array<Int>
    lateinit var type: Array<String>
    private val filterSearchFragment = FilterSearchFragment()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)

        return inflater.inflate(R.layout.fragment_search, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        id = arrayOf(0, 1, 2)
        type = arrayOf("Bureau", "Chaise", "Table")

        newRecyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        newRecyclerView.layoutManager = LinearLayoutManager(context)
        newRecyclerView.setHasFixedSize(true)

        newArrayList = arrayListOf<TypeData>()
        tempArrayList = arrayListOf<TypeData>()
        getUserdata()
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
        for (i in id.indices) {
            val product = TypeData(id[i], type[i])
            newArrayList.add(product)
        }
        tempArrayList.addAll(newArrayList)

        var adapter = TypeAdapter(tempArrayList)
        newRecyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : TypeAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
//                val intent = Intent(context, ProductList::class.java)
//                intent.putExtra("type", newArrayList[position].type)
//                startActivity(intent)

                replaceFragment(filterSearchFragment)


            }

        })

    }

    private fun replaceFragment(fragment: Fragment) {
        val transaction = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.fragment_container, fragment)
        transaction?.commit()
    }
}