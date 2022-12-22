package com.seca.skincare.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.seca.skincare.R
import com.seca.skincare.fragment.AIScanFragment
import com.seca.skincare.model.AuthResponse
import com.seca.skincare.model.ImagesModel
import com.seca.skincare.retrofit.APIClient
import com.seca.skincare.retrofit.APIInterface
import com.seca.skincare.retrofit.response.RestResponse
import com.seca.skincare.utils.ErrorResponseHandler
import com.seca.skincare.utils.Utility
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class ScansAdapter(internal var c: Context, internal var spacecrafts: ArrayList<ImagesModel>,internal var scansAdapter: AIScanFragment ) : RecyclerView.Adapter<ScansAdapter.MyViewHolder>() {
    /*
    class: MyViewHolder
    This is our View Holder class.
   */
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var dateTxt: TextView = itemView.findViewById(R.id.date)
        internal var image: ImageView = itemView.findViewById(R.id.user_image)
        internal var cross: ImageView = itemView.findViewById(R.id.cross)
        internal var progress: ProgressBar = itemView.findViewById(R.id.progress)
    }

    override fun getItemCount(): Int {
        return spacecrafts.size
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(c).inflate(R.layout.scan_item, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //BIND DATA
        holder.dateTxt.text = spacecrafts[position].updated_at

        val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
        val output = SimpleDateFormat("dd MMMM")

        var d: Date? = null
        try {
            d = input.parse(spacecrafts[position].created_at)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val formatted: String = output.format(d)
        Log.i("DATE", "" + formatted)
        holder.dateTxt.text = formatted +", Score: "+ spacecrafts[position].skin_health_score

        holder.progress.visibility = View.VISIBLE

        var requestOptions = RequestOptions()
        requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(106))
        Glide.with(c)
            .load(spacecrafts[position].image_url)
            .apply(requestOptions)
            .into(object : CustomTarget<Drawable?>() {

                override fun onLoadCleared(@Nullable placeholder: Drawable?) {}
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable?>?
                ) {
                    holder.image.setBackground(resource)
                    holder.progress.visibility = View.GONE

                }
            })



        holder.cross.setOnClickListener {
            deleteScan(spacecrafts[position].id,position)
        }
//        Glide.with(c).load(spacecrafts[position].image_url).placeholder(R.drawable.noprofile).into(holder.image)
    }

    private fun deleteScan(id: Int, position: Int) {

        try {
            val utility: Utility = Utility.getInstance(c)

//
            utility.showLoading(c.resources.getString(R.string.please_wait))
            val apiInterface: APIInterface =
                APIClient.getClient(c).create(APIInterface::class.java)
            val callApi: Call<RestResponse<AuthResponse>> = apiInterface.removeFromScans(id)
            callApi.enqueue(object : Callback<RestResponse<AuthResponse>> {
                override fun onResponse(
                    call: Call<RestResponse<AuthResponse>>,
                    response: Response<RestResponse<AuthResponse>>
                ) {
                    utility.hideLoading()
                    if (response.code() == 200) {

                        //    Toast.makeText(c,response.message(), Toast.LENGTH_SHORT).show()
                        Toast.makeText(c,response.body()!!.message, Toast.LENGTH_SHORT).show()

                        spacecrafts.removeAt(position)
                        //  notifyItemChanged(pos)
                      notifyDataSetChanged()

                        (scansAdapter as AIScanFragment).getAIScanImages()


                    } else if (response.code() == 400) {
                        val message: String = ErrorResponseHandler.parseError(response)
                        Toast.makeText(c, message, Toast.LENGTH_SHORT).show()
                        utility.hideLoading()
                    } else {
                        Toast.makeText(c,response.message(), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<RestResponse<AuthResponse>>, t: Throwable) {
                    Log.e("exception_msg", t.message!!)
                    utility.hideLoading()
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}