package com.seca.skincare.model



data class LoginModel(val phone_code : String, val  phone_no: String)

data class LoginDataModel(val phone_code : String, val  phone_no: String,val password: String)

data class OtpModel(val phone_code : String, val  phone_no: String,val code:String)

data class RegisterModel(val phone_code : String, val  phone_no: String,val token : String, val  username: String,val password:String)


