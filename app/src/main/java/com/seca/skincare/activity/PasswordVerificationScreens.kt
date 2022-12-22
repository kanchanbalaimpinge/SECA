package com.seca.skincare.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.viewpager2.widget.ViewPager2
import com.seca.skincare.R
import me.relex.circleindicator.CircleIndicator3
import android.text.InputType

import androidx.core.content.ContextCompat

import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton

import androidx.appcompat.widget.AppCompatEditText

import androidx.appcompat.widget.AppCompatImageView
import com.seca.skincare.model.AuthResponse
import com.seca.skincare.model.LoginDataModel
import com.seca.skincare.retrofit.APIClient
import com.seca.skincare.retrofit.APIInterface
import com.seca.skincare.retrofit.response.RestResponse
import com.seca.skincare.utils.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.util.HashMap

class PasswordVerificationScreens : BaseActivity() , View.OnClickListener{

    lateinit var viewPager2 : ViewPager2;
    lateinit var titleView : TextView;
    lateinit var subtitleView : TextView;
    lateinit var indicatore : CircleIndicator3;
    lateinit var editText: EditText
    lateinit var button: AppCompatButton
    var isVisible1 : Boolean = false
    var phone_no : String =""
    var phone_code : String =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pswd_verification)

        titleView = findViewById(R.id.title)
        subtitleView = findViewById(R.id.subtitle)
        editText = findViewById(R.id.etPassword)
        phone_no = intent.getStringExtra("PHONE_NO").toString()
        phone_code = intent.getStringExtra("PHONE_CODE").toString()

        showORhidePass(findViewById(R.id.imgShowPassword1),findViewById(R.id.etPassword),false)
        button = findViewById(R.id.submit)
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {

                if(p0.toString().length>3){
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

    override fun onClick(view: View?) {

        when(view!!.id){

            R.id._cross -> if(!editText.getText().toString().isEmpty())
                editText.setText(
                    editText.text.toString().subSequence(0, editText.text.toString().length - 1)
                )

            R.id.submit ->  if (Utility.isNetworkAvailable(this)) {
                if (Validator.isValidateText(
                        this,
                        editText,
                        findViewById(R.id.password_title) as AppCompatTextView,
                        resources.getString(R.string.password_short),
                        4
                    )

                ) {
                    { startActivity(Intent(this@PasswordVerificationScreens, HomeScreens::class.java))
                        finishAffinity() }
                }

                else {

                }
            } else {
                Alert.showToast(this,resources.getString(R.string.check_network_connection))
            }

         R.id.imgShowPassword1 -> if(isVisible1){ showORhidePass(findViewById(R.id.imgShowPassword1),findViewById(R.id.etPassword),true)
             isVisible1 = false}
         else {
             showORhidePass(findViewById(R.id.imgShowPassword1), findViewById(R.id.etPassword), false)
             isVisible1 = true
         }

         R.id.forgot_pwd->  startActivity(Intent(this,ResetPasswordScreens::class.java))


        }

    }

    fun showORhidePass(imageView: AppCompatImageView, editText: AppCompatEditText, save: Boolean) {
        if (TextUtils.isEmpty(editText.text)) {
            return
        }
        if (save) {
            editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            editText.setSelection(editText.length())
            imageView.setColorFilter(
                ContextCompat.getColor(
                   this,
                    android.R.color.darker_gray))
        } else {
            editText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            editText.setSelection(editText.length())
            imageView.setColorFilter(ContextCompat.getColor(this, R.color.dark_cyan))
        }
    }



}