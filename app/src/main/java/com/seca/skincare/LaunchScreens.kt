package com.seca.skincare

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.seca.skincare.activity.BaseActivity
import com.seca.skincare.activity.SignupScreens
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import me.relex.circleindicator.CircleIndicator3
import com.seca.skincare.adapter.ViewPagerAdapter2 as ViewPagerAdapter2

class LaunchScreens : BaseActivity() {
    lateinit var viewPager2 : ViewPager2;
    lateinit var titleView : TextView;
    lateinit var subtitleView : TextView;
    lateinit var indicatore : CircleIndicator3;
    lateinit var tabLayout : TabLayout;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)
        titleView = findViewById(R.id.title)
        subtitleView = findViewById(R.id.subtitle)
        setUpViewPager();
       var  sharedPreferences = getSharedPreferences("sharedpreference", Context.MODE_PRIVATE)

        val editor: SharedPreferences.Editor =sharedPreferences!!.edit()
        editor.putBoolean("IS_OPEN", true)
        editor.apply()
        var next : Button = findViewById(R.id.next)

        next.setOnClickListener {

           if(viewPager2.currentItem ==3)
              startActivity(Intent(this,SignupScreens::class.java))
           else
                viewPager2.setCurrentItem(viewPager2.currentItem+1)

        }
        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            //Some implementation
        }.attach()
    }

    private fun setUpViewPager() {
        viewPager2 = findViewById(R.id.viewpager);
        indicatore = findViewById(R.id.indicator);
        tabLayout = findViewById(R.id.into_tab_layout);

        // Object of ViewPager2Adapter
        // this will passes the
        // context to the constructor
        // of ViewPager2Adapter
        var viewPager2Adapter = ViewPagerAdapter2(this);
        indicatore.setViewPager(viewPager2)

        // adding the adapter to viewPager2
        // to show the views in recyclerviewj
        viewPager2.setAdapter(viewPager2Adapter);

        // To get swipe event of viewpager2

        viewPager2.registerOnPageChangeCallback(object : OnPageChangeCallback() {
            // This method is triggered when there is any scrolling activity for the current page
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            // triggered when you select a new page
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                if(position == 0){
                    setTexts(resources.getString(R.string.page1_title),resources.getString(R.string.page1_subtitle))
                }else if(position == 1){
                    setTexts(resources.getString(R.string.page2_title),resources.getString(R.string.page2_subtitle))
                }else if(position == 2){
                    setTexts(resources.getString(R.string.page3_title),resources.getString(R.string.page3_subtitle))
                }else if(position == 3){
                    setTexts(resources.getString(R.string.page4_title),resources.getString(R.string.page4_subtitle))
                }

            }

            // triggered when there is
            // scroll state will be changed
            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
            }
        })

        setTexts(resources.getString(R.string.page1_title),resources.getString(R.string.page1_subtitle))

    }

    private fun setTexts(string: String, string1: String) {

        titleView.setText(string)
        subtitleView.setText(string1)

    }
}