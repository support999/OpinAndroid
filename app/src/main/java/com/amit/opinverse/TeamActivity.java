package com.amit.opinverse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class TeamActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<Integer> level_icon;
    List<String> level_name;
    AppCompatActivity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team2);
        activity = (AppCompatActivity)this;

        level_icon = new ArrayList<>();
        level_name = new ArrayList<>();

        level_icon.add(R.drawable.user1);
        level_name.add("Team Level 1");
        level_icon.add(R.drawable.user1);
        level_name.add("Team Level 2");
        level_icon.add(R.drawable.user1);
        level_name.add("Team Level 3");
        level_icon.add(R.drawable.user1);
        level_name.add("Team Level 4");
        level_icon.add(R.drawable.user1);
        level_name.add("Team Level 5");
        level_icon.add(R.drawable.user1);
        level_name.add("Team Level 6");
        level_icon.add(R.drawable.user1);
        level_name.add("Team Level 7");
        level_icon.add(R.drawable.user1);
        level_name.add("Team Level 8");
        level_icon.add(R.drawable.user1);
        level_name.add("Team Level 9");
        level_icon.add(R.drawable.user1);
        level_name.add("Team Level 10");

        recyclerView = findViewById(R.id.team_level_recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(new TeamLevelAdapter(level_icon, level_name, this, activity));

    }

    public void executeClick(View view) {
    }
}