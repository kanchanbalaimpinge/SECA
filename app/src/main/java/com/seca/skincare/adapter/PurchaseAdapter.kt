package com.seca.skincare.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.seca.skincare.R
import com.seca.skincare.model.PurchaseOrderModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class


PurchaseAdapter(internal var c: Context, internal var spacecrafts: ArrayList<PurchaseOrderModel>) : RecyclerView.Adapter<PurchaseAdapter.MyViewHolder>() {
    /*
    class: MyViewHolder
    This is our View Holder class.
   */
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var nameTxt: TextView = itemView.findViewById(R.id.product_name)
        internal var dateTxt: TextView = itemView.findViewById(R.id.date)
        internal var orderTxt: TextView = itemView.findViewById(R.id.order_id)
    }

    override fun getItemCount(): Int {
        return spacecrafts.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(c).inflate(R.layout.purchase_item, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //BIND DATA
        holder.dateTxt.text = spacecrafts[position].updated_at
        holder.orderTxt.text = spacecrafts[position].midtrans_order_id as String


    }
}