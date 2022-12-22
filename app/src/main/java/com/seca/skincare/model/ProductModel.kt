package com.seca.skincare.model

import java.io.Serializable

data class ProductModel(val title : String, val  description: String, val image: String)

data class ImageModel(
    val id: Int, val created_at: String?, val updated_at: String?
    , val image: String?, val product: Int, val image_url: String?
) :Serializable

data class MetaModel(
    val id: Int, val created_at: String?, val updated_at: String?
    , val title: String?, val description: String, val product: Int
) :Serializable


data class RecommendedProductItemModel(var id: Int,var product_name :String, var comment:String)
data class ProductItemModel(
    val id: Int, val created_at: String?,val display_price:String, val updated_at: String?, val name: String?
    , val long_description: String?, val short_description: String?, val price: String?
    , val published:Boolean, val stock: Int, val image: ArrayList<ImageModel>, val meta :ArrayList<MetaModel>) : Serializable

