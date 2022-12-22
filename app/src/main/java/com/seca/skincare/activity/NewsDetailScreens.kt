package com.seca.skincare.activity

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.seca.skincare.R
import com.seca.skincare.model.NewsModel
import me.relex.circleindicator.CircleIndicator3

class NewsDetailScreens : BaseActivity() , View.OnClickListener{
    lateinit var viewPager2 : ViewPager2;
    lateinit var titleView : TextView;
    lateinit var subtitleView : TextView;
    lateinit var indicatore : CircleIndicator3;
    lateinit var tvTitle: AppCompatTextView
    lateinit var tvDescription: AppCompatTextView
    lateinit var tvTime: AppCompatTextView
    lateinit var ivNewsImage: AppCompatImageView
    lateinit var ivBack: AppCompatImageView
    lateinit var news_object : NewsModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.news_detail)

        tvTitle = findViewById(R.id.news)
        tvDescription = findViewById(R.id.description)
        tvTime = findViewById(R.id.time)
        ivBack = findViewById(R.id.back)
        ivNewsImage = findViewById(R.id.news_image);

        ivBack.setOnClickListener {

            finish()
        }

        news_object = intent.getSerializableExtra("NEWS_OBJECT") as NewsModel

        tvTitle.setText(news_object.title)
        tvDescription.setText(news_object.long_description)
        tvTime.setText(news_object.created_at)

        Glide.with(this).load(news_object.image_url).into(ivNewsImage)

    }


    override fun onClick(view: View?) {


    }

}