package com.seca.skincare

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.TextUtils
import com.seca.skincare.activity.BaseActivity
import com.seca.skincare.activity.HomeScreens
import com.seca.skincare.activity.SignupScreens
import com.seca.skincare.utils.SharedPreference

class MainActivity : BaseActivity() {
    var is_open = false;
   lateinit var sharedPreferences : SharedPreferences;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         sharedPreferences = getSharedPreferences("sharedpreference", Context.MODE_PRIVATE)

       is_open = sharedPreferences.getBoolean("IS_OPEN",false);

        //                SharedPreference.fetchPrefenceData(context, PreferenceData.TOKEN);
//
        if (!TextUtils.isEmpty(SharedPreference.fetchPrefenceData(this, "TOKEN"))) {

            Handler(Looper.getMainLooper()).postDelayed({


                startActivity(Intent(this@MainActivity, HomeScreens::class.java))
                overridePendingTransition(
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right
                )
                finish()
            }, 2000)
        } else
            Handler(Looper.getMainLooper()).postDelayed({

                if(is_open) {

                    startActivity(Intent(this@MainActivity, SignupScreens::class.java))
                }else{
                    startActivity(Intent(this@MainActivity, LaunchScreens::class.java))

                }
                overridePendingTransition(
                    android.R.anim.slide_in_left,
                    android.R.anim.slide_out_right
                )
                finish()
            }, 2000)
    }

}