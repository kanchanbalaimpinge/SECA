package com.seca.skincare.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.seca.skincare.R
import com.seca.skincare.adapter.NewsAdapter
import com.seca.skincare.model.NewsModel
import com.seca.skincare.retrofit.APIClient
import com.seca.skincare.retrofit.APIInterface
import com.seca.skincare.retrofit.response.ListResponse
import com.seca.skincare.utils.ErrorResponseHandler
import com.seca.skincare.utils.Utility
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


class NewsFragment: Fragment() {
    private lateinit var newsApapter: NewsAdapter

    private var newsList : ArrayList<NewsModel> = ArrayList()
    lateinit var  view1 : View
    lateinit var  listView : RecyclerView
    companion object {
        fun newInstance() = NewsFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        view1 =  inflater.inflate(R.layout.fragment_news, container, false)
        listView = view1.findViewById(R.id.rv)

        return view1
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val llm = LinearLayoutManager(activity)
        llm.orientation = LinearLayoutManager.VERTICAL
        listView.layoutManager = llm
        newsApapter = NewsAdapter(requireContext(), newsList)
        listView.adapter = newsApapter
        getNewsList()
    }


    private fun setAdapter() {
        if(newsApapter == null) {
            val llm = LinearLayoutManager(activity)
            llm.orientation = LinearLayoutManager.VERTICAL
            listView.layoutManager = llm
            newsApapter = NewsAdapter(requireContext(), newsList)
            listView.adapter = newsApapter
        }else{
            newsApapter.notifyDataSetChanged()
        }

    }

    override fun onResume() {
        super.onResume()
    }

    private fun getNewsList() {
        try {
            val utility: Utility = Utility.getInstance(activity)

            utility.showLoading(getString(R.string.please_wait))
            val apiInterface: APIInterface =
                APIClient.getClient(activity).create(APIInterface::class.java)
            val callApi: Call<ListResponse<NewsModel>> = apiInterface.news
            callApi.enqueue(object : Callback<ListResponse<NewsModel>> {
                override fun onResponse(
                    call: Call<ListResponse<NewsModel>>,
                    response: Response<ListResponse<NewsModel>>
                ) {
                    utility.hideLoading()
                    if (response.code() == 200) {


                        if(response.body()?.result!= null && response.body()?.result!!.size>0){
                            newsList.addAll(response.body()?.result!!)
                        }
                        setAdapter()
                    } else if (response.code() == 400) {
                        val message: String = ErrorResponseHandler.parseError(response)
                        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
                        utility.hideLoading()
                    } else {
                        Toast.makeText(activity,response.message(), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ListResponse<NewsModel>>, t: Throwable) {
                    Log.e("exception_msg", t.message!!)
                    utility.hideLoading()
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}