package com.amit.opinverse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

public class VideoActivity extends AppCompatActivity {
    ImageView playVideo, video_image;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    static CardView video_view;
    static TextView textView;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_view);

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        video_image = findViewById(R.id.video_image);

        video_view = findViewById(R.id.video_view);

        TextView textView = findViewById(R.id.textView);

        String url = CurrentModule.moduleModel.getVideo_url();
        textView.setText(CurrentModule.moduleModel.getDescription());

        Glide.with(this).load(url).into(video_image);
        textView = findViewById(R.id.textView);

        playVideo = findViewById(R.id.playVideo);
        playVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFragment(new FragmentVideoView());
            }
        });
    }

    public void addFragment(Fragment fragmentAdd) {
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.bottom_up, R.anim.bottom_down, R.anim.bottom_up, R.anim.bottom_down);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.add(R.id.frame, fragmentAdd).commit();
    }
}