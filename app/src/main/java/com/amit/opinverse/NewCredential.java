package com.amit.opinverse;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NewCredential extends AppCompatActivity {

    EditText new_password;
    EditText confirm_password;
    String email_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_credential);

        new_password = findViewById(R.id.new_password_edit);
        confirm_password = findViewById(R.id.confirm_password_edit);
        email_id = getIntent().getStringExtra("email_id");
        email_id = email_id.replace("@", "%40");
        findViewById(R.id.update_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(new_password.getText().toString().equals(confirm_password.getText().toString())) {
                    if (CurrentUser.user == null || CurrentUser.user.user_id.equals("")) {
                        StringRequest request = new StringRequest(Request.Method.GET, "http://opinverse.com:8000/api-user-login/?email=" + email_id, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                String uid = "";
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                                    uid = jsonObject1.getString("user_id");
                                    StringRequest request = new StringRequest(Request.Method.PATCH, "http://opinverse.com:8000/api-user-login/" + uid + "/", new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                Toast.makeText(getApplicationContext(), "Password updated Successfully", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(getApplicationContext(), Login.class);
                                                startActivity(intent);
                                                finish();
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {

                                        }
                                    }) {
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {
                                            Map<String, String> map = new HashMap<>();
                                            map.put("password", new_password.getText().toString());
                                            return map;
                                        }
                                    };

                                    RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(request);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });

                        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(request);
                    }
                    else {
                        StringRequest request = new StringRequest(Request.Method.PATCH, "http://opinverse.com:8000/api-user-login/" + CurrentUser.user.user_id + "/", new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject.getString("user_id").equals(CurrentUser.user.user_id)) {
                                        Toast.makeText(getApplicationContext(), "Password updated Successfully", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> map = new HashMap<>();
                                map.put("password", new_password.getText().toString());
                                return map;
                            }
                        };

                        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(request);
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Passwords Dont Match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}