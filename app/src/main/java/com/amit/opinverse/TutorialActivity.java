package com.amit.opinverse;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;

public class TutorialActivity extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;
    View mIndicator;
    TabAdapter adapter;
    int indicatorWidth;
    ImageView backBtn;

    public TutorialActivity() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment__tutorial__assignment);
        tabLayout = findViewById(R.id.tab);
        mIndicator = findViewById(R.id.indicator);
        viewPager = findViewById(R.id.viewPager);
        backBtn = findViewById(R.id.backBtn);

        tabLayout.addTab(tabLayout.newTab().setText("Tutorial"));
        tabLayout.addTab(tabLayout.newTab().setText("Assignment"));

        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new FragmentTutorial(), "Tutorial");
        adapter.addFragment(new FragmentAssignment(), "Assignment");

        viewPager.setOffscreenPageLimit(2);

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                indicatorWidth = tabLayout.getWidth() / tabLayout.getTabCount();

                //Assign new width
                FrameLayout.LayoutParams indicatorParams = (FrameLayout.LayoutParams) mIndicator.getLayoutParams();
                indicatorParams.width = indicatorWidth;
                mIndicator.setLayoutParams(indicatorParams);
            }
        });



        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)mIndicator.getLayoutParams();

                //Multiply positionOffset with indicatorWidth to get translation
                float translationOffset =  (v+i) * indicatorWidth ;
                params.leftMargin = (int) translationOffset;
                mIndicator.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int i) {
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }
}