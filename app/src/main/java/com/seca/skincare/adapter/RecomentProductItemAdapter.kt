package com.seca.skincare.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.seca.skincare.R
import com.seca.skincare.model.RecommendedProductItemModel
import kotlin.collections.ArrayList


class RecomentProductItemAdapter(internal var c: Context, internal var spacecrafts: ArrayList<RecommendedProductItemModel>) : RecyclerView.Adapter<RecomentProductItemAdapter.MyViewHolder>() {

    /*
    class: MyViewHolder
    This is our View Holder class.
   */

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var titleTxt: TextView = itemView.findViewById(R.id.title)
        internal var valueTxt: TextView = itemView.findViewById(R.id.value)
    }

    override fun getItemCount(): Int {
        return spacecrafts.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(c).inflate(R.layout.recommendation_item_layout, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
            //BIND DATA

            holder.titleTxt.text = spacecrafts[position].product_name
            holder.valueTxt.text = spacecrafts[position].comment

    }
}