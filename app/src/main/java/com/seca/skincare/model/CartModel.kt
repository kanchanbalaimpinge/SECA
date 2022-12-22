package com.seca.skincare.model

data class CartModel(val product : Int, val  quantity: String)
data class CartModel1( val  quantity: String)

data class CartListModel(val id : Int, val  created_at: String,val  updated_at: String
,val  quantity: Int,val  sub_total: String,
                         val  status: Boolean,val  user: Int,val  product:ProductItemModel,val display_price:String)




