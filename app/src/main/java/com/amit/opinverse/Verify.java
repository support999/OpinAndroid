package com.amit.opinverse;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Verify extends AppCompatActivity {
    String mVerificationId, email_id;
    String country_code, mobile_no;

    public void movetochange(View view) {
        Intent intent = new Intent(getApplicationContext(), NewCredential.class);
        startActivity(intent);
    }


    EditText otp_textbox_one, otp_textbox_two, otp_textbox_three, otp_textbox_four, otp_textbox_five, otp_textbox_six;
    TextView verify_otp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);

        Intent intent = getIntent();
        mobile_no = intent.getStringExtra("mobile_no");
        country_code = intent.getStringExtra("country_code");
        email_id = intent.getStringExtra("email_id");

        Toast.makeText(getApplicationContext(), country_code+mobile_no, Toast.LENGTH_SHORT).show();
        sendVerificationCode(mobile_no, country_code);

        otp_textbox_one = findViewById(R.id.otptext1);
        otp_textbox_two = findViewById(R.id.otptext2);
        otp_textbox_three = findViewById(R.id.otptext3);
        otp_textbox_four = findViewById(R.id.otptext4);
        otp_textbox_five = findViewById(R.id.otptext5);
        otp_textbox_six = findViewById(R.id.otptext6);
        verify_otp = findViewById(R.id.verify_code);

        EditText[] edit = {otp_textbox_one, otp_textbox_two, otp_textbox_three, otp_textbox_four};

        otp_textbox_one.addTextChangedListener((TextWatcher) new GenericTextWatcher(otp_textbox_one, edit));
        otp_textbox_two.addTextChangedListener((TextWatcher) new GenericTextWatcher(otp_textbox_two, edit));
        otp_textbox_three.addTextChangedListener((TextWatcher) new GenericTextWatcher(otp_textbox_three, edit));
        otp_textbox_four.addTextChangedListener((TextWatcher) new GenericTextWatcher(otp_textbox_four, edit));
        otp_textbox_five.addTextChangedListener((TextWatcher) new GenericTextWatcher(otp_textbox_five, edit));
        otp_textbox_six.addTextChangedListener((TextWatcher) new GenericTextWatcher(otp_textbox_six, edit));
        verify_otp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyVerificationCode(otp_textbox_one.getText().toString()+otp_textbox_two.getText().toString()+otp_textbox_three.getText().toString()+
                        otp_textbox_four.getText().toString()+otp_textbox_five.getText().toString()+otp_textbox_six.getText().toString());
            }
        });

    }

    private void sendVerificationCode(String mobile, String country) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+"+country + mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }


    //the callback to detect the verification status
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                otp_textbox_one.setText(code.charAt(0)+"");
                otp_textbox_two.setText(code.charAt(1)+"");
                otp_textbox_three.setText(code.charAt(2)+"");
                otp_textbox_four.setText(code.charAt(3)+"");
                otp_textbox_five.setText(code.charAt(4)+"");
                otp_textbox_six.setText(code.charAt(5)+"");
                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            mVerificationId = s;
        }
    };


    private void verifyVerificationCode(String code) {
        //creating the credential
        try {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
            signInWithPhoneAuthCredential(credential);
        }catch (Exception e){
            Toast toast = Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        Toast.makeText(getApplicationContext(), "Phone Number Verified Successfully", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, NewCredential.class);
        intent.putExtra("email_id", email_id);
        overridePendingTransition(0, 0);
        startActivity(intent);
        finish();
    }
}