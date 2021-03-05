package com.amit.opinverse;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Registration {
    Context context;
    String user_name, last_name, email, phone, gender, password, comment, status;
    Registration(Context context, String user_name, String last_name, String email, String phone, String gender, String password, String comment, String status){
        this.context = context;
        this.user_name = user_name;
        this.last_name = last_name;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.password = password;
        this.comment = comment;
        this.status = status;
    }

    void register(){
        StringRequest request = new StringRequest(Request.Method.POST, Constants.user_register, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("user_name", user_name);
                map.put("last_name", last_name);
                map.put("email", email);
                map.put("phone", phone);
                map.put("gender", gender);
                map.put("password", password);
                map.put("comment", comment);
                map.put("status", status);
                return map;
            }
        };

        RequestHandler.getInstance(context).addToRequestQueue(request);
    }
}
