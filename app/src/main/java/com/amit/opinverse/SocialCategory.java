package com.amit.opinverse;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SocialCategory extends AppCompatActivity {
    RecyclerView recyclerView;
    Toolbar toolbar;
    List<YoutubeCategoryModel> youtubeCategoryModels;
    Intent intent;
    TextView moduleName;
    int category;

    public SocialCategory() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_youtube_category);

        intent = getIntent();
        category = intent.getIntExtra("category", -1);

        ImageView backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Bitmap bitmap = null;
        toolbar = findViewById(R.id.toolbar);
        moduleName = toolbar.findViewById(R.id.moduleName);
        if(category == 1){
            moduleName.setText("Youtube Category");
            youtubeCategoryModels = new ArrayList<>();
            youtubeCategoryModels.add(new YoutubeCategoryModel("Game", "Best Place for Game Promotoions", bitmap));
            youtubeCategoryModels.add(new YoutubeCategoryModel("Friends &amp Family", "Friends and Family can promote them", bitmap));
            youtubeCategoryModels.add(new YoutubeCategoryModel("Vlog", "Vloggers can promote their vlog", bitmap));
            youtubeCategoryModels.add(new YoutubeCategoryModel("Game", "Best Place for Game Promotoions", bitmap));
            youtubeCategoryModels.add(new YoutubeCategoryModel("Game", "Best Place for Game Promotoions", bitmap));
            youtubeCategoryModels.add(new YoutubeCategoryModel("Game", "Best Place for Game Promotoions", bitmap));
        }else if(category == 2){
            moduleName.setText("Facebook Category");
            youtubeCategoryModels = new ArrayList<>();
            youtubeCategoryModels.add(new YoutubeCategoryModel("Digital Marketing", "Best Place for Game Promotoions", bitmap));
            youtubeCategoryModels.add(new YoutubeCategoryModel("Food and Beverages", "Connect with food and beverages", bitmap));
            youtubeCategoryModels.add(new YoutubeCategoryModel("Vlog", "Vloggers can promote their vlog", bitmap));
            youtubeCategoryModels.add(new YoutubeCategoryModel("Game", "Best Place for Game Promotoions", bitmap));
            youtubeCategoryModels.add(new YoutubeCategoryModel("Game", "Best Place for Game Promotoions", bitmap));
            youtubeCategoryModels.add(new YoutubeCategoryModel("Game", "Best Place for Game Promotoions", bitmap));
        }


        recyclerView = findViewById(R.id.youtubeCategoryRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new SocialCategoryAdapter(this, getApplicationContext(), youtubeCategoryModels, category));

    }
}