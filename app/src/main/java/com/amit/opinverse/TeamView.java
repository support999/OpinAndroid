package com.amit.opinverse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.SearchView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TeamView extends AppCompatActivity {
    RecyclerView team_view_recycler;
    List<String> team_member_name;
    List<String> team_member_contact;
    List<String> team_member_user_id, team_member_sponsor_id, team_member_sponsor_name, team_member_level, team_member_date_of_joining, team_member_upgrade_on, team_member_upgrade_package;
    TeamViewAdapter teamViewAdapter;
    TextView moduleName;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_view);

        team_member_name = new ArrayList<>();
        team_member_contact = new ArrayList<>();
        moduleName = findViewById(R.id.moduleName);
        searchView = findViewById(R.id.searchView);
        team_member_sponsor_name = new ArrayList<>();
        team_member_sponsor_id = new ArrayList<>();
        team_member_date_of_joining = new ArrayList<>();
        team_member_level = new ArrayList<>();
        team_member_upgrade_on = new ArrayList<>();
        team_member_upgrade_on = new ArrayList<>();
        team_member_upgrade_package = new ArrayList<>();
        team_member_user_id = new ArrayList<>();

        teamViewAdapter = new TeamViewAdapter(team_member_name, team_member_contact, team_member_user_id, team_member_sponsor_id, team_member_sponsor_name, team_member_level, team_member_date_of_joining, team_member_upgrade_on, team_member_upgrade_package, getApplicationContext());
        team_view_recycler = findViewById(R.id.team_view_recycler);
        team_view_recycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        team_view_recycler.setAdapter(teamViewAdapter);

        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                teamViewAdapter.getFilter().filter(newText);
                return false;
            }
        });

        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        moduleName.setText("Level "+intent.getStringExtra("level"));

        Constants constants = new Constants();
        constants.setUserAndLevel(CurrentUser.user.user_id, intent.getStringExtra("level"));
        StringRequest request = new StringRequest(Request.Method.GET, constants.user_team, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    for(int i = 0; i<jsonArray.length(); i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        team_member_name.add(jsonObject1.getString("user_name")+" "+jsonObject1.getString("last_name"));
                        team_member_contact.add(jsonObject1.getString("phone"));
                        team_member_date_of_joining.add(jsonObject1.getString("create_at"));
                        team_member_level.add(intent.getStringExtra("level"));
                        JSONObject sponsor_info = jsonObject1.getJSONObject("sponsor_info");
                        team_member_sponsor_id.add(sponsor_info.getString("sponsor_id"));
                        team_member_sponsor_name.add(sponsor_info.getString("sponsor_name"));
                        try{

                            team_member_upgrade_on.add("null");
                            team_member_upgrade_package.add("null");
                        }catch (Exception e){

                        }
                        team_member_user_id.add(jsonObject1.getString("user_id"));
                        teamViewAdapter.notifyDataSetChanged();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        RequestHandler.getInstance(getApplicationContext()).addToRequestQueue(request);



    }
}