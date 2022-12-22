package com.seca.skincare.retrofit;


import com.seca.skincare.model.AIDataModel;
import com.seca.skincare.model.AddressModel;
import com.seca.skincare.model.AuthResponse;
import com.seca.skincare.model.CartModel;
import com.seca.skincare.model.CartModel1;
import com.seca.skincare.model.CheckoutModel;
import com.seca.skincare.model.ConsultantModel;
import com.seca.skincare.model.ConsultantProfileModel;
import com.seca.skincare.model.DataDescriptionModel;
import com.seca.skincare.model.DataModel;
import com.seca.skincare.model.ImageArrayModel;
import com.seca.skincare.model.ImagesModel;
import com.seca.skincare.model.LoginDataModel;
import com.seca.skincare.model.LoginModel;
import com.seca.skincare.model.MidtransModel;
import com.seca.skincare.model.NewsModel;
import com.seca.skincare.model.OrderModel;
import com.seca.skincare.model.OtpModel;
import com.seca.skincare.model.PostCheckoutModel;
import com.seca.skincare.model.ProductItemModel;
import com.seca.skincare.model.ProfileModel;
import com.seca.skincare.model.PurchaseOrderModel;
import com.seca.skincare.model.RegisterModel;
import com.seca.skincare.model.SubscriptionModel;
import com.seca.skincare.model.WebinarModel;
import com.seca.skincare.model.subscriptionModel_CREATE;
import com.seca.skincare.retrofit.response.ListResponse;
import com.seca.skincare.retrofit.response.RestResponse;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author PA1810.
 */
// for api calling stuff

public interface APIInterface {

    @Headers({"Content-Type:application/json"})
    @POST("auth/getOTP/")
    Call<RestResponse<AuthResponse>> getOTP(@Body LoginModel hashMap);


    @Headers({"Content-Type:application/json"})
    @POST("auth/verifyOTP/")
    Call<RestResponse<AuthResponse>> verifiyOtp(@Body OtpModel hashMap);

    @Headers({"Content-Type:application/json"})
    @POST("auth/registerUser/")
    Call<RestResponse<AuthResponse>> registerUser(@Body RegisterModel hashMap);

    @Headers({"Content-Type:application/json"})
    @POST("auth/login/")
    Call<RestResponse<AuthResponse>> login(@Body LoginDataModel hashMap);


    @Headers({"Content-Type:application/json"})
    @POST("auth/forgotPasswordGetOTP/")
    Call<RestResponse<AuthResponse>> forgotPasswordGetOTP(@Body LoginModel hashMap);


    @Headers({"Content-Type:application/json"})
    @POST("auth/forgotPasswordVerifyOTP/")
    Call<RestResponse<AuthResponse>> forgotPasswordVerifyOTP(@Body OtpModel hashMap);

    @Headers({"Content-Type:application/json"})
    @PUT("auth/resetPassword/")
    Call<RestResponse<Integer>> resetPassword(@Body RegisterModel hashMap);

    @GET("auth/getUserProfile/")
    Call<RestResponse<ProfileModel>> getUserProfile();

    @Multipart
    @PUT("auth/updateUserProfile/")
    Call<RestResponse<AuthResponse>> updateUserProfile(@Part("profile_image") RequestBody category_id,
                                                       @Part MultipartBody.Part imageFile);



//    @Headers({"Content-Type:application/json"})


    @Headers({"Content-Type:application/json"})
    @GET("inventory/getAddonList/")
    Call<RestResponse<WebinarModel>> getWebinars();

    @Headers({"Content-Type:application/json"})
    @GET("inventory/getNewsandTips/")
    Call<ListResponse<NewsModel>> getNews();



    @GET("auth/getConsumer/{id}")
    Call<RestResponse<ConsultantProfileModel>> getConsumerUserProfile(@Path("id") int id);

    @GET("order/getOrders/")
    Call<ListResponse<PurchaseOrderModel>> getUserPurchaseHistoryList(@Query("page_size") int page_zie);


    @GET("order/getOrders/{id}")
    Call<ListResponse<PurchaseOrderModel>> getUserPurchaseHistoryList(@Path("id") int id,@Query("page_size") int page_zie);



    @Headers({"Content-Type:application/json"})
    @DELETE("inventory/deleteAIScanImage/{id}/")
    Call<RestResponse<AuthResponse>> removeFromScans(@Path("id") int id);



    @Headers({"Content-Type:application/json"})
    @GET("inventory/getUserLatestSkinScoreCard/{id}/")
    Call<RestResponse<AIDataModel>> getUserLatestSkinScoreCard(@Path("id") int id);


}