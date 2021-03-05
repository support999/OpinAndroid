package com.amit.opinverse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ayazalam.paytmsdk.paytm_integration.PaytmTransaction;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

public class PaymentActivity extends AppCompatActivity implements PaytmPaymentTransactionCallback {
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent = getIntent();
        Double payable = intent.getDoubleExtra("payable", 0);
        String order = intent.getStringExtra("order");
        PaytmTransaction paytmTransaction = new PaytmTransaction(this, this);
        paytmTransaction.initTransacation(order, CurrentUser.user.user_id, payable+"", null, null);
    }

    @Override
    public void onTransactionResponse(Bundle inResponse) {
        Toast.makeText(getApplicationContext(), "Payment Transaction response " + inResponse.toString(), Toast.LENGTH_LONG).show();
        Log.d("Status", "onTransactionResponse: "+inResponse.toString());
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
}