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
import com.seca.skincare.model.RegisterModel
import com.seca.skincare.retrofit.APIClient
import com.seca.skincare.retrofit.APIInterface
import com.seca.skincare.retrofit.response.RestResponse
import com.seca.skincare.utils.Alert
import com.seca.skincare.utils.ErrorResponseHandler
import com.seca.skincare.utils.Utility
import com.seca.skincare.utils.Validator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

public class ChangePasswordScreens : BaseActivity() , View.OnClickListener, TextWatcher {
    private lateinit var token: String
    private lateinit var phone_code: String
    private lateinit var phone_no: String
    private lateinit var username: String
    lateinit var viewPager2 : ViewPager2;
    lateinit var titleView : TextView;
    lateinit var subtitleView : TextView;
    lateinit var indicatore : CircleIndicator3;
    lateinit var editText: EditText
    lateinit var editText1: EditText
    var isVisible1 : Boolean = false
    var isVisible2 : Boolean = false
    lateinit var button: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        titleView = findViewById(R.id.title)
        subtitleView = findViewById(R.id.subtitle)
        editText = findViewById(R.id.etPassword)
        editText1 = findViewById(R.id.etcPassword)
        phone_no = intent.getStringExtra("PHONE_NO").toString()
        phone_code =intent.getStringExtra("PHONE_CODE").toString()
        token =intent.getStringExtra("TOKEN").toString()
        username =intent.getStringExtra("USERNAME").toString()
        button = findViewById(R.id.submit)

        showORhidePass(findViewById(R.id.imgShowPassword1),findViewById(R.id.etPassword),false)
       showORhidePass(findViewById(R.id.imgShowPassword2),findViewById(R.id.etcPassword),false)
        editText.addTextChangedListener(this)
        editText1.addTextChangedListener(this)
    }

     fun setNumber(view: View?) {

       editText.setText(editText.text.toString() + (view as AppCompatTextView ) .text.toString())

    }

    override fun onClick(view: View?) {

        when(view!!.id){
            R.id.back -> finish()

            R.id._cross -> if(!editText.getText().toString().isEmpty())
                editText.setText(
                    editText.text.toString().subSequence(0, editText.text.toString().length - 1)
                )


            R.id.submit -> try {
                if (Utility.isNetworkAvailable(this)) {
                    if (Validator.isValidateText(
                            this,
                            editText,
                            findViewById(R.id.password_title) as AppCompatTextView,
                            resources.getString(R.string.password_short),
                            4
                        )

                        && Validator.isValidateText(
                            this,
                            editText1,
                            findViewById(R.id.cPassword_title) as AppCompatTextView,
                            resources.getString(R.string.password_short),
                            4
                        )

                        && Validator.isValidatepassword(
                            this,
                            editText1, editText,
                            findViewById(R.id.cPassword_title) as AppCompatTextView,
                            resources.getString(R.string.password_not_match),
                            4
                        )


                    ) {
                        registerAccount()
                    } else {

                    }
                } else {
                    Alert.showToast(this, resources.getString(R.string.check_network_connection))
                }
            }catch (e:Exception){
                e.printStackTrace()
            }

         R.id.imgShowPassword1 -> if(isVisible1){ showORhidePass(findViewById(R.id.imgShowPassword1),findViewById(R.id.etPassword),true)
             isVisible1 = false}
         else {
             showORhidePass(findViewById(R.id.imgShowPassword1), findViewById(R.id.etPassword), false)
             isVisible1 = true
         }


            R.id.imgShowPassword2 -> if(isVisible2){ showORhidePass(findViewById(R.id.imgShowPassword2),findViewById(R.id.etcPassword),true)
                isVisible2 = false}
            else {
                showORhidePass(findViewById(R.id.imgShowPassword2), findViewById(R.id.etcPassword), false)
                isVisible2 = true
            }

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
    private fun registerAccount() {
        try {
            val utility: Utility = Utility.getInstance(this)

//            Log.e("OTP_DATA", fcmToken)
//            val jsonObject = JSONObject()
//            jsonObject.put("phone_number", phone_number)
//            jsonObject.put("otp_number", otp)
//            val loginDataModel = LoginDataModel()
//            loginDataModel.setOtp(otp)
//            loginDataModel.setPhone_number(phone_number.toString())
//            loginDataModel.setDevice_token(fcmToken)
//            loginDataModel.setDevice_type("android")

            utility.showLoading(getString(R.string.please_wait))
            val apiInterface: APIInterface =
                APIClient.getClient(this).create(APIInterface::class.java)
            val callApi: Call<RestResponse<Int>> = apiInterface.resetPassword(RegisterModel(phone_code,phone_no,token,username,editText.text.toString()))
            callApi.enqueue(object : Callback<RestResponse<Int>> {
                override fun onResponse(
                    call: Call<RestResponse<Int>>,
                    response: Response<RestResponse<Int>>
                ) {
                    utility.hideLoading()
                    if (response.code() == 200) {

                        Toast.makeText(this@ChangePasswordScreens,response.body()!!.message, Toast.LENGTH_SHORT).show()

                        startActivity(Intent(this@ChangePasswordScreens, LoginScreens::class.java))
                        finish()
                    } else if (response.code() == 400) {
                        val message: String = ErrorResponseHandler.parseError(response)
                        Toast.makeText(this@ChangePasswordScreens, message, Toast.LENGTH_SHORT).show()
                        utility.hideLoading()
                    } else {
                        Toast.makeText(this@ChangePasswordScreens,response.message(), Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<RestResponse<Int>>, t: Throwable) {
                    Log.e("exception_msg", t.message!!)
                    utility.hideLoading()
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun afterTextChanged(p0: Editable?) {
        if(editText.text.toString().length>3 && editText1.text.toString().length>3
            && editText.text.toString().equals(editText1.text.toString())){
            button.setBackgroundDrawable(resources.getDrawable(R.drawable.selected_button))
            button.setTextColor(resources.getColor(R.color.black))
        }else{
            button.setBackgroundDrawable(resources.getDrawable(R.drawable.ic_rectangle_25))
            button.setTextColor(resources.getColor(R.color.middle_gray))
        }

    }
}