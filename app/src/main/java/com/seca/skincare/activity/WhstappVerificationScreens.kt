package com.seca.skincare.activity

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
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
import com.seca.skincare.model.OtpModel
import com.seca.skincare.retrofit.APIClient
import com.seca.skincare.retrofit.APIInterface
import com.seca.skincare.retrofit.response.RestResponse
import com.seca.skincare.utils.Alert
import com.seca.skincare.utils.ErrorResponseHandler.*
import com.seca.skincare.utils.Utility
import com.seca.skincare.utils.Validator
import me.relex.circleindicator.CircleIndicator3
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.util.HashMap

class WhstappVerificationScreens : BaseActivity() , View.OnClickListener{

    lateinit var viewPager2 : ViewPager2;
    lateinit var titleView : TextView;
    lateinit var subtitleView : TextView;
    lateinit var indicatore : CircleIndicator3;
    lateinit var editText: EditText
    lateinit var code: TextView
    lateinit var resend_code: TextView
    lateinit var button: AppCompatButton

    var phone_no :String =""
    var phone_code :String =""
     var flag: Int =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_whtsapp_verification)
        titleView = findViewById(R.id.title)
        subtitleView = findViewById(R.id.subtitle)
        editText = findViewById(R.id.etphone)
        code = findViewById(R.id.code_timer)
        resend_code = findViewById(R.id.resend_code)

        flag = intent.getIntExtra("FLAG",0)
        phone_no = intent.getStringExtra("PHONE_NO").toString()
        phone_code =intent.getStringExtra("PHONE_CODE").toString()

        subtitleView.append(" "+phone_code+phone_no)
      setTimer()

        button = findViewById(R.id.next)
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

    private fun setTimer() {
        val timer = object: CountDownTimer(45000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                code.setText(resources.getString(R.string.you_will_code_in)+" "+millisUntilFinished/1000+" " +
                        "secs")
            }

            override fun onFinish() {

                resend_code.visibility = View.VISIBLE
                code.visibility = View.GONE
            }
        }
        timer.start()

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

            R.id.signin -> {
                startActivity(Intent(this, LoginScreens::class.java))
                finish()
            }

            R.id.next -> {
                if (Utility.isNetworkAvailable(this)) {
                    if (Validator.isValidateText(
                            this,
                            editText,
                            findViewById(R.id.editText_title) as AppCompatTextView,
                            resources.getString(R.string.wrong_code),
                            4
                        )
                    ) {
                        var intent= Intent(this@WhstappVerificationScreens, CreateAccountScreen::class.java)
                        intent .putExtra("PHONE_NO",phone_no)
                        intent.putExtra("PHONE_CODE",phone_code)
                        intent.putExtra("TOKEN",response.body()?.data?.token)

                        startActivity(intent)
                    }
                } else {
                    Alert.showToast(this, resources.getString(R.string.check_network_connection))
                }


            }

            R.id.resend_code-> {
             //   getOtp()
            }

            R.id.back -> finish()

        }
    }




}