package com.seca.skincare.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.RecyclerView
import com.seca.skincare.R
import com.seca.skincare.model.SkinResultsModel
import kotlin.collections.ArrayList


class AnalysisItemAdapter(internal var c: Context, internal var spacecrafts: ArrayList<SkinResultsModel>,var key:String) : RecyclerView.Adapter<AnalysisItemAdapter.MyViewHolder>() {
    /*
    class: MyViewHolder
    This is our View Holder class.
   */
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var titleTxt: TextView = itemView.findViewById(R.id.title)
        internal var valueTxt: TextView = itemView.findViewById(R.id.value)
        internal var analysis_itemLv: LinearLayoutCompat = itemView.findViewById(R.id.analysis_item)
    }

    override fun getItemCount(): Int {
        return spacecrafts.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(c).inflate(R.layout.analysis_item, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //BIND DATA

        if(spacecrafts[position].category.equals(key,true)) {
            holder.analysis_itemLv.visibility = View.VISIBLE

            holder.titleTxt.text = spacecrafts[position].name
            holder.valueTxt.text = spacecrafts[position].value.toString()
        }else{
            holder.analysis_itemLv.visibility = View.GONE
        }

    }
}