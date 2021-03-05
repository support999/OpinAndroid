package com.amit.opinverse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

public class ClientActivity extends AppCompatActivity {
    RecyclerView client_recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        client_recycler = findViewById(R.id.client_recycler);
        client_recycler.setAdapter(new ClientAdapter(this));
        client_recycler.setLayoutManager(new LinearLayoutManager(this));
    }
}