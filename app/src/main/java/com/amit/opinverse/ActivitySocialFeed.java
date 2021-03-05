package com.amit.opinverse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivitySocialFeed extends AppCompatActivity {
    TextView moduleName;
    Toolbar toolbar;
    Intent intent;
    RecyclerView videoRecycler;
    int category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_video);
        intent = getIntent();

        ImageView backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        toolbar = findViewById(R.id.toolbar);
        moduleName = toolbar.findViewById(R.id.moduleName);
        moduleName.setText(intent.getStringExtra("name"));
        category = intent.getIntExtra("category", -1);

        if(category == 2){
            moduleName.setVisibility(View.GONE);
            toolbar.setBackground(getResources().getDrawable(R.drawable.white_bg));
            backBtn.setColorFilter(ContextCompat.getColor(getApplicationContext(), android.R.color.black));
            toolbar.findViewById(R.id.person).setVisibility(View.VISIBLE);
        }


        videoRecycler = findViewById(R.id.videoRecycler);

        videoRecycler.setAdapter(new VideoAdapter(this, category));
        videoRecycler.setLayoutManager(new LinearLayoutManager(this));

    }
}