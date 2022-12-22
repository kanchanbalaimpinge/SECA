package com.seca.skincare.model


data class ConsultantModel(val id : Int, val  phone_no: String,val phone_code : String, val  username: String,val first_name:String
                        , val last_name : String,val profile_image : String,val is_consultant:Boolean ,val account_type: String ,val image_url:String)


data class ProfileModel(val id : Int, val  phone_no: String,val phone_code : String, val  username: String,val first_name:String
                        , val last_name : String,val profile_image : String,val is_consultant:Boolean ,val account_type: String,val address:String,val city :String, val consultant : ConsultantModel ,val image_url : String )

data class ConsultantProfileModel(val id : Int, val  phone_no: String,val phone_code : String, val  username: String,val first_name:String
                        , val last_name : String,val profile_image : String,val is_consultant:Boolean ,val account_type: String,val purchases:Int, val money_spent : Int  ,val image_url:String)

data class AddressModel(val address:String,val  city: String)