package com.seca.skincare.retrofit.response;

import java.util.ArrayList;

/**
 * @author PA1810.
 */

//receiving data from api data status and error or success message

public class ListResponse<T> {

    private ArrayList<T> results;
    private ArrayList<T> data;
    private int status;
    private int count;
    private String msg;

    public ArrayList<T> getData() {
        return data;
    }

    public void setData(ArrayList<T> data) {
        this.data = data;
    }

    public ArrayList<T> getResult() {
        return results;
    }

    public void setResult(ArrayList<T> result) {
        this.results = result;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int status() {
        return status;
    }

    public String msg() {
        return msg;
    }
}
