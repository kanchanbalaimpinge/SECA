package com.seca.skincare.model;

/**
 * @author PA1810.
 */

//receiving data from api data status and error or success message

public class AuthResponse{
//    {"valid":true,"code":200,"message":"OTP Verified Successfully","data":{"phone_code":"+91","phone_no":"8699402880","token":"b2dhod-0dd449b2b9395763727e08d465af54eb"}}
//{"valid":true,"code":200,"message":"OTP Verified Successfully","data":{"phone_code":"+91","phone_no":"8699402880","token":"b2dkku-b13835dd5bd6a32ada80636d3551b386","username":"kanchan1"}}
    String token;
    String phone_no;
    String phone_code;
    String username;
    int id;
    Boolean is_consultant;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Boolean getIs_consultant() {
        return is_consultant;
    }

    public void setIs_consultant(Boolean is_consultant) {
        this.is_consultant = is_consultant;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(String phone_no) {
        this.phone_no = phone_no;
    }

    public String getPhone_code() {
        return phone_code;
    }

    public void setPhone_code(String phone_code) {
        this.phone_code = phone_code;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
