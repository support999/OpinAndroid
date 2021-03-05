package com.amit.opinverse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Sliderpage extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mPager;
    private int[] layouts = {R.layout.first_slide, R.layout.second_slide, R.layout.third_slide};
    private MpagerAdapter mpagerAdapter;

    private LinearLayout Dots_Layout;
    private ImageView[] dots;

    private Button BnNext, BnSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (new PreferenceManager(this).checkPreference())
//        {
//            loadHome();
//        }

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        setContentView(R.layout.activity_sliderpage);

        mPager = (ViewPager) findViewById(R.id.viewpager);
        mpagerAdapter = new MpagerAdapter(layouts,this);
        mPager.setAdapter(mpagerAdapter);

        Dots_Layout = (LinearLayout)findViewById(R.id.dotsLayout);
        BnNext = (Button)findViewById(R.id.bnNext);
        BnSkip =(Button)findViewById(R.id.bnSkip);
        BnNext.setOnClickListener(this);
        BnSkip.setOnClickListener(this);
        createDots(0);

        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                createDots(position);
                if(position==layouts.length-1)
                {
                    BnNext.setText("Start");
                    BnSkip.setVisibility(View.INVISIBLE);
                }
                else
                {
                    BnNext.setText("Next");
                    BnSkip.setVisibility(View.VISIBLE);
                }
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

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.bnNext:
                loadNextSlide();
                break;

            case R.id.bnSkip:
                loadHome();
                new PreferenceManager(this).writePreference();
                break;
        }
    }

    private void loadHome()
    {
        startActivity(new Intent(this,Enrolle.class));
        finish();
    }

    private void loadNextSlide()
    {
        int next_slide = mPager.getCurrentItem()+1;

        if (next_slide<layouts.length)
        {
            mPager.setCurrentItem(next_slide);
        }
        else
        {
            loadHome();
            new PreferenceManager(this).writePreference();
        }

    }
}















