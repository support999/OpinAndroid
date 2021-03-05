package com.amit.opinverse;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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

public class Login extends AppCompatActivity {
    EditText psw_id, email_id;
    ImageView show_password;
    boolean isVisible = false;

    public void movetosubscribe(View view) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Logging in...");
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        if(validate()) {
            Constants constants = new Constants();
            constants.setEmailAndPassword(email_id.getText().toString(), psw_id.getText().toString());
            Log.d("Name:", email_id.getText().toString()+psw_id.getText().toString());
            StringRequest request = new StringRequest(Request.Method.GET, "http://opinverse.com:8000/api-user-login/?email="+email_id.getText().toString()+"&password="+psw_id.getText().toString(), new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    try {
                        Log.d("Login Response: ", response);
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("results");
                        JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                        User user = new User(jsonObject1.getString("user_id"), jsonObject1.getString("user_name"), jsonObject1.getString("last_name"), jsonObject1.getString("email"),
                                jsonObject1.getString("phone"), jsonObject1.getString("gender"), jsonObject1.getString("password"), jsonObject1.getString("comment"),
                                jsonObject1.getString("status"));
                        CurrentUser.user = user;
                        CurrentUser.saveUser();
                        Toast.makeText(getApplicationContext(), "Welcome "+jsonObject1.getString("user_name"), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    error.printStackTrace();
                }
            }) /*{
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("email", email_id.getText().toString());
                    map.put("password", psw_id.getText().toString());
                    return map;
                }
            }*/;

            RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(request);
        }else{
            progressDialog.dismiss();
        }
    }

    boolean validate(){
        boolean validation = true;
        if(psw_id.getText().toString().equals("") || email_id.getText().toString().equals("")){
            Toast.makeText(this, "Required fields are missing", Toast.LENGTH_SHORT).show();
            validation = false;
        }
        if(email_id.getText().toString().indexOf('@') == -1){
            Toast.makeText(getApplicationContext(), "Please enter a proper email-id", Toast.LENGTH_SHORT).show();
            validation = false;
        }
        return validation;
    }

    public void movetoforget(View view) {

        Intent intent = new Intent(getApplicationContext(), ForgetPassword.class);
        startActivity(intent);
        finish();
    }



    public void backtosignup(View view) {

        Intent intent = new Intent(getApplicationContext(), Signup.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        psw_id = findViewById(R.id.psw_id);
        email_id = findViewById(R.id.email_id);
        show_password = findViewById(R.id.show_password);

        psw_id.setTransformationMethod(new PasswordTransformationMethod());
        show_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isVisible){
                    psw_id.setTransformationMethod(new PasswordTransformationMethod());
                    isVisible = !isVisible;
                }else{
                    psw_id.setTransformationMethod(null);
                    isVisible = !isVisible;
                }
            }
        });
    }
}