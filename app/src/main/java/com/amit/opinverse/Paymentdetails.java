package com.amit.opinverse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.ayazalam.paytmsdk.paytm_integration.PaytmConfig;
import com.ayazalam.paytmsdk.paytm_integration.PaytmTransaction;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.zip.Checksum;

public class Paymentdetails extends AppCompatActivity {
    RadioButton paytm_id ,gpay_id;
    TextView amt_payable, discount_amt, amt_payable_taxed;
    EditText phone_number;
    int subsId;
    double payable;
    TextView total_subs_amt;
    Button proceed_payment;
    AppCompatActivity activity;

    public void movetohome(View view) {

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paymentdetails);
        Toast.makeText(getApplicationContext(), CurrentUser.user.user_id+"", Toast.LENGTH_SHORT).show();

        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        paytm_id = findViewById(R.id.paytm_id);
        total_subs_amt = findViewById(R.id.total_subs_amt);
        amt_payable = findViewById(R.id.amt_payable);
        discount_amt = findViewById(R.id.discount_amt);
        proceed_payment = findViewById(R.id.proceed_payment);
        amt_payable_taxed = findViewById(R.id.amt_payable_taxed);



        activity = this;
        Intent intent = getIntent();
        subsId = intent.getIntExtra("id", -1);

        final Constants constants = new Constants();
        constants.setSubscriptionId(subsId+"");
        StringRequest request = new StringRequest(Request.Method.GET, constants.subscription_info, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    JSONObject jsonObject1 = jsonArray.getJSONObject(0);
                    total_subs_amt.setText("₹ "+jsonObject1.getString("package_amount"));
                    discount_amt.setText("₹ "+jsonObject1.getString("discount"));
                    amt_payable.setText("₹ "+jsonObject1.getString("net_amount"));
                    payable = Double.parseDouble(jsonObject1.getString("net_amount"));
                    payable = payable*(1+(18.0/100.0));
                    amt_payable_taxed.setText("₹ "+payable+"");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestHandler.getInstance(this).addToRequestQueue(request);

        /*proceed_payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String s = System.currentTimeMillis()+"";
                final String order = CurrentUser.user.user_id+s;
                if(paytm_id.isChecked()){
                    if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_SMS) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, 101);
                    }else{
                        constants.setOrderId(order, CurrentUser.user.user_id, payable+"");
                        StringRequest request = new StringRequest(Request.Method.GET, constants.generate_checksumhash_url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                String checksum = "";
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    checksum = jsonObject.getString("token");
                                    Log.d("CHECKSUM", "onResponse: " + checksum);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


                                PaytmPGService Service = PaytmPGService.getStagingService();
                                HashMap<String, String> paramMap = new HashMap<String,String>();
                                paramMap.put( "ORDER_ID" , order);
                                paramMap.put( "CUST_ID" , CurrentUser.user.user_id);
                                paramMap.put( "CHANNEL_ID" , "WEB");
                                paramMap.put( "TXN_AMOUNT" , payable+"");
                                paramMap.put( "MID" , PaytmConfig.MERCHANT_ID);
                                paramMap.put( "WEBSITE" , PaytmConfig.WEBSITE);
                                paramMap.put( "INDUSTRY_TYPE_ID" , PaytmConfig.INDUSTRY_TYPE);
                                paramMap.put( "CALLBACK_URL", "https://securegw.paytm.in/theia/paytmCallback?ORDER_ID="+order);
                                paramMap.put( "CHECKSUMHASH" , checksum);
                                Log.d("MAP DATA: ", "onResponse: "+paramMap.toString());
                                PaytmOrder Order = new PaytmOrder(paramMap);

                                Service.initialize(Order, null);
                                Service.startPaymentTransaction(activity, true, true, new PaytmPaymentTransactionCallback() {
                                    @Override
                                    public void onTransactionResponse(Bundle inResponse) {
                                        Toast.makeText(getApplicationContext(), "Payment Transaction response " + inResponse.toString(), Toast.LENGTH_LONG).show();
                                        Log.d("Status", "onTransactionResponse: "+inResponse.toString());
                                        try {
                                            final JSONObject jsonObject = new JSONObject(inResponse.toString());
                                            StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.update_payment_status, new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    try {
                                                        JSONObject jsonObject1 = new JSONObject(response);
                                                        if(jsonObject1.getString("status").equals("success")){
                                                            Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                                                            overridePendingTransition(0, 0);
                                                            startActivity(intent1);
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
                                            }){
                                                @Override
                                                protected Map<String, String> getParams() throws AuthFailureError {
                                                    Map<String, String> map = new HashMap();
                                                    try {
                                                        map.put("bank_txn_id", jsonObject.getString("BANKTXNID"));
                                                        map.put("user_id", CurrentUser.user.user_id);
                                                        map.put("subscription_id", subsId+"");
                                                        map.put("order_id", order);
                                                        map.put("txn_amount", payable+"");
                                                        map.put("txn_type", jsonObject.getString("CURRENCY"));
                                                        map.put("gateway_name", jsonObject.getString("GATEWAYNAME"));
                                                        map.put("resp_code", jsonObject.getString("RESPCODE"));
                                                        map.put("resp_msg", "RESPMSG");
                                                        map.put("bank_name", "");
                                                        map.put("mid", jsonObject.getString("MID"));
                                                        map.put("payment_mode", jsonObject.getString("PAYMENTMODE"));
                                                        map.put("refund_amt", payable+"");
                                                        map.put("txn_date", jsonObject.getString("TXNDATE"));
                                                        map.put("status", jsonObject.getString("STATUS"));
                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }
                                                    return map;
                                                }
                                            };
                                            RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void networkNotAvailable() {

                                    }

                                    @Override
                                    public void clientAuthenticationFailed(String inErrorMessage) {
                                        Toast.makeText(getApplicationContext(), "Authentication failed: Server error" + inErrorMessage.toString(), Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void someUIErrorOccurred(String inErrorMessage) {
                                        Toast.makeText(getApplicationContext(), "Authentication failed: Server error" + inErrorMessage.toString(), Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {
                                        Log.d("Server Error: ", "onErrorLoadingWebPage: " + inErrorMessage);
                                    }

                                    @Override
                                    public void onBackPressedCancelTransaction() {

                                    }

                                    @Override
                                    public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {
                                        Toast.makeText(getApplicationContext(), "Transaction cancelled" , Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        }){
                            /*@Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> map = new HashMap<>();
                                map.put("ORDER_ID", order);
                                map.put("CUSTOMER_ID", CurrentUser.user.user_id);
                                map.put("TXN_AMT", payable+"");
                                return map;
                            }
                        };

                        RequestHandler.getInstance(activity).addToRequestQueue(request);
                    }
                }
            }
        });*/
    }
}