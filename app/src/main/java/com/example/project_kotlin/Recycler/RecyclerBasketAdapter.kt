package com.example.project_kotlin.Recycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.project_kotlin.BasketData
import com.example.project_kotlin.R
import com.squareup.picasso.Picasso

class RecyclerBasketAdapter(private val newsList: ArrayList<BasketData>) :
    RecyclerView.Adapter<RecyclerBasketAdapter.ViewHolder>() {
    private lateinit var mListener: OnItemClickListener

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.basket_list, parent, false)
        return ViewHolder(itemView, mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = newsList[position]
        holder.itemTitle.text = currentItem.title
        holder.itemDetail.text = currentItem.detail
        "${currentItem.price.toString()} €".also { holder.itemPrice.text = it }
        Picasso.get()
            .load(currentItem.image)
            .into(holder.itemImage)
        holder.itemAmount.text = currentItem.amount.toString()
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    class ViewHolder(itemView: View, listener: OnItemClickListener) :
        RecyclerView.ViewHolder(itemView) {
        val itemImage: ImageView = itemView.findViewById(R.id.item_image)
        val itemTitle: TextView = itemView.findViewById(R.id.item_title)
        val itemDetail: TextView = itemView.findViewById(R.id.item_detail)
        val itemPrice: TextView = itemView.findViewById(R.id.item_price)
        val itemAmount: TextView = itemView.findViewById(R.id.item_amount)

        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }
}