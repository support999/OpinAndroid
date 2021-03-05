package com.amit.opinverse;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeamLevelAdapter extends RecyclerView.Adapter<TeamLevelAdapter.Holder> {
    List<String> level_name;
    List<Integer> level_icon;
    List<Integer> user_icons = new ArrayList<>();
    Context context;
    AppCompatActivity appCompatActivity;
    TeamLevelAdapter(List<Integer> level_icon, List<String> level_name, Context context, AppCompatActivity activity){
        this.level_icon = level_icon;
        this.level_name = level_name;
        this.context = context;
        this.appCompatActivity = activity;
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_team_level_grid, parent, false);
        return new TeamLevelAdapter.Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
        holder.team_level_icon.setImageResource(level_icon.get(position));
        holder.team_level_text.setText(level_name.get(position));
        holder.teamLEvelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_icons.clear();
                final ProgressDialog progressDialog = new ProgressDialog(context);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                final Constants constants = new Constants();
                constants.setUserAndLevel(CurrentUser.user.user_id, position+1+"");
                StringRequest request = new StringRequest(Request.Method.GET, constants.user_team, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            progressDialog.dismiss();
                            JSONObject jsonObject = new JSONObject(response);
                            user_icons.add(R.drawable.view_all_png);
                            int count = jsonObject.getInt("count");
                            switch (count){
                                case 0:
                                    break;
                                case 1:
                                    user_icons.add(R.drawable.user1);
                                    break;
                                case 2:
                                    user_icons.add(R.drawable.user1);
                                    user_icons.add(R.drawable.user1);
                                    break;
                                case 3:
                                    user_icons.add(R.drawable.user1);
                                    user_icons.add(R.drawable.user1);
                                    user_icons.add(R.drawable.user1);
                                    break;
                                case 4:
                                    user_icons.add(R.drawable.user1);
                                    user_icons.add(R.drawable.user1);
                                    user_icons.add(R.drawable.user1);
                                    user_icons.add(R.drawable.user1);
                                    break;
                                default:
                                    user_icons.add(R.drawable.user1);
                                    user_icons.add(R.drawable.user1);
                                    user_icons.add(R.drawable.user1);
                                    user_icons.add(R.drawable.user1);
                                    user_icons.add(R.drawable.user1);
                                    break;
                            }
                            Dialog dialog = new Dialog(context);
                            dialog.setContentView(R.layout.layout_team_diallog);
                            RecyclerView teamDialogRecycler = dialog.findViewById(R.id.teamDialogRecycler);
                            teamDialogRecycler.setAdapter(new TeamDialogAdapter(context, user_icons, position+1, dialog));
                            teamDialogRecycler.setLayoutManager(new GridLayoutManager(context, 3));
                            dialog.show();
                        } catch (JSONException e) {
                            progressDialog.dismiss();
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                    }
                });

                RequestHandler.getInstance(context).addToRequestQueue(request);
            }
        });
    }

    @Override
    public int getItemCount() {
        return level_icon.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView team_level_text;
        CircleImageView team_level_icon;
        ConstraintLayout teamLEvelLayout;
        public Holder(@NonNull View itemView) {
            super(itemView);
            team_level_text = itemView.findViewById(R.id.team_level_text);
            team_level_icon = itemView.findViewById(R.id.level_user_icon);
            teamLEvelLayout = itemView.findViewById(R.id.teamLevelLayout);
        }
    }
}
