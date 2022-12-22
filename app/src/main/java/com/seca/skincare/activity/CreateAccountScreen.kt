package com.seca.skincare.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.seca.skincare.R
import com.seca.skincare.model.AuthResponse
import com.seca.skincare.model.LoginDataModel
import com.seca.skincare.model.RegisterModel
import com.seca.skincare.retrofit.APIClient
import com.seca.skincare.retrofit.APIInterface
import com.seca.skincare.retrofit.response.RestResponse
import com.seca.skincare.utils.*
import me.relex.circleindicator.CircleIndicator3
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.util.HashMap

class  CreateAccountScreen : BaseActivity() , View.OnClickListener,TextWatcher{
    private lateinit var token: String
    private lateinit var phone_code: String
    private lateinit var phone_no: String
    lateinit var viewPager2 : ViewPager2;
    lateinit var titleView : TextView;
    lateinit var subtitleView : TextView;
    lateinit var indicatore : CircleIndicator3;
    lateinit var editText: EditText
    lateinit var etPassword: EditText
    lateinit var etCPassword: EditText
    lateinit var button: AppCompatButton

    var isVisible1 : Boolean = false
    var isVisible2 : Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_user_registration)

        titleView = findViewById(R.id.title)
        subtitleView = findViewById(R.id.subtitle)
        editText = findViewById(R.id.etusername)
        etPassword = findViewById(R.id.etPassword)
        etCPassword = findViewById(R.id.etConfirmPassword)
        phone_no = intent.getStringExtra("PHONE_NO").toString()
        phone_code =intent.getStringExtra("PHONE_CODE").toString()
        token =intent.getStringExtra("TOKEN").toString()
        showORhidePass(findViewById(R.id.imgShowPassword1),findViewById(R.id.etPassword),false)
        showORhidePass(findViewById(R.id.imgShowPassword2),findViewById(R.id.etConfirmPassword),false)

        button = findViewById(R.id.signup)


        editText.addTextChangedListener(this)
        etCPassword.addTextChangedListener(this)
        etPassword.addTextChangedListener(this)

    }

     fun setNumber(view: View?) {

       editText.setText(editText.text.toString() + (view as AppCompatTextView ) .text.toString())

    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onClick(view: View?) {

        when(view!!.id){
            R.id.imgShowPassword1 -> if(isVisible1){ showORhidePass(findViewById(R.id.imgShowPassword1),findViewById(R.id.etPassword),true)
                isVisible1 = false}
            else {
                showORhidePass(findViewById(R.id.imgShowPassword1), findViewById(R.id.etPassword), false)
                isVisible1 = true
            }

            R.id.imgShowPassword2 -> if(isVisible2){ showORhidePass(findViewById(R.id.imgShowPassword2),findViewById(R.id.etConfirmPassword),true)
                isVisible2 = false}
            else {
                showORhidePass(findViewById(R.id.imgShowPassword2), findViewById(R.id.etConfirmPassword), false)
                isVisible2 = true
            }


            R.id.signin -> startActivity(Intent(this,LoginScreens::class.java))

            R.id.signup ->  if (Utility.isNetworkAvailable(this)) {
                if (Validator.isValidateText(
                        this,
                        editText,
                        findViewById(R.id.username_title) as AppCompatTextView,
                        resources.getString(R.string.username_short),
                        2
                    )

                    && Validator.isValidateText(
                        this,
                        etPassword,
                        findViewById(R.id.password_title) as AppCompatTextView,
                        resources.getString(R.string.password_short),
                        4)

                    && Validator.isValidateText(
                        this,
                        etCPassword,
                        findViewById(R.id.cPassword_title) as AppCompatTextView,
                        resources.getString(R.string.password_short),
                        4)

                    && Validator.isValidatepassword(
                        this,
                        etCPassword,etPassword,
                        findViewById(R.id.cPassword_title) as AppCompatTextView,
                        resources.getString(R.string.password_not_match),
                        4
                    )

                ) {
                    registerAccount()
                }

                else {

                }
            } else {
                Alert.showToast(this,resources.getString(R.string.check_network_connection))
            }
            R.id.back -> finish()

        }

    }

    private fun registerAccount() {
        try {
            val utility: Utility = Utility.getInstance(this)

            utility.showLoading(getString(R.string.please_wait))
            val apiInterface: APIInterface =
                APIClient.getClient(this).create(APIInterface::class.java)
            //"b2dkds-9d8d9f23e6152164a392325145213b0b"
            val callApi: Call<RestResponse<AuthResponse>> = apiInterface.registerUser(RegisterModel(phone_code,phone_no,token,editText.text.toString(),etPassword.text.toString()))
            callApi.enqueue(object : Callback<RestResponse<AuthResponse>> {
                override fun onResponse(
                    call: Call<RestResponse<AuthResponse>>,
                    response: Response<RestResponse<AuthResponse>>
                ) {
                    utility.hideLoading()
                    if (response.code() == 200) {

                        Toast.makeText(this@CreateAccountScreen,response.body()!!.message, Toast.LENGTH_SHORT).show()
                        login()
//                        startActivity(Intent(this@CreateAccountScreen, LoginScreens::class.java))
//                        finish()
                    } else if (response.code() == 400) {
                        val message: String = ErrorResponseHandler.parseError(response)
                        Toast.makeText(this@CreateAccountScreen, message, Toast.LENGTH_SHORT).show()
                        utility.hideLoading()
                    } else {
                        Toast.makeText(this@CreateAccountScreen,response.message(), Toast.LENGTH_SHORT).show()
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
    private fun login() {
        try {
            val utility: Utility = Utility.getInstance(this)
            val hashMap = HashMap<String, Any>()
            hashMap["phone_code"] = "+91"
            hashMap["phone_no"] = editText.text.toString()
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
            val callApi: Call<RestResponse<AuthResponse>> = apiInterface.login(LoginDataModel(phone_code,phone_no,etPassword.text.toString()))
            callApi.enqueue(object : Callback<RestResponse<AuthResponse>> {
                override fun onResponse(
                    call: Call<RestResponse<AuthResponse>>,
                    response: Response<RestResponse<AuthResponse>>
                ) {
                    utility.hideLoading()
                    if (response.code() == 200) {

                        SharedPreference.savePreferenceData(this@CreateAccountScreen,"TOKEN",response.body()?.data?.token)
                        SharedPreference.savePreferenceData(this@CreateAccountScreen,"PHONE_CODE",phone_code)
                        SharedPreference.savePreferenceData(this@CreateAccountScreen,"PHONE_NO",phone_no)
                        SharedPreference.savePreferenceData(this@CreateAccountScreen,"USERNAME",response.body()?.data?.username)
                        SharedPreference.savePreferenceData(this@CreateAccountScreen,"USER_ID",response.body()?.data!!.id)
                        SharedPreference.savePreferenceData(this@CreateAccountScreen,"IS_CONSULTANT",response.body()?.data?.is_consultant)

                        startActivity(Intent(this@CreateAccountScreen, HomeScreens::class.java))
                        finishAffinity()

                    } else if (response.code() == 400) {
                        val message: String = ErrorResponseHandler.parseError(response)
                        Toast.makeText(this@CreateAccountScreen, message, Toast.LENGTH_SHORT).show()
                        utility.hideLoading()
                    } else {
                        Toast.makeText(this@CreateAccountScreen,response.message(), Toast.LENGTH_SHORT).show()
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

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun afterTextChanged(p0: Editable?) {
        if(editText.text.toString().length>1 && etPassword.text.toString().length>3 && etCPassword.text.toString().length>3
            && etPassword.text.toString().equals(etCPassword.text.toString())){
            button.setBackgroundDrawable(resources.getDrawable(R.drawable.selected_button))
            button.setTextColor(resources.getColor(R.color.black))
        }else{
            button.setBackgroundDrawable(resources.getDrawable(R.drawable.ic_rectangle_25))
            button.setTextColor(resources.getColor(R.color.middle_gray))
        }    }
}