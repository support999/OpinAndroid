package com.amit.opinverse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.request.SimpleMultiPartRequest;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Signup extends AppCompatActivity {
    TextView ln_id, fn_id, email_id, mobileno_id, psw_id, conpsw_id, referral_id;
    CheckBox checkBox;
    Button age_id;
    Spinner gender_id;
    String[] genders = new String[]{"Male", "Female"};
    String gender = "Male";
    ImageView show_password, show_password2;
    boolean isVisiblePassword = false;
    boolean isVisibleConPass = false;
    CircleImageView upload_image;
    String picturePath;

    public void movetosignin(View view) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Creating Account...");
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        if(validate()) {
            Date date = Calendar.getInstance().getTime();
            String time = new SimpleDateFormat("YYYY-DD-MM hh:mm:ss").format(date);
            time  = time.replace(" ", "T");
            String finalTime = time;
            Log.d("time: ", finalTime);
            StringRequest request = new StringRequest(Request.Method.POST, "http://opinverse.com:8000/api-user-login/", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    try {
                        Log.d("RESPONSE", "onResponse: " + response);
                        final JSONObject jsonObject = new JSONObject(response);

                        SimpleMultiPartRequest simpleMultiPartRequest = new SimpleMultiPartRequest(Request.Method.POST, "http://opinverse.com:8000/upload_profile_photo/", new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("msg","####"+response);

                                Intent intent = new Intent(getApplicationContext(), TempActivity.class);
                                try {
                                    intent.putExtra("user_id", jsonObject.getString("user_id"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                startActivity(intent);
                                finish();
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("msg","####"+error);
                                Toast.makeText(getApplicationContext(), "There was some error while uploading your profile picture...", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(), TempActivity.class);
                                try {
                                    intent.putExtra("user_id", jsonObject.getString("user_id"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                startActivity(intent);
                                finish();
                            }
                        });
                        simpleMultiPartRequest.addStringParam("user_id", CurrentUser.user.user_id);
                        simpleMultiPartRequest.addFile("image", picturePath);

                        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(simpleMultiPartRequest);
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
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("user_id", "111");
                    map.put("sponsor_id", referral_id.getText().toString());
                    map.put("user_name", fn_id.getText().toString());
                    map.put("last_name", ln_id.getText().toString());
                    map.put("email", email_id.getText().toString());
                    map.put("dob", age_id.getText().toString());
                    //map.put("dob", age_id.getText().toString());
                    map.put("phone", mobileno_id.getText().toString());
                    map.put("gender", gender);
                    map.put("password", psw_id.getText().toString());
                    map.put("profile_img", "");
                    map.put("create_at", finalTime);
                    map.put("comment", "No");
                    map.put("status", "No");


                    return map;
                    /*user_id:1
                    sponsor_id:14
                    user_name:amit
                    last_name:saxena
                    email:amit@gmail.com
                    dob:2020-10-01T11:14:00
                    phone:7723871767
                    gender:Male
                    password:admin@123
                    //create_at:"2020-10-07T11:14:00"
                    comment:No
                    status:No
                    //referral_code:""
                    profile_img:none*/
                }
            };

            RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(request);
        }else {
            progressDialog.dismiss();
        }
    }
    public void backtoenroll(View view) {

        Intent intent = new Intent(getApplicationContext(), Enrolle.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_signup);


        ln_id = findViewById(R.id.ln_id);
        fn_id = findViewById(R.id.fn_id);
        age_id = findViewById(R.id.age_id);
        gender_id = findViewById(R.id.gender_id);
        email_id = findViewById(R.id.email_id);
        mobileno_id = findViewById(R.id.mobilno_id);
        psw_id = findViewById(R.id.psw_id);
        conpsw_id = findViewById(R.id.conpsw_id);
        checkBox = findViewById(R.id.checkBox);
        referral_id = findViewById(R.id.referral_id);
        upload_image = findViewById(R.id.upload_image);

        show_password = findViewById(R.id.show_password);
        show_password2 = findViewById(R.id.show_password2);

        show_password2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isVisibleConPass) {
                    conpsw_id.setTransformationMethod(new PasswordTransformationMethod());
                }else{
                    conpsw_id.setTransformationMethod(null);
                }

                isVisibleConPass = !isVisibleConPass;
            }
        });

        show_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isVisiblePassword) {
                    psw_id.setTransformationMethod(new PasswordTransformationMethod());
                }else{
                    psw_id.setTransformationMethod(null);
                }

                isVisiblePassword = !isVisiblePassword;
            }
        });

        upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK,  android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1001);
            }
        });

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Getting things ready...");
        progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        FirebaseDynamicLinks.getInstance().getDynamicLink(getIntent()).addOnSuccessListener(new OnSuccessListener<PendingDynamicLinkData>() {
            @Override
            public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                progressDialog.dismiss();
                Uri deepLink = null;
                if (pendingDynamicLinkData != null) {
                    deepLink = pendingDynamicLinkData.getLink();
                }
                if(deepLink != null
                        && deepLink.getBooleanQueryParameter("user_id", false)){
                    String referral_id_received = deepLink.getQueryParameter("user_id");
                    referral_id.setText(referral_id_received);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
            }
        });

        age_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.calendar_layout);
                Button confirmDateBtn = dialog.findViewById(R.id.confirmDateBtn);
                final DatePicker datePicker = dialog.findViewById(R.id.calendar);
                confirmDateBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int i = datePicker.getYear();
                        int i1 = datePicker.getMonth();
                        int i2 = datePicker.getDayOfMonth();
                        i1+=1;
                        if(i1<10) {
                            if(i2<10) {
                                age_id.setText(i+ "-0" + i1+"-0"+i2);
                            }else{
                                age_id.setText(i+ "-0" + i1+"-"+i2);
                            }
                        }else{
                            if(i2<10) {
                                age_id.setText(i + "-" + i1+"-"+"0"+i2);
                            }else{
                                age_id.setText(i + "-" + i1+"-"+i2);
                            }
                        }
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, genders);
        gender_id.setAdapter(adapter);
        gender_id.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                gender = genders[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        transparentStatusAndNavigation();
    }

    boolean validate(){
        boolean validation = true;
        if(!psw_id.getText().toString().equals(conpsw_id.getText().toString())){
            validation = false;
            Toast.makeText(getApplicationContext(), "Passwords dont match", Toast.LENGTH_SHORT).show();
        }
        if(fn_id.getText().toString().equals("") || ln_id.getText().toString().equals("") || age_id.getText().toString().equals("") || email_id.getText().toString().equals("")
        || mobileno_id.getText().toString().equals("") || psw_id.getText().toString().equals("") || conpsw_id.getText().toString().equals("")){
            validation = false;
            Toast.makeText(getApplicationContext(), "Required Fields Are Empty", Toast.LENGTH_SHORT).show();
        }
        if(!checkBox.isChecked()){
            validation = false;
            Toast.makeText(getApplicationContext(), "Please accept the terms and conditions", Toast.LENGTH_SHORT).show();
        }
        if(referral_id.getText().toString().equals("")){
            validation = false;
            Toast.makeText(getApplicationContext(), "Cannot register without a referral id", Toast.LENGTH_SHORT).show();
            referral_id.requestFocus();
            referral_id.setFocusableInTouchMode(true);
        }
        if(mobileno_id.getText().toString().length() != 10){
            Toast.makeText(getApplicationContext(), "Please enter a valid 10 digit phone number", Toast.LENGTH_SHORT).show();
            validation = false;
        }
        if(email_id.getText().toString().indexOf('@') == -1){
            Toast.makeText(getApplicationContext(), "Please enter a proper email-id", Toast.LENGTH_SHORT).show();
            validation = false;
        }
        return validation;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1001 & resultCode == RESULT_OK){
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            cursor.close();
            upload_image.setImageURI(selectedImage);
        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void transparentStatusAndNavigation() {
        //make full transparent statusBar
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            );
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
        }
    }

    private void setWindowFlag(final int bits, boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}