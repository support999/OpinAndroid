package com.amit.opinverse;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentTutorial extends Fragment {
    RecyclerView tutorialRecycler;
    TutorialAdapter tutorialAdapter;
    LinearLayoutManager linearLayoutManager;
    List<TutorialModel> models;
    AppCompatActivity activity;

    View v;
    public FragmentTutorial() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_tutorial, container, false);
        models = new ArrayList<>();

        activity = (AppCompatActivity) v.getContext();

        final ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.GET, Constants.get_module, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    progressDialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("results");
                    for(int i = 0; i<jsonArray.length(); i++){
                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        Log.d("Response: ", jsonObject1.toString());
                        models.add(new TutorialModel(jsonObject1.getString("module_name"), jsonObject1.getString("module_desc"),
                                "", 0, 100));
                    }
                    tutorialRecycler = v.findViewById(R.id.tutorialRecycler);
                    tutorialAdapter = new TutorialAdapter(activity, v.getContext(), models);
                    linearLayoutManager = new LinearLayoutManager(v.getContext());
                    tutorialRecycler.setAdapter(tutorialAdapter);
                    tutorialRecycler.setLayoutManager(linearLayoutManager);
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



        return v;
    }
}