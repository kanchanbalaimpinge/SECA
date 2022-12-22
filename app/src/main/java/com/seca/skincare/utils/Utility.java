package com.seca.skincare.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utility {
    private static Utility utility;
    private static Context ctx;
    private ProgressDialog mProgressDialog;

    public static Utility getInstance(Context context){

        if(utility == null){
            utility = new Utility();

        }

        ctx = context;
        return utility;

    }


    public  void showLoading(String message) {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = new ProgressDialog(ctx);
                mProgressDialog.setMessage(message);
                mProgressDialog.setIndeterminate(true);
                mProgressDialog.setCancelable(false);
                mProgressDialog.setCanceledOnTouchOutside(false);

                mProgressDialog.show();
            } else if (mProgressDialog != null && !mProgressDialog.isShowing()) {

                mProgressDialog.show();

            }
        }catch (Exception e){
            e.printStackTrace();
            mProgressDialog= null;

        }

    }

    public  void hideLoading() {
        try {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();

                mProgressDialog.cancel();
                mProgressDialog= null;
            }
        }catch (Exception e){
            e.printStackTrace();
            mProgressDialog.dismiss();
            mProgressDialog.cancel();
            mProgressDialog= null;


        }
    }
    public static boolean isNetworkAvailable(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifi = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (wifi != null && wifi.isConnected()) {
            return true;
        }

        NetworkInfo mobile = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mobile != null && mobile.isConnected()) {
            return true;
        }

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }


        return false;
    }
}
