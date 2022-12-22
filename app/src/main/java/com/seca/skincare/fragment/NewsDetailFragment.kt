package com.seca.skincare.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

import com.seca.skincare.R
import com.seca.skincare.adapter.NewsAdapter
import com.seca.skincare.model.ProductModel



class NewsDetailFragment: Fragment() {
    private lateinit var newsApapter: NewsAdapter

    private var newsList : ArrayList<ProductModel> = ArrayList()
    lateinit var  view1 : View
    lateinit var  listView : RecyclerView
    lateinit var  backView : AppCompatImageView
    companion object {
        fun newInstance() = NewsDetailFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view1 =  inflater.inflate(R.layout.news_detail, container, false)



        return view1
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }


    private fun setAdapter() {


    }

    override fun onResume() {
        super.onResume()

    }


}