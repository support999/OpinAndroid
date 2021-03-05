package com.amit.opinverse;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.hbb20.CountryCodePicker;

public class ForgetPassword extends AppCompatActivity {
    String country_code, mobile_number, email_id;
    EditText mobile_input, email_input;
    CountryCodePicker countryCodePicker;
    public void movetoverify(View view) {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        countryCodePicker = findViewById(R.id.country_code_picker);
        mobile_input = findViewById(R.id.mobile_input);
        Button nxt_btn = findViewById(R.id.next_btn);
        email_input = findViewById(R.id.email_input);

        nxt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()) {
                    mobile_number = mobile_input.getText().toString();
                    country_code = countryCodePicker.getSelectedCountryCode();
                    email_id = email_input.getText().toString();
                    Intent intent = new Intent(getApplicationContext(), Verify.class);
                    intent.putExtra("country_code", country_code);
                    intent.putExtra("mobile_no", mobile_number);
                    intent.putExtra("email_id", email_id);
                    overridePendingTransition(0, 0);
                    startActivity(intent);
                }
            }
        });
    }

    boolean validate(){
        boolean validate  = true;
        if(mobile_input.getText().toString().length() != 10){
            Toast.makeText(getApplicationContext(), "Please enter a valid 10 digit phone number", Toast.LENGTH_SHORT).show();
            validate = false;
        }
        if(email_input.getText().toString().indexOf('@') == -1){
            Toast.makeText(getApplicationContext(), "Please enter a proper email-id", Toast.LENGTH_SHORT).show();
            validate = false;
        }
        return validate;
    }
}