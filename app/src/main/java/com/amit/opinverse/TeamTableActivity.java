package com.amit.opinverse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TeamTableActivity extends AppCompatActivity {

    private TableLayout mTableLayout;
    ProgressDialog mProgressBar;
    List<TeamTotalModel> teamTotalModels = new ArrayList<>();
    RecyclerView teamTotalRecycler;
    TeamTotalAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_table);

        teamTotalRecycler = findViewById(R.id.team_total_recycler);
        adapter = new TeamTotalAdapter(this, teamTotalModels);
        teamTotalRecycler.setAdapter(adapter);
        teamTotalRecycler.setLayoutManager(new LinearLayoutManager(this));

        mProgressBar = new ProgressDialog(this);

        findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        startLoadData();
    }

    private void startLoadData() {
        mProgressBar.setCancelable(false);
        mProgressBar.setMessage("Loading data...");
        mProgressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressBar.show();
        loadData();
    }


    public void loadData() {
        StringRequest request = new StringRequest(Request.Method.GET, "http://opinverse.com:8000/api-user-team-level/?user_id=" + CurrentUser.user.user_id + "&level=1", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject responseObject = new JSONObject(response);
                    JSONArray jsonArray = responseObject.getJSONArray("results");
                    if (responseObject.getInt("count") != 0) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            JSONObject sponsorObject = jsonObject.getJSONObject("sponsor_info");
                            TeamTotalModel teamTotalModel = new TeamTotalModel((i+1)+"", 1+"", jsonObject.getString("user_name")+jsonObject.getString("last_name"),
                                    sponsorObject.getString("sponsor_id"), 1+"", jsonObject.getString("create_at"), "null", "null", jsonObject.getString("status"));
                            teamTotalModels.add(teamTotalModel);
                        }
                        mProgressBar.dismiss();
                        adapter.notifyDataSetChanged();
                    } else {
                        mProgressBar.dismiss();
                        Toast.makeText(getApplicationContext(), "Nothing to display", Toast.LENGTH_SHORT).show();
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

        RequestHandler.getInstance(this).addToRequestQueue(request);
    }
}