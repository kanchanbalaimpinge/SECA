package com.seca.skincare.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.viewpager2.widget.ViewPager2
import com.seca.skincare.R
import com.seca.skincare.utils.Alert
import com.seca.skincare.utils.Utility
import com.seca.skincare.utils.Validator
import com.hbb20.CountryCodePicker
import me.relex.circleindicator.CircleIndicator3

class LoginScreens : BaseActivity() , View.OnClickListener{
    lateinit var viewPager2 : ViewPager2;
    lateinit var titleView : TextView;
    lateinit var subtitleView : TextView;
    lateinit var indicatore : CircleIndicator3;
    lateinit var editText: EditText
    lateinit var ccp: CountryCodePicker
    lateinit var button: AppCompatButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        titleView = findViewById(R.id.title)
        subtitleView = findViewById(R.id.subtitle)
        editText = findViewById(R.id.etphone)
        ccp = findViewById(R.id.ccp)
        button = findViewById(R.id.signin)

        editText.addTextChangedListener(object :TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {

                if(p0.toString().length>8){
                 button.setBackgroundDrawable(resources.getDrawable(R.drawable.selected_button))
                    button.setTextColor(resources.getColor(R.color.black))
                }else{
                    button.setBackgroundDrawable(resources.getDrawable(R.drawable.ic_rectangle_25))
                    button.setTextColor(resources.getColor(R.color.middle_gray))
                }



            }
        })

    }

     fun setNumber(view: View?) {

       editText.setText(editText.text.toString() + (view as AppCompatTextView ) .text.toString())

    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onClick(view: View?) {

        when(view!!.id){

            R.id.forgot_pwd ->  startActivity(Intent(this,ResetPasswordScreens::class.java))

            R.id._cross -> if(!editText.getText().toString().isEmpty())
                editText.setText(
                    editText.text.toString().subSequence(0, editText.text.toString().length - 1)
                )

            R.id.signin -> if (Utility.isNetworkAvailable(this)) {
                if (Validator.isValidateText(
                        this,
                        editText,
                        findViewById(R.id.editText_title) as AppCompatTextView,
                        resources.getString(R.string.enter_new_phon_no_error),
                        8
                    )

                    )
                    startActivity(Intent(this,PasswordVerificationScreens::class.java).putExtra("PHONE_NO",editText.text.toString())
                        .putExtra("PHONE_CODE",ccp.selectedCountryCodeWithPlus))

                else {

                }
            } else {
                Alert.showToast(this,resources.getString(R.string.check_network_connection))
            }

            R.id.back -> finish()

            R.id.forgot_pwd->  startActivity(Intent(this,ResetPasswordScreens::class.java))

        }
    }


}