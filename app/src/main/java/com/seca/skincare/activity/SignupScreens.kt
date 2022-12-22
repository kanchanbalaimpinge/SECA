package com.seca.skincare.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.viewpager2.widget.ViewPager2
import com.seca.skincare.R
import com.seca.skincare.model.AuthResponse
import com.seca.skincare.model.LoginModel
import com.seca.skincare.retrofit.APIClient
import com.seca.skincare.retrofit.APIInterface
import com.seca.skincare.retrofit.response.RestResponse
import com.seca.skincare.utils.*
import com.hbb20.CountryCodePicker
import me.relex.circleindicator.CircleIndicator3
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.util.HashMap

class SignupScreens : BaseActivity() , View.OnClickListener{
    lateinit var viewPager2 : ViewPager2;
    lateinit var titleView : TextView;
    lateinit var subtitleView : TextView;
    lateinit var indicatore : CircleIndicator3;
    lateinit var editText: EditText
    lateinit var ccp: CountryCodePicker
    lateinit var button: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        titleView = findViewById(R.id.title)
        subtitleView = findViewById(R.id.subtitle)
        editText = findViewById(R.id.etphone)
        ccp = findViewById(R.id.ccp)
        button = findViewById(R.id.signup)
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {

                if(p0.toString().length>9){
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

        when(view!!.id) {

            R.id._cross -> if(!editText.getText().toString().isEmpty())
                editText.setText(
                editText.text.toString().subSequence(0, editText.text.toString().length - 1)
            )

            R.id.signin -> startActivity(Intent(this, LoginScreens::class.java))

            R.id.signup ->
                if (Utility.isNetworkAvailable(this)) {
                    if (Validator.isValidateText(
                            this,
                            editText,
                            findViewById(R.id.editText_title) as AppCompatTextView,
                            resources.getString(R.string.enter_new_phon_no_error),
                            8
                        )

                    )
                        callOtpVerify()

                } else {
                    Alert.showToast(this, resources.getString(R.string.check_network_connection))
                }
            }

        }

    private fun callOtpVerify() {
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
            val callApi: Call<RestResponse<AuthResponse>> = apiInterface.getOTP(LoginModel(ccp.selectedCountryCodeWithPlus,editText.text.toString()))
            callApi.enqueue(object : Callback<RestResponse<AuthResponse>> {
                override fun onResponse(
                    call: Call<RestResponse<AuthResponse>>,
                    response: Response<RestResponse<AuthResponse>>
                ) {
                    utility.hideLoading()
                    if (response.code() == 200) {

                        Toast.makeText(this@SignupScreens,response.body()!!.message,Toast.LENGTH_SHORT).show()
                       // store(this@SignupScreens).updateUserName("phone_no",editText.text.toString())

                        var intent= Intent(this@SignupScreens, WhstappVerificationScreens::class.java)
                               intent.putExtra("FLAG", 0)
                               intent .putExtra("PHONE_NO",editText.text.toString())
                               intent.putExtra("PHONE_CODE",ccp.selectedCountryCodeWithPlus)
                        startActivity(intent)

                    } else if (response.code() == 400) {
                        val message: String = ErrorResponseHandler.parseError(response)
                        Toast.makeText(this@SignupScreens, message, Toast.LENGTH_SHORT).show()
                        utility.hideLoading()
                    } else {
                        Toast.makeText(this@SignupScreens,response.message(),Toast.LENGTH_SHORT).show()
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

}