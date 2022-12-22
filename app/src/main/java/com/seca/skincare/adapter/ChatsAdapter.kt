package com.seca.skincare.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.seca.skincare.R
import com.seca.skincare.activity.HomeScreens
import com.seca.skincare.fragment.ConsumerProfileViewFragment
import com.seca.skincare.model.ConsultantModel


class ChatsAdapter(internal var c: Context, internal var spacecrafts: ArrayList<ConsultantModel>) : RecyclerView.Adapter<ChatsAdapter.MyViewHolder>() {
    /*
    class: MyViewHolder
    This is our View Holder class.
   */
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var titleText: AppCompatTextView = itemView.findViewById(R.id.title)
        internal var whtsappView: AppCompatImageView = itemView.findViewById(R.id.whtsapp)
        internal var profileView: AppCompatImageView = itemView.findViewById(R.id.profile)
    }

    override fun getItemCount(): Int {
        return spacecrafts.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(c).inflate(R.layout.chat_item, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //BIND DATA
        holder.titleText.text = spacecrafts[position].username

        holder.profileView.setOnClickListener {

            var bundle = Bundle()
            bundle.putInt("CONSUMER_ID",spacecrafts[position].id)
            var consumerProfileViewFragment  = ConsumerProfileViewFragment()
            consumerProfileViewFragment.arguments = bundle
            (c as HomeScreens).replaceFragment(consumerProfileViewFragment,"CONSUMER_ID",bundle)

        }

        holder.whtsappView.setOnClickListener {  val message = "Hallo"

            c.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(
                        String.format(
                            "https://api.whatsapp.com/send?phone=%s&text=%s",
                            spacecrafts[position].phone_code+spacecrafts[position].phone_no,
                            message
                        )
                    )
                )
            )

        }

//        holder.nameTxt.text = spacecrafts[position].title


    }
}