package com.amit.opinverse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Subspages extends AppCompatActivity  {

    private ViewPager nPager;
    private int[] layouts = {R.layout.first_subscrib};
    private MpagerAdapter npagerAdapter;

    private LinearLayout Dots_Layout;
    private ImageView[] dots;
    int subs_id = 0;

    public void movetopayment(View view) {
        if(subs_id == 0){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }else {
            Intent intent = new Intent(getApplicationContext(), Paymentdetails.class);
            intent.putExtra("id", subs_id);
            startActivity(intent);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (new PreferenceManager(this).checkPreference())
//        {
//            loadHomes();
//        }

        setContentView(R.layout.activity_subscriptionpages);

        nPager = (ViewPager) findViewById(R.id.subs_viewpager);
        npagerAdapter = new MpagerAdapter(layouts,this);
        nPager.setAdapter(npagerAdapter);

        Dots_Layout = (LinearLayout)findViewById(R.id.dotsLayouts);

        createDots(0);

        nPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0) {
                    subs_id = 0;
                }
            }

            @Override
            public void onPageSelected(int position) {
                createDots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void createDots(int current_position)
    {
        if (Dots_Layout != null)
            Dots_Layout.removeAllViews();

        dots = new ImageView[layouts.length];

        for (int i = 0; i<layouts.length; i++)
        {
            dots[i] = new ImageView(this);
            if (i==current_position)
            {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.dots_selected));
            }
            else
            {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this,R.drawable.dots_default));
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(4,0,4,0);

            Dots_Layout.addView(dots[i],params);

        }

    }

//    private void loadHomes() {
//        startActivity(new Intent(this,Subspages.class));
//        finish();
//    }


}