package com.seca.skincare.activity

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.seca.skincare.R
import com.seca.skincare.fragment.*
import com.seca.skincare.utils.SharedPreference
import com.google.android.material.bottomnavigation.BottomNavigationView
import me.relex.circleindicator.CircleIndicator3

class HomeScreens : BaseActivity() , View.OnClickListener{
    lateinit var viewPager2 : ViewPager2;
    lateinit var titleView : TextView;
    lateinit var subtitleView : TextView;
    lateinit var indicatore : CircleIndicator3;
    lateinit var editText: EditText
     var isConsultant: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        isConsultant =  SharedPreference.fetchPrefenceBoolData(this, "IS_CONSULTANT")
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setNavigationBottomBar()


    }

    override fun onClick(p0: View?) {
    }

    fun replaceFragment(fragment: Fragment,title : String, bundle: Bundle): Boolean  {

         supportFragmentManager.beginTransaction().replace(R.id.container,fragment).commit()
         return true
    }
    fun replaceFragment(fragment: Fragment,title : String, bundle: Bundle,isAddToBackStack :Boolean): Boolean  {

        supportFragmentManager.beginTransaction().replace(R.id.container,fragment).addToBackStack(title).commit()
        return true
    }
    private fun setNavigationBottomBar() {
        replaceFragment(HomeFragment(),"item.title as String", Bundle())

        var bottomNavigationView = findViewById(R.id.bottom_navigation) as BottomNavigationView

        bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            var fragment: Fragment = Fragment()
            when (item.itemId) {
                R.id.home -> {fragment = HomeFragment()}

                R.id.ai_scan ->{fragment = AIScanFragment()}

//                R.id.become->   if(isConsultant) {
//                    fragment = ConsultantFragment()
//                }else{
//                    fragment = BecomeConsultantFragment()
//                }

              //  R.id.shop ->{fragment = ShopFragment()}

                R.id.news -> {
                    fragment = NewsFragment()
                }


            }

            replaceFragment(fragment, item.title as String, Bundle())

        })

        bottomNavigationView.getMenu().findItem(R.id.shop).isChecked = true

        if(isConsultant) {

            bottomNavigationView.getMenu().findItem(R.id.become)
                .setTitle(resources.getString(R.string.consultant))

        }else{
            bottomNavigationView.getMenu().findItem(R.id.become)
                .setTitle(resources.getString(R.string.become))
        }

    }


}