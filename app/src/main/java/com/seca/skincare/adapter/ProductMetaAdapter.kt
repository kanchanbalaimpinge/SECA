package com.seca.skincare.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.seca.skincare.R
import com.seca.skincare.model.MetaModel


class ProductMetaAdapter(internal var c: Context, internal var spacecrafts: ArrayList<MetaModel>) : RecyclerView.Adapter<ProductMetaAdapter.MyViewHolder>() {
    /*
    class: MyViewHolder
    This is our View Holder class.
   */
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var titleText: TextView = itemView.findViewById(R.id.title)
        internal var descriptionText: TextView = itemView.findViewById(R.id.description)
        internal var expandView: ImageView = itemView.findViewById(R.id.expand)
        internal var mainLayout: RelativeLayout = itemView.findViewById(R.id.do_dont)
    }

    override fun getItemCount(): Int {
        return spacecrafts.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(c).inflate(R.layout.product_meta_layout, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //BIND DATA
        holder.titleText.text = spacecrafts[position].title
        holder.descriptionText.text = spacecrafts[position].description

        holder.mainLayout.setOnClickListener{

            if(holder.descriptionText.isVisible){
                holder.descriptionText.visibility = View.GONE
                holder.expandView.setImageDrawable(c.resources.getDrawable(R.drawable.expand));
            }else{
                holder.descriptionText.visibility = View.VISIBLE
                holder.expandView.setImageDrawable(c.resources.getDrawable(R.drawable.down_arrow));
            }
        }

    }
}