package com.seca.skincare.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.seca.skincare.R
import com.seca.skincare.activity.NewsDetailScreens
import com.seca.skincare.model.NewsModel


class NewsAdapter(internal var c: Context, internal var spacecrafts: ArrayList<NewsModel>) : RecyclerView.Adapter<NewsAdapter.MyViewHolder>() {
    /*
    class: MyViewHolder
    This is our View Holder class.
   */
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var lvParentLayout: LinearLayout = itemView.findViewById(R.id.parent_layout)
        internal var tvTitle: AppCompatTextView = itemView.findViewById(R.id.news)
        internal var tvTime: AppCompatTextView = itemView.findViewById(R.id.time)
        internal var tvDescription: AppCompatTextView = itemView.findViewById(R.id.description)
        internal var ivImage: AppCompatImageView = itemView.findViewById(R.id.news_image)
        internal var btReadMore: AppCompatButton = itemView.findViewById(R.id.read_more)

    }

    override fun getItemCount(): Int {
        return spacecrafts.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(c).inflate(R.layout.news_item, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //BIND DATA
        holder.tvTitle.text = spacecrafts[position].title
        holder.tvDescription.text = spacecrafts[position].short_description
        holder.tvTime.text = spacecrafts[position].created_at +" ago"

        Glide.with(c).load(spacecrafts[position].image_url).into(holder.ivImage)

        holder.lvParentLayout.setOnClickListener {

            c.startActivity(Intent(c,NewsDetailScreens::class.java).putExtra("NEWS_OBJECT",spacecrafts.get(position)))

        }

        holder.btReadMore.setOnClickListener {

            c.startActivity(Intent(c,NewsDetailScreens::class.java).putExtra("NEWS_OBJECT",spacecrafts.get(position)))

        }

    }
}