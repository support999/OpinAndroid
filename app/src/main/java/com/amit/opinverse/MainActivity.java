package com.amit.opinverse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    DrawerLayout mDrawerlayout;
    NavigationView navigationLinearLayout;
    TextView username_side_menu;
    ConstraintLayout ov_coins_layout, ov_cash_layout;

    //button clicked for drawerlayout
    ImageView btn_menu;
    Boolean buttonStateOpen;
    LinearLayout homeButton, notifyButton, contactsButton, profileButton, accountButton, calenderButton, tutorialButton, upgradeButton, faqButton, settingsButton, myclienteButton, walletButton, privacyButton, termsButton, careButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //sidemenu drawer
        mDrawerlayout = findViewById(R.id.drawer_layout);
        navigationLinearLayout = findViewById(R.id.nav_view);
        username_side_menu = findViewById(R.id.username_side_menu);
        username_side_menu.setText(CurrentUser.user.user_name+" "+CurrentUser.user.last_name+"\n"+CurrentUser.user.user_id);

        homeButton = navigationLinearLayout.findViewById(R.id.homeButton);
        notifyButton = navigationLinearLayout.findViewById(R.id.notifyButton);

        contactsButton = navigationLinearLayout.findViewById(R.id.contactsButton);
        profileButton = navigationLinearLayout.findViewById(R.id.profilesButton);

        accountButton = navigationLinearLayout.findViewById(R.id.accountButton);
        calenderButton = navigationLinearLayout.findViewById(R.id.calenderButton);

        tutorialButton = navigationLinearLayout.findViewById(R.id.tutorialButton);
        upgradeButton = navigationLinearLayout.findViewById(R.id.upgradeButton);
        faqButton = navigationLinearLayout.findViewById(R.id.faqButton);

        settingsButton = navigationLinearLayout.findViewById(R.id.settingsButton);
        myclienteButton = navigationLinearLayout.findViewById(R.id.myclienteButton);

        walletButton = navigationLinearLayout.findViewById(R.id.walletButton);


        privacyButton = navigationLinearLayout.findViewById(R.id.privacyButton);
        termsButton = navigationLinearLayout.findViewById(R.id.termsButton);
        careButton = navigationLinearLayout.findViewById(R.id.careButton);

        ov_cash_layout = findViewById(R.id.ov_cash_layout);
        ov_coins_layout = findViewById(R.id.ov_coins_layout);


        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerlayout,R.string.app_name,R.string.app_name);
        mDrawerlayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        //button clicked for drawerlayout
        buttonStateOpen = false;
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        btn_menu = (ImageView) findViewById(R.id.btn_menu);
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
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setItemIconTintList(null);

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
                        //startActivity(new Intent(getApplicationContext(), TeamMenuActivity.class));
                        //overridePendingTransition(0,0);
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


    @Override
    public void onClick(View view) {

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
                overridePendingTransition(0, 0);
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
                String url = "http://opinverse.com/plan_view/?user_id="+CurrentUser.user.user_id;
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
                startActivity(new Intent(getApplicationContext(), GrievanceActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.referLayout:
                startActivity(new Intent(getApplicationContext(), ReferralActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.bottomTeamButton:
                startActivity(new Intent(getApplicationContext(), TeamMenuActivity.class));
                overridePendingTransition(0,0);
                break;
            case R.id.walletButton:
            case R.id.bottomWalletButton:
                startActivity(new Intent(getApplicationContext(), Wallet.class));
                overridePendingTransition(0,0);
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
                finish();
                break;
            case R.id.ov_coins_layout:
                Dialog dialog = new Dialog(this);
                Button okbtn, detailsbtn;
                dialog.setContentView(R.layout.pop_up_layout_ovcoins);
                okbtn = dialog.findViewById(R.id.ok_btn);
                detailsbtn = dialog.findViewById(R.id.details_btn);
                WindowManager.LayoutParams params = new WindowManager.LayoutParams();
                params.width = WindowManager.LayoutParams.WRAP_CONTENT;
                params.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setAttributes(params);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                okbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
                break;
            case R.id.ov_cash_layout:
                Dialog dialog1 = new Dialog(this);
                Button okbtn1, detailsbtn1;
                dialog1.setContentView(R.layout.pop_up_layout_ovcash);
                okbtn = dialog1.findViewById(R.id.ok_btn);
                detailsbtn = dialog1.findViewById(R.id.details_btn);
                WindowManager.LayoutParams params1 = new WindowManager.LayoutParams();
                params1.width = WindowManager.LayoutParams.WRAP_CONTENT;
                params1.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog1.getWindow().setAttributes(params1);
                dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                okbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog1.dismiss();
                    }
                });
                dialog1.show();
                break;
        }
    }


    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        String rTitle[];
        String rDescription[];
        int rImage[];

        MyAdapter (Context c, String title[], String description[], int imgs[]) {
            super(c, R.layout.row, R.id.txtview1, title);
            this.context = c;
            this.rTitle = title;
            this.rDescription = description;
            this.rImage = imgs;

        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);
            ImageView images = row.findViewById(R.id.image1);
            TextView myTitle = row.findViewById(R.id.txtview1);
            TextView myDescription = row.findViewById(R.id.txtview2);

            //now set our resource on view
            images.setImageResource(rImage[position]);
            myTitle.setText(rTitle[position]);
            myDescription.setText(rDescription[position]);

            return row;
        }
    }


}