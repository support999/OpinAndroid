package com.amit.opinverse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MotionEventCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FaqActivity extends AppCompatActivity {
    List<FaqModel> faqModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);

        faqModels = new ArrayList<>();

        ImageView backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        StringRequest request = new StringRequest(Request.Method.GET, Constants.fetch_qa, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getInt("count") == 0) {
                        Toast.makeText(getApplicationContext(), "Could not find any FAQ questions", Toast.LENGTH_SHORT).show();
                    } else {
                        JSONArray jsonArray = jsonObject.getJSONArray("results");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            List<FaqQuestionModel> faqQuestionModels = new ArrayList<>();
                            JSONArray qa_data = jsonObject1.getJSONArray("qa_data");
                            for (int j = 0; j<qa_data.length(); j++){
                                JSONObject qa_object = qa_data.getJSONObject(j);
                                faqQuestionModels.add(new FaqQuestionModel(qa_object.getString("questions"), qa_object.getString("answers")));
                            }
                            FaqModel faqModel = new FaqModel(jsonObject1.getString("title"), faqQuestionModels);
                            faqModels.add(faqModel);
                        }
                        RecyclerView faqRecycler = findViewById(R.id.faqRecycler);
                        faqRecycler.setAdapter(new FaqAdapter(getApplicationContext(), faqModels));
                        faqRecycler.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestHandler.getInstance(this).addToRequestQueue(request);


    }
}
