package com.seca.skincare.retrofit.response;

/**
 * @author PA1810.
 */

//receiving data from api data status and error or success message

public class RestResponse<T> {

    private T data;
    private int code;
    private boolean valid;
    private String message;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
