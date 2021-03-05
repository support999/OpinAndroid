package com.amit.opinverse;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class Pramotion extends AppCompatActivity {

    private DrawerLayout mDrawerlayout;
    private NavigationView mNavigationView;

    //button clicked for drawerlayout
    Button btn_menu;
    Boolean buttonStateOpen;
    TextView username_side_menu;

    ListView listView;
    String mTitle[] = {"Pinterest", "YouTube", "Instagram", "Facebook", "Surveys", "SEO", "Twitter", "Lead Generation", "Websites", "Product Testing", "Reviews", "New Mobile App", "Offers & Coupons"};
    String mDescription[] = {"Built to the Better Ad Standers", "Showing under your Contents", "Showing under your Contents", "Showing Full or Modal Boxes", "Invite Visitors to add to their HomePage", "Great for FAQ's and Compacting Content", "Interact with Users and Show Messages", "Detect and Auto Activate Dark Mode", "Designed with for Multiple Purposes", "Badge or Single Button to go Back Up!", "Powerwed by charts.js for Power and Features", "Small Elements to Show Small Information", "Consent or Show Messages with Cookies"};
    int images[] = {R.drawable.pinterest, R.drawable.youtube, R.drawable.instagram, R.drawable.facebook, R.drawable.survey, R.drawable.seo, R.drawable.twitter, R.drawable.generation, R.drawable.web, R.drawable.new_product, R.drawable.feedback, R.drawable.newmobileapp, R.drawable.coupon};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pramotion);

        //sidemenu drawer
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.app_name,R.string.app_name);
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

        username_side_menu = findViewById(R.id.username_side_menu);
        username_side_menu.setText("Username: "+CurrentUser.user.user_name+" "+CurrentUser.user.last_name+"\nUser-ID: "+CurrentUser.user.user_id);
        //button clicked for drawerlayout
        buttonStateOpen = false;
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        btn_menu = (Button) findViewById(R.id.btn_menu);
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
//                        startActivity(new Intent(getApplicationContext(), Pramotion.class));
//                        overridePendingTransition(0,0);
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

        //list of duties
        listView = findViewById(R.id.dutieslistview);
        //now create an adapter class

        Pramotion.MyAdapter_prom adapter = new Pramotion.MyAdapter_prom(this, mTitle, mDescription, images);
        listView.setAdapter(adapter);

        //now set item click on list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Toast.makeText(Pramotion.this, "Give Your Opinion", Toast.LENGTH_SHORT).show();
                }
                else if (position == 1) {
                    Intent intent = new Intent(getApplicationContext(), SocialCategory.class);
                    intent.putExtra("category", 1);
                    startActivity(intent);
                    overridePendingTransition(0,0);
                }
                else if (position == 3) {
                    Intent intent = new Intent(getApplicationContext(), SocialCategory.class);
                    intent.putExtra("category", 2);
                    startActivity(intent);
                }

            }
        });
    }

    class MyAdapter_prom extends ArrayAdapter<String> {

        Context context;
        String rTitle[];
        String rDescription[];
        int rImage[];

        MyAdapter_prom (Context c, String title[], String description[], int imgs[]) {
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
                String url = "http://opinverse.com/plan_view/?user_id="+CurrentUser.user.user_id;
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                int colorInt = Color.parseColor("#4A154B"); //red
                builder.setToolbarColor(colorInt);
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(this, Uri.parse(url));
                break;
            case R.id.faqButton:
                startActivity(new Intent(this, FaqActivity.class));
                overridePendingTransition(0, 0);
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
                startActivity(new Intent(getApplicationContext(), Wallet.class));
                overridePendingTransition(0,0);
                break;
            case R.id.settingsButton:
            case R.id.bottomSettingsButton:
                startActivity(new Intent(getApplicationContext(), Setting.class));
                overridePendingTransition(0,0);
                break;
            case R.id.bottomPromotionsButton:
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