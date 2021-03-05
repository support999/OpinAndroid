package com.amit.opinverse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PaymentHistoryActivity extends AppCompatActivity {
    RecyclerView payment_history_recycler;
    List<PaymentModel> paymentModels;
    PaymentHistoryAdapter paymentHistoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);
        paymentModels = new ArrayList<>();
        payment_history_recycler = findViewById(R.id.payment_history_recycler);
        paymentHistoryAdapter = new PaymentHistoryAdapter(this, paymentModels);
        payment_history_recycler.setLayoutManager(new LinearLayoutManager(this));
        payment_history_recycler.setAdapter(paymentHistoryAdapter);
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("Loading...");
        progressDialog.setIndeterminate(true);
        progressDialog.show();

        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        StringRequest request = new StringRequest(Request.Method.GET, "http://opinverse.com:8000/api-user-payment-transaction/", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray results = object.getJSONArray("results");
                    for (int i = 0; i<results.length(); i++){
                        JSONObject result = results.getJSONObject(i);
                        PaymentModel paymentModel = new PaymentModel(result.getString("payment_id"), result.getString("payout_amount"), result.getString("payable_amount"), result.getString("payment_type") , result.getString("total_ovr_cash") , result.getString("payment_user"), result.getString("total_team_ovr_cash"), result.getString("total_revenue") , result.getString("admin_charges") , result.getString("tds") , result.getString("txn_date"), result.getString("status"));
                        paymentModels.add(paymentModel);
                    }
                    progressDialog.dismiss();
                    paymentHistoryAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    progressDialog.dismiss();
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

}