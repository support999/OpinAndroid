package com.amit.opinverse;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ModuleActivity extends AppCompatActivity {
    TextView moduleName;
    RecyclerView moduleRecycler;
    ModuleAdapter moduleAdapter;
    TabLayout tabLayout;
    View mIndicator;
    int indicatorWidth;
    List<ModuleModel> modelList;
    ViewPager dummyPager;
    TabAdapter adapter;
    AppCompatActivity activity;
    ImageView backBtn;

    public ModuleActivity() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_module);

        activity = this;
        modelList = new ArrayList<>();

        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.GET, Constants.fetch_tutorials, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressDialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(getApplicationContext(), jsonObject.getInt("count")+"", Toast.LENGTH_SHORT).show();
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    for(int i = 0; i<jsonArray.length(); i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        modelList.add(new ModuleModel(jsonObject1.getString("name"), jsonObject1.getString("description"), "NA", 20, 5, 10, 4,"http://opinverse.com:8080/tutorials_video/"+jsonObject1.getString("video_url"),
                                jsonObject1.getString("img_url")));
                    }
                    moduleAdapter = new ModuleAdapter(activity, getApplicationContext(), modelList);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());

                    moduleRecycler.setAdapter(moduleAdapter);
                    moduleRecycler.setLayoutManager(linearLayoutManager);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                error.printStackTrace();
            }
        });

        RequestHandler.getInstance(activity).addToRequestQueue(request);

        Intent intent = getIntent();
        String moduleName1 = intent.getStringExtra("moduleName");

        moduleName = findViewById(R.id.moduleName);
        moduleName.setText(moduleName1);

        dummyPager = findViewById(R.id.dummyPager);
        adapter = new TabAdapter(getSupportFragmentManager());

        moduleRecycler = findViewById(R.id.moduleRecycler);

        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        final int tabs = 5; //fetch tabs from api call
        dummyPager.setOffscreenPageLimit(tabs);

        tabLayout = findViewById(R.id.tab);
        mIndicator = findViewById(R.id.indicator);

        for(int i = 1; i<=tabs; i++){
            tabLayout.addTab(tabLayout.newTab().setText("Day "+i));
            adapter.addFragment(new DummyFragment(), "Day "+i);
        }

        dummyPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(dummyPager);

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

        dummyPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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