package com.seca.skincare.model

import java.io.Serializable

data class ImagesModel(val id :Int, val created_at :String, val updated_at:String, val image_url:String,val user :Int,val skin_health_score:Int
) :Serializable


data class ImageArrayModel(val images :ArrayList<ImagesModel>)