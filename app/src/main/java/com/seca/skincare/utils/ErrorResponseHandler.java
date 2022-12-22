package com.seca.skincare.utils;

import org.json.JSONObject;

import retrofit2.Response;

public class ErrorResponseHandler {

    public static String parseError(Response<?> response){
        String errorMsg = null;
        try {
            JSONObject jObjError = new JSONObject(response.errorBody().string());
            JSONObject jsonArray = jObjError.getJSONObject("message");

            errorMsg = jsonArray.getString("error");

          //  errorMsg = jObjError.getJSONObject("date").getString("");
            return errorMsg ;
        } catch (Exception e) {
        }
        return errorMsg;
    }
}
