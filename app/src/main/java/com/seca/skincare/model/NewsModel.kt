package com.seca.skincare.model

import java.io.Serializable

data class NewsModel(val id :Int,val created_at :String,val updated_at:String ,val title:String,
                     val long_description :String,val short_description:String ,val image:String
                     ,val published :Boolean , val image_url:String
) :Serializable
