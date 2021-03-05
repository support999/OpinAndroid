package com.amit.opinverse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TeamMenuActivity extends AppCompatActivity {
    AppCompatActivity activity;
    ImageView btn_menu;
    Boolean buttonStateOpen;
    private DrawerLayout mDrawerlayout;
    private NavigationView mNavigationView;
    TextView username_side_menu;
    TextView team_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_menu);
        activity = this;
        team_num = findViewById(R.id.team_num);

        username_side_menu = findViewById(R.id.username_side_menu);
        username_side_menu.setText("Username: "+CurrentUser.user.user_name+" "+CurrentUser.user.last_name+"\nUser-ID: "+CurrentUser.user.user_id);

        findViewById(R.id.level_wise).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), TeamActivity.class));
            }
        });

        StringRequest request = new StringRequest(Request.Method.GET, "http://opinverse.com:8000/api-user-team-level/?user_id="+CurrentUser.user.user_id+"&level=1", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    team_num.setText("Total Team Number = "+jsonObject.getInt("count")+"");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(request);

        findViewById(R.id.total_direct).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), TeamTableActivity.class));
            }
        });

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
        bottomNavigationView.setSelectedItemId(R.id.pramotion);

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
                        startActivity(new Intent(getApplicationContext(), TeamMenuActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.wallet:
                        startActivity(new Intent(getApplicationContext(), Wallet.class));
                        overridePendingTransition(0,0);
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
        switch (view.getId()) {
            case R.id.homeButton:
            case R.id.bottomHomeButton:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                overridePendingTransition(0, 0);
                break;
            case R.id.notifyButton:
                break;
            case R.id.contactsButton:
                startActivity(new Intent(getApplicationContext(), ContactsActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.profilesButton:
                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                overridePendingTransition(0, 0);
                break;
            case R.id.accountButton:
                startActivity(new Intent(getApplicationContext(), AccountActivity.class));
                overridePendingTransition(0, 0);
                break;
            case R.id.calenderButton:
                startActivity(new Intent(getApplicationContext(), CalendarActivity.class));
                overridePendingTransition(0, 0);
                break;
            case R.id.tutorialButton:
                startActivity(new Intent(getApplicationContext(), TutorialActivity.class));
                overridePendingTransition(0, 0);
                break;
            case R.id.upgradeButton:
                String url = "http://opinverse.com/plan_view/?user_id=" + CurrentUser.user.user_id;
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                int colorInt = Color.parseColor("#4A154B"); //red
                builder.setToolbarColor(colorInt);
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(this, Uri.parse(url));
                break;
            case R.id.faqButton:
                startActivity(new Intent(getApplicationContext(), FaqActivity.class));
                overridePendingTransition(0, 0);
                break;

            case R.id.myclienteButton:
                startActivity(new Intent(getApplicationContext(), ClientActivity.class));
                overridePendingTransition(0, 0);
                break;
            case R.id.privacyButton:
                String url1 = "http://opinverse.com/privacy_policy";
                CustomTabsIntent.Builder builder1 = new CustomTabsIntent.Builder();
                int colorInt1 = Color.parseColor("#4A154B"); //red
                builder1.setToolbarColor(colorInt1);
                CustomTabsIntent customTabsIntent1 = builder1.build();
                customTabsIntent1.launchUrl(this, Uri.parse(url1));
                break;
            case R.id.termsButton:
                break;
            case R.id.careButton:
                break;


            case R.id.bottomTeamButton:
                break;
            case R.id.walletButton:
            case R.id.bottomWalletButton:
                startActivity(new Intent(getApplicationContext(), Wallet.class));
                overridePendingTransition(0, 0);
                break;
            case R.id.settingsButton:
            case R.id.bottomSettingsButton:
                startActivity(new Intent(getApplicationContext(), Setting.class));
                overridePendingTransition(0, 0);
                break;
            case R.id.bottomPromotionsButton:
                startActivity(new Intent(getApplicationContext(), Pramotion.class));
                overridePendingTransition(0, 0);
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