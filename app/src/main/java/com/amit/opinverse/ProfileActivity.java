package com.amit.opinverse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    EditText first_name, last_name, gender,dob, current_address, permanent_address, bank_name, ifsc_code, acc_num, paytm_num, phonepe_num, gpay_num, enter_tag;
    ProgressDialog progressDialog;
    ImageView edit;
    ImageView add_tag;
    RecyclerView tag_recycler;
    TagAdapter tagAdapter;
    boolean enabled = false;
    AppCompatActivity appCompatActivity;
    List<String> languages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_profile);
        TagsListClass.setContext(this);
        init();

        ImageView backBtn = findViewById(R.id.backBtn);
        languages = new ArrayList<>();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(enabled){
                    disable();
                    edit.setImageResource(R.drawable.ic_baseline_edit_24);
                }else{
                    enable();
                    edit.setImageResource(R.drawable.ic_baseline_save_24);
                }
                enabled = !enabled;
            }
        });
    }

    void init(){
        appCompatActivity = this;
        first_name = findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);
        gender = findViewById(R.id.gender);
        dob = findViewById(R.id.dob);
        current_address = findViewById(R.id.current_address);
        permanent_address = findViewById(R.id.permamnent_address);
        edit = findViewById(R.id.edit);

        bank_name = findViewById(R.id.bank_name);
        ifsc_code = findViewById(R.id.ifsc_code);
        acc_num = findViewById(R.id.acc_num);
        paytm_num = findViewById(R.id.paytm_num);
        phonepe_num = findViewById(R.id.phonepe_num);
        gpay_num = findViewById(R.id.gpay_num);

        enter_tag = findViewById(R.id.enter_tag);
        add_tag = findViewById(R.id.add_tag);
        tag_recycler = findViewById(R.id.tags_recycler);
        tag_recycler.setLayoutManager(new GridLayoutManager(this, 4));
        tagAdapter = new TagAdapter(this, TagsListClass.getTagList(), 1);
        tag_recycler.setAdapter(tagAdapter);
        add_tag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(appCompatActivity);
                dialog.setContentView(R.layout.layout_skills);
                WindowManager.LayoutParams params = new WindowManager.LayoutParams();
                params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                params.width = WindowManager.LayoutParams.MATCH_PARENT;
                dialog.getWindow().setAttributes(params);
                ProgressDialog progressDialog = new ProgressDialog(appCompatActivity);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Loading...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();

                AutoCompleteTextView enter_tag = dialog.findViewById(R.id.enter_tag);
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_list_item_1, languages);
                enter_tag.setThreshold(1);
                enter_tag.setAdapter(adapter);

                List<String> selectedSkillss = new ArrayList<>();
                StringRequest request = new StringRequest(Request.Method.POST, "http://opinverse.com:8000/api-master-skill-and-intrest-data/", new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray results = new JSONArray(jsonObject.getJSONArray("results"));
                            for (int i = 0; i<results.length(); i++){
                                JSONObject skill = results.getJSONObject(i);
                                languages.add(skill.getString("name"));
                            }
                            adapter.notifyDataSetChanged();
                            progressDialog.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        progressDialog.dismiss();
                    }
                });

                RequestHandler.getInstance(appCompatActivity).addToRequestQueue(request);


                ImageView add_tag = dialog.findViewById(R.id.add_tag);
                Button skills_save = dialog.findViewById(R.id.skills_save);
                Button skills_cancel = dialog.findViewById(R.id.skills_cancel);
                RecyclerView selected_skills_recycler = dialog.findViewById(R.id.selected_tags_recycler);

                TagAdapter tagAdapter = new TagAdapter(getApplicationContext(), selectedSkillss, 0);
                selected_skills_recycler.setAdapter(tagAdapter);
                selected_skills_recycler.setLayoutManager(new GridLayoutManager(getApplicationContext(), 3));

                add_tag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        selectedSkillss.add(enter_tag.getText().toString());
                        tagAdapter.notifyDataSetChanged();
                    }
                });

                skills_save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TagsListClass.appendList(selectedSkillss);
                        dialog.dismiss();
                    }
                });

                skills_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
        disable();
        loadData();
    }

    void loadData(){
        StringRequest request = new StringRequest(Request.Method.GET, "http://opinverse.com:8000/api-user-profile-data/?user_id=" + CurrentUser.user.user_id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray results = jsonObject.getJSONArray("results");
                    JSONObject result = results.getJSONObject(0);
                    JSONArray user_address = result.getJSONArray("user_address");
                    JSONArray bank_infos = result.getJSONArray("user_bank");
                    if(user_address.length() > 0) {
                        JSONObject address = user_address.getJSONObject(0);
                        JSONObject bank = bank_infos.getJSONObject(0);
                        first_name.setText(result.getString("user_name"));
                        last_name.setText(result.getString("last_name"));
                        gender.setText(result.getString("gender"));
                        dob.setText(result.getString("dob"));
                        current_address.setText(address.getString("current_address"));
                        permanent_address.setText(address.getString("permanent_address"));
                        bank_name.setText(bank.getString("bank_name"));
                        ifsc_code.setText(bank.getString("ifsc_code"));
                        acc_num.setText(bank.getString("account_number"));
                        paytm_num.setText(bank.getString("paytm_number"));
                        gpay_num.setText(bank.getString("google_pay_account"));
                        phonepe_num.setText(bank.getString("paytm_number"));
                    }else{
                        first_name.setText(result.getString("user_name"));
                        last_name.setText(result.getString("last_name"));
                        gender.setText(result.getString("gender"));
                        dob.setText(result.getString("dob"));
                        current_address.setText("null");
                        permanent_address.setText("null");
                        bank_name.setText("null");
                        ifsc_code.setText("null");
                        acc_num.setText("null");
                        paytm_num.setText("null");
                        gpay_num.setText("null");
                        phonepe_num.setText("null");
                    }

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
        });

        RequestHandler.getInstance(this).addToRequestQueue(request);
    }

    void enable(){
        first_name.setEnabled(true);
        last_name.setEnabled(true);
        gender.setEnabled(true);
        dob.setEnabled(true);
        current_address.setEnabled(true);
        permanent_address.setEnabled(true);
        bank_name.setEnabled(true);
        ifsc_code.setEnabled(true);
        acc_num.setEnabled(true);
        paytm_num.setEnabled(true);
        phonepe_num.setEnabled(true);
        gpay_num.setEnabled(true);
    }

    void disable(){
        first_name.setEnabled(false);
        last_name.setEnabled(false);
        gender.setEnabled(false);
        dob.setEnabled(false);
        current_address.setEnabled(false);
        permanent_address.setEnabled(false);
        bank_name.setEnabled(false);
        ifsc_code.setEnabled(false);
        acc_num.setEnabled(false);
        paytm_num.setEnabled(false);
        phonepe_num.setEnabled(false);
        gpay_num.setEnabled(false);
    }
}