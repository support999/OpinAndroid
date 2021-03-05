package com.ayazalam.paytmsdk.paytm_integration.network;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.ayazalam.paytmsdk.paytm_integration.entities.Order;
import com.ayazalam.paytmsdk.paytm_integration.Constants;
import com.ayazalam.paytmsdk.paytm_integration.PaytmConfig;
import com.ayazalam.paytmsdk.paytm_integration.callback.ChecksumCallback;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;
import static com.ayazalam.paytmsdk.paytm_integration.Constants.CALLBACK_URL;
import static com.ayazalam.paytmsdk.paytm_integration.Constants.CUST_ID;
import static com.ayazalam.paytmsdk.paytm_integration.Constants.INDUSTRY_TYPE_ID;
import static com.ayazalam.paytmsdk.paytm_integration.Constants.MID;
import static com.ayazalam.paytmsdk.paytm_integration.Constants.ORDER_ID;
import static com.ayazalam.paytmsdk.paytm_integration.Constants.TXN_AMOUNT;
import static com.ayazalam.paytmsdk.paytm_integration.Constants.VERIFY_URL;
import static com.ayazalam.paytmsdk.paytm_integration.Constants.WEBSITE;
import static com.ayazalam.paytmsdk.paytm_integration.PaytmConfig.CHECKSUM_GEN_URL;
import static com.ayazalam.paytmsdk.paytm_integration.PaytmConfig.INDUSTRY_TYPE;
import static com.ayazalam.paytmsdk.paytm_integration.PaytmConfig.MERCHANT_ID;

public class CheckSumHelper {

    public static void getChecksum(final Context context, final Order order, final ChecksumCallback callback) {

        RequestQueue queue = com.ayazalam.paytmsdk.paytm_integration.network.SingletonRequestQueue.getInstance(context).getRequestQueue();
        StringRequest postRequest = new StringRequest(Request.Method.POST, CHECKSUM_GEN_URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                            try {
                                JSONObject responseObject = new JSONObject(response);
                                String checkSum =responseObject.has("token_2")?responseObject.getString("token_2"):"";
                                Log.d(TAG, "onResponse: Checksum = "+checkSum);
                                if(!checkSum.equals(""))
                                    callback.onSuccess(checkSum);
                                else callback.onFailure("Request error");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        // response
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                if(order!=null) {
                    params.put(MID, MERCHANT_ID);
                    params.put("order_id", order.getOrderID());
                    params.put(CUST_ID, order.getCustomerID());
                    params.put(Constants.CHANNEL_ID, "WAP");
                    params.put(TXN_AMOUNT, order.getTransactionAmount());
                    params.put(WEBSITE, PaytmConfig.WEBSITE);
                    params.put(CALLBACK_URL, VERIFY_URL);
                    params.put(INDUSTRY_TYPE_ID, INDUSTRY_TYPE);
                }else{
                    if(callback!=null) callback.onFailure("Order is null");
                }
                return params;
            }
        };
        queue.add(postRequest);
    }
}
