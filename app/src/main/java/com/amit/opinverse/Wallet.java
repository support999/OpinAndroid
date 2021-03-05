package com.amit.opinverse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

public class Wallet extends AppCompatActivity {
    TextView ov_cash, ovr_cash, team_ov_cash, team_ovr_cash, achiever_cash, rewards_prizes;

    private DrawerLayout mDrawerlayout;
    private NavigationView mNavigationView;

    //button clicked for drawerlayout
    ImageView btn_menu;
    Boolean buttonStateOpen;
    TextView username_side_menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        //sidemenu drawer

        /*ov_cash = findViewById(R.id.ov_cash);
        ovr_cash = findViewById(R.id.ovr_cash);
        team_ov_cash = findViewById(R.id.team_ov_cash);
        team_ovr_cash = findViewById(R.id.team_ovr_cash);
        rewards_prizes = findViewById(R.id.rewards_prizes);
        achiever_cash = findViewById(R.id.achiever_cash);*/

        username_side_menu = findViewById(R.id.username_side_menu);
        username_side_menu.setText("Username: "+CurrentUser.user.user_name+" "+CurrentUser.user.last_name+"\nUser-ID: "+CurrentUser.user.user_id);

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        //progressDialog.setIndeterminate(true);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.GET, "http://opinverse.com:8000/get_user_account_data_cal/?user_id="+CurrentUser.user.user_id , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject wallet_obj = jsonObject.getJSONObject("user_wallet_data");
                    ov_cash.setText(wallet_obj.getInt("ov_coins")+"");
                    ovr_cash.setText(wallet_obj.getInt("ovr_cash")+"");
                    team_ov_cash.setText(wallet_obj.getInt("team_ov_cash")+"");
                    team_ovr_cash.setText(wallet_obj.getInt("team_ovr_cash")+"");
                    achiever_cash.setText(wallet_obj.get("achievers_cashback_fund")+"");
                    rewards_prizes.setText(wallet_obj.get("prizes_and_awards")+"");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.dismiss();
            }
        });

        //RequestHandler.getInstance(this).addToRequestQueue(request);

        /*findViewById(R.id.payment_hist_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PaymentHistoryActivity.class));
                overridePendingTransition(0, 0);
            }
        });*/

        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.app_name,R.string.app_name);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        //button clicked for drawerlayout
        buttonStateOpen = false;
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        btn_menu = findViewById(R.id.btn_menu);
        btn_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                if (buttonStateOpen==false)
                {
                    drawer.openDrawer(Gravity.RIGHT);
                    buttonStateOpen=true;
                }
                else if (buttonStateOpen==true)
                {
                    drawer.closeDrawer(Gravity.RIGHT);
                    buttonStateOpen=false;
                }
            }
        });


        //Initialize And Assign Variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_Navigation);

        //Set Home Selected
        bottomNavigationView.setSelectedItemId(R.id.wallet);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.pramotion:
                        startActivity(new Intent(getApplicationContext(), Pramotion.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.team:
                        startActivity(new Intent(getApplicationContext(), TeamActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.wallet:
//                        startActivity(new Intent(getApplicationContext(), Wallet.class));
//                        overridePendingTransition(0,0);
                        return true;
                    case R.id.setting:
                        startActivity(new Intent(getApplicationContext(), Setting.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    public void executeClick(View view) {
        switch (view.getId()){
            case R.id.homeButton:
            case R.id.bottomHomeButton:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.notifyButton:
                break;
            case R.id.contactsButton:
                startActivity(new Intent(getApplicationContext(), ContactsActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.profilesButton:
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.accountButton:
                startActivity(new Intent(getApplicationContext(), AccountActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.calenderButton:
                startActivity(new Intent(getApplicationContext(), CalendarActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.tutorialButton:
                startActivity(new Intent(getApplicationContext(), TutorialActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.upgradeButton:
                break;
            case R.id.faqButton:
                break;

            case R.id.myclienteButton:
                startActivity(new Intent(getApplicationContext(), ClientActivity.class));
                overridePendingTransition(0, 0);
                break;
            case R.id.privacyButton:
                break;
            case R.id.termsButton:
                break;
            case R.id.careButton:
                break;


            case R.id.bottomTeamButton:
                startActivity(new Intent(getApplicationContext(), TeamMenuActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.walletButton:
            case R.id.bottomWalletButton:
                break;
            case R.id.settingsButton:
            case R.id.bottomSettingsButton:
                startActivity(new Intent(getApplicationContext(), Setting.class));
                overridePendingTransition(0,0);
                break;
            case R.id.bottomPromotionsButton:
                startActivity(new Intent(getApplicationContext(), Pramotion.class));
                overridePendingTransition(0,0);
                break;
            case R.id.logoutBtn:
                CurrentUser.user.user_id = "";
                CurrentUser.saveUser();
                startActivity(new Intent(getApplicationContext(), Enrolle.class));
                overridePendingTransition(0, 0);
                break;
        }
    }
}