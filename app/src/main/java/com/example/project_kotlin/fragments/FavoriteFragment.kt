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
import com.example.project_kotlin.Recycler.RecyclerAdapter
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
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        images = arrayOf()
        titles = arrayOf()
        details = arrayOf()
        prices = arrayOf()
        ids = arrayOf()

        //Je récupère le jwt pour avoir l'id et faire des requettes

        preferences = activity?.getSharedPreferences("JWT", Context.MODE_PRIVATE)!!

        val myJwt = preferences.getString("JWT", "")
        if (myJwt.isNullOrEmpty()) {
            println("JWT null")
        } else {
            val jwt = JWT(myJwt)
            var claim: String? = jwt.getClaim("id").asString()

            //je get les id des favories
            //Requette qui permet de récupère les favories n'est pas encore implementé en back-end

            model.loadFavoritesBasketsData("http://10.0.2.2:8083/favorite/$claim")
        }

        model.dataFavoriteBasket.observe(viewLifecycleOwner) {
            if (it != null) {
                println(it)
                for (i in it.boxElements) {
                    ids += arrayOf(i.boxEmb.articleId)
                }

                //je get les id des favories
                //Requette qui permet de récupère les favories grace a un tableau d'id n'est pas encore implementé en back-end
                model.loadArticlesByIds(
                    "{\n" +
                            "    \"articleIds\":$ids\n" +
                            "}"
                )


            }

        }

        model.dataOne.observe(viewLifecycleOwner) {
            //Le backend ne l'a pas encore implémenté donc pas encore fonctionnel
            if (it != null) {
                images += arrayOf(it.imagePath)
                details += arrayOf(it.description)
                prices += arrayOf(it.price)
                titles += arrayOf(it.name)

            }
        }

        model.dataOne.observe(viewLifecycleOwner) {
            println(it)
        }

        newRecyclerView = view.findViewById(R.id.recyclerView)

        newRecyclerView.layoutManager = LinearLayoutManager(context)
        newRecyclerView.setHasFixedSize(true)

        newArrayList = arrayListOf()

        getUserdata()


    }

    private fun getUserdata() {
        newArrayList = arrayListOf()
        println(titles.indices)
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

    private fun showMeArrayList(arrayList: Array<Int>) {
        for (element in arrayList) {
            println(element)
        }
    }

}
