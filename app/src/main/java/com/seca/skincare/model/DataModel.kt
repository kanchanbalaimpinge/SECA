package com.seca.skincare.model

import kotlin.collections.ArrayList

data class DataModel(val cart : ArrayList<CartListModel>,val total:String,val sub_total: String,val display_total:String,val display_sub_total: String, val shipping:ShippingModel )
data class AIDataModel(val AI_result : ArrayList<AIResultModel>)
data class DataDescriptionModel(val description : ArrayList<DescriptionModel> )
data class ShippingModel(val shipping_cost:String,val shipping_currency: String,val display_shipping_cost:String)
data class SubscriptionModel(val id:Int,val created_at: String,val updated_at: String,val name: String,val long_description: String,val short_description: String,
                             val price: Double,val discount: Double,val published: Boolean,val duration: String, val duration_display:String

                             )


data class subscriptionModel_CREATE(var subscription :String)
data class CheckoutModel(var transaction_details :TransactionModel,var user_id:String,var gopay : GoPayModel,var shopeepay: ShopeeModel)
data class TransactionModel(var currency :String,var gross_amount :Double, var order_id:String )
data class GoPayModel(var enable_callback :Boolean,var callback_url :String )
data class ShopeeModel(var callback_url :String )
data class OrderModel(var order_id :String )
data class MidtransModel(var token :String , var redirect_url :String)
data class PostCheckoutModel1(var cart_id :String,var user_phone_code:String,var user_phone_no : String,
                             var transaction_id: String , var transaction_status :String,
                             var sub_total : String,var total: String , var shipping :String)


data class PostCheckoutModel(
                             var transaction_id: String , var transaction_status :String,
                             var sub_total : String,var total: String , var shipping :String)

data class PurchaseOrderModel(
    var id: Int , var created_at :String,
    var updated_at : String,var midtrans_order_id: Object , var sub_total :String
    ,var total :String,var status:Object , var transaction_id :String
    ,var shipping:String,var user :Int,var date:String,var display_total: String )



data class DescriptionModel(
    var id: Int , var created_at :String,
    var updated_at : String,var header_1: String , var title :String
    ,var video :Object,var detail_section_1:Object , var header_2 :String
    ,var detail_section_2:String, var header_3: String, var detail_section_3: String)

