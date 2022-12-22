package com.seca.skincare.model

import java.io.Serializable

data class SkinResultsModel(val value :Int, val name :String, val category:String
) :Serializable


data class AIResultModel(val id :Int, val result_image :String, val result_image_url:String, val scores:ArrayList<SkinResultsModel>,
                         val recommended_products :ArrayList<RecommendedProductItemModel>,val updated_at: String,val skin_health_score : Int
                         ,val skin_type:String
) :Serializable
