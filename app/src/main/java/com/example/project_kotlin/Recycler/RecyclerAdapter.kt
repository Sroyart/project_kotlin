package com.example.project_kotlin.Recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project_kotlin.ProductsData
import com.example.project_kotlin.R
import com.squareup.picasso.Picasso

class RecyclerAdapter(
    private val newsList: ArrayList<ProductsData>,
    private val wichActivity: String = ""
) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    private lateinit var mListener: onItemClickListener

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.card_layout, parent, false)
        val searchView =
            LayoutInflater.from(parent.context).inflate(R.layout.search_layout, parent, false)
        if (wichActivity === "searchView") {
            itemView = searchView
        }
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = newsList[position]
        holder.itemTitle.text = currentItem.title
        holder.itemDetail.text = currentItem.detail
        holder.itemPrice.text = currentItem.price.toString() + " €"
        Picasso.get()
            .load(currentItem.image)
            .into(holder.itemImage)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    class ViewHolder(itemView: View, listener: onItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        val itemImage: ImageView = itemView.findViewById(R.id.item_image)
        val itemTitle: TextView = itemView.findViewById(R.id.item_title)
        val itemDetail: TextView = itemView.findViewById(R.id.item_detail)
        val itemPrice: TextView = itemView.findViewById(R.id.item_price)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }


}