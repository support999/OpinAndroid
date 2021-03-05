package com.amit.opinverse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Enrolle extends AppCompatActivity {

    public void movetosignup(View view) {

        Intent intent = new Intent(getApplicationContext(), Signup.class);
        startActivity(intent);
        finish();
    }

    public void movetologin(View view) {
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CurrentUser.setContext(getApplicationContext());
        CurrentUser.loadUser();
        if(CurrentUser.user == null || CurrentUser.user.user_id.equals("")){
        }else{
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        setContentView(R.layout.activity_enrolle);
    }
}