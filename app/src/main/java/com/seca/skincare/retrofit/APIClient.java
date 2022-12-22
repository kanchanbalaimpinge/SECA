package com.seca.skincare.retrofit;

import android.content.Context;
import android.text.TextUtils;

import com.seca.skincare.utils.SharedPreference;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author PA1810.
 */
public class APIClient {

    private static Retrofit retrofit = null;
//    private static Retrofit retrofit = null;
//    private static Retrofit retrofit = null;
///eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzUxMiIsImV4cCI6MTY0OTIyMzM2MiwiYXVkIjoiIiwic3ViIjoia2FuY2hhbjEyMzQiLCJpc3MiOiJTa2luQ2FyZSIsImp3dElkIjoiMTg0MWZiNDBhYzc1NDFhYzk2MTE3M2Q0ZWJjODdiOTAiLCJpYXQiOjE2NDg2MTg1NjJ9.eyJpZCI6MTAsInVzZXJuYW1lIjoia2FuY2hhbjEyMzQiLCJleHAiOjE2NDkyMjMzNjJ9.S-kPDS-opDLvkndh6TiFxwO_4Rn3p0B9VDeNInejRlBXiZPOn0ScKDZxzz2p6Z0Y-iXi-aAG3m4vksVF7zkrUv7LJzngLLIIGQqeO5vCilMddhfpVkDSgU6MttBcS1I_fQLp-2HZe4DTUNFyv6US65oOnoX-A_RRPXtRGFixUeM

    public static final String Auth_URL = "http://163.47.212.61:8085/";
    public static final String  INVENTERY_URL = "http://163.47.212.61:8085/inventory/";
    public static final String Cart_URL = "http://163.47.212.61:8085/order/";

    public static final String MEDIA_URL = "";
    public static Retrofit getClient(Context context) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        httpClient.addNetworkInterceptor(new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {

                Request.Builder requestBuilder = chain.request().newBuilder();
//              SharedPreference.fetchPrefenceData(context, PreferenceData.TOKEN);
///eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzUxMiIsImV4cCI6MTY0ODcwMTEwOSwiYXVkIjoiIiwic3ViIjoia2FuY2hhbjEiLCJpc3MiOiJTa2luQ2FyZSIsImp3dElkIjoiZmQ2MjI1NDM1M2Y3NDc5YjliZWNhMzBmOTJjMGYwMTUiLCJpYXQiOjE2NDgwOTYzMDl9.eyJpZCI6NywidXNlcm5hbWUiOiJrYW5jaGFuMSIsImV4cCI6MTY0ODcwMTEwOX0.aLHtbT_ct4TEokmqVaID4AQ3_-rRBQsHwa7pw67cS4lp0cqyGOYMVNeTtooXCnM9-2wo93zCoDi4PIp7hQmpdgYgApl1NPRHWAXT9xlHLFQ2sq9UeRMVST971hPeIFW-FgHXpbZuEVuKAtbzep9eSYWXg054km_dLvDTNUttg7c
                if (!TextUtils.isEmpty(SharedPreference.fetchPrefenceData(context, "TOKEN"))) {
                    requestBuilder.header("Authorization", "Bearer " + SharedPreference.fetchPrefenceData(context, "TOKEN"));
            // requestBuilder.header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiMDVmYmQ3ZGZkNGFiMThjNDE5ZWY1N2E2MjJkNGE4ZGU2MmEyYWNlMWI0ZTYwMGNmODhlODZiOTgzYTc1NzhiN2M4YTMyN2UxNDhkMmFhZmYiLCJpYXQiOjE2MzM2OTc4MTQsIm5iZiI6MTYzMzY5NzgxNCwiZXhwIjoxNjY1MjMzODE0LCJzdWIiOiIyMSIsInNjb3BlcyI6W119.GW2t0apamom8a6pEgIWee-fCQunL0KFS8XPDlyyFINpSQlkeeT3pygdIqqVAJtQIV1_RsGhUf6wjv6Z17dm1Fa-QMWh1Z7WjGEPMQ2LiBaoQwg7BumvtYi9byHINWzQFuUza5-QTAyjrvAOEUNvb4zuUijQ7vIDeHHmjkhzUMKXmPjMfK3ss3WKkUwcIw-GKQbtY--0gthO7y6vZwgHMNMaKXUjwD5vrbmy_-5JAYJ_d5ZtyDIIZwXQ9IluDTW8_-jo8HcOHyH0jX40OXDPFm0PjMdm3VnEO1yBSzxkD_W3DtC9js6K3kS8kXZ8fJKDlt0NE1sA-udywmPejiwaVyDArirlnLRC5IGWJQxg5WoXcFB_QSgsWJYgKo0F-nJxBa5YL-u9iyqeuGTGm4fiLOg1YmBSe_vtCoE_n3XSp0dr4KoYAmUhSnBjIksZvmbuIUQTAv4iG4GIYhNvGF8HOLHS0CRv3XNw_ABjmTdKQ91yaY7A2OWasKAG8GqWt0KvDmJjhCh0Rt3_i7HOcHq3_0fqpW9-RehowIwSV9HdBlgmInQkBlMWSuoSamQHG_4z_wcK6iV1Zet-BmfCM6jXBHQgpXE9RJ0TVE2_n_uLUpQ9pZUQ5qr2v68LXTXBzmbkNTSVUpwKkzl9JZJd20by71t_7iTG-Yoxw8VxnAwG4jjU");
                }
//                Log.e("TOKEN", "" + SharedPreference.fetchPrefenceData(context, PreferenceData.TOKEN));
                return chain.proceed(requestBuilder.build());
            }
        });

        httpClient.connectTimeout(2, TimeUnit.MINUTES);
        httpClient.readTimeout(2, TimeUnit.MINUTES);
        httpClient.writeTimeout(2, TimeUnit.MINUTES);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(/*BuildConfig.BASE_URL*/Auth_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient.build())
                    .build();
        }

        return retrofit;
    }
    public static Retrofit getClient1(Context context) {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);

        httpClient.addNetworkInterceptor(new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {

                Request.Builder requestBuilder = chain.request().newBuilder();
//                SharedPreference.fetchPrefenceData(context, PreferenceData.TOKEN);
//
//                if (!TextUtils.isEmpty(SharedPreference.fetchPrefenceData(context, PreferenceData.TOKEN))) {
//                    requestBuilder.header("Authorization", "Bearer " + SharedPreference.fetchPrefenceData(context, PreferenceData.TOKEN));
//            // requestBuilder.header("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiMDVmYmQ3ZGZkNGFiMThjNDE5ZWY1N2E2MjJkNGE4ZGU2MmEyYWNlMWI0ZTYwMGNmODhlODZiOTgzYTc1NzhiN2M4YTMyN2UxNDhkMmFhZmYiLCJpYXQiOjE2MzM2OTc4MTQsIm5iZiI6MTYzMzY5NzgxNCwiZXhwIjoxNjY1MjMzODE0LCJzdWIiOiIyMSIsInNjb3BlcyI6W119.GW2t0apamom8a6pEgIWee-fCQunL0KFS8XPDlyyFINpSQlkeeT3pygdIqqVAJtQIV1_RsGhUf6wjv6Z17dm1Fa-QMWh1Z7WjGEPMQ2LiBaoQwg7BumvtYi9byHINWzQFuUza5-QTAyjrvAOEUNvb4zuUijQ7vIDeHHmjkhzUMKXmPjMfK3ss3WKkUwcIw-GKQbtY--0gthO7y6vZwgHMNMaKXUjwD5vrbmy_-5JAYJ_d5ZtyDIIZwXQ9IluDTW8_-jo8HcOHyH0jX40OXDPFm0PjMdm3VnEO1yBSzxkD_W3DtC9js6K3kS8kXZ8fJKDlt0NE1sA-udywmPejiwaVyDArirlnLRC5IGWJQxg5WoXcFB_QSgsWJYgKo0F-nJxBa5YL-u9iyqeuGTGm4fiLOg1YmBSe_vtCoE_n3XSp0dr4KoYAmUhSnBjIksZvmbuIUQTAv4iG4GIYhNvGF8HOLHS0CRv3XNw_ABjmTdKQ91yaY7A2OWasKAG8GqWt0KvDmJjhCh0Rt3_i7HOcHq3_0fqpW9-RehowIwSV9HdBlgmInQkBlMWSuoSamQHG_4z_wcK6iV1Zet-BmfCM6jXBHQgpXE9RJ0TVE2_n_uLUpQ9pZUQ5qr2v68LXTXBzmbkNTSVUpwKkzl9JZJd20by71t_7iTG-Yoxw8VxnAwG4jjU");
//                }
//                Log.e("TOKEN", "" + SharedPreference.fetchPrefenceData(context, PreferenceData.TOKEN));
                return chain.proceed(requestBuilder.build());
            }
        });

        httpClient.connectTimeout(2, TimeUnit.MINUTES);
        httpClient.readTimeout(2, TimeUnit.MINUTES);
        httpClient.writeTimeout(2, TimeUnit.MINUTES);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(/*BuildConfig.BASE_URL*/INVENTERY_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(httpClient.build())
                    .build();
       }

        return retrofit;
    }
}
