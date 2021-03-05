package com.amit.opinverse;

public class User {
    String user_id, user_name, last_name, email, phone, gender, password, comment, status;
    User(String user_id, String user_name, String last_name, String email, String phone, String gender, String password, String comment, String status){
        this.user_id = user_id;
        this.user_name = user_name;
        this.last_name = last_name;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.password = password;
        this.comment = comment;
        this.status = status;
    }
}
