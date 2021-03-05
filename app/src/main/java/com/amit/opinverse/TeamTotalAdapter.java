package com.amit.opinverse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TeamTotalAdapter extends RecyclerView.Adapter<TeamTotalAdapter.Holder> {
    List<TeamTotalModel> models;
    Context context;

    TeamTotalAdapter(Context context, List<TeamTotalModel> models){
        this.context = context;
        this.models = models;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TeamTotalAdapter.Holder(LayoutInflater.from(context).inflate(R.layout.item_team_total, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        TeamTotalModel teamTotalModel = models.get(position);
        holder.sl_no.setText(teamTotalModel.sl_no);
        holder.level_no.setText(teamTotalModel.level_no);
        holder.name.setText(teamTotalModel.name);
        holder.sponsor_id.setText(teamTotalModel.sponsor_id);
        holder.pos.setText(teamTotalModel.pos);
        holder.joining_on.setText(teamTotalModel.joining_on);
        holder.upgd_on.setText(teamTotalModel.upgd_on);
        holder.package_id.setText(teamTotalModel.package_id);
        holder.status.setText(teamTotalModel.status);
    }

    @Override
    public int getItemCount() {
        return models.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView sl_no, level_no, name, sponsor_id,  pos, joining_on, package_id, upgd_on, status;
        public Holder(@NonNull View itemView) {
            super(itemView);
            sl_no = itemView.findViewById(R.id.sl_no);
            level_no = itemView.findViewById(R.id.lvl_no);
            name = itemView.findViewById(R.id.name);
            sponsor_id = itemView.findViewById(R.id.sponsor_id);
            pos = itemView.findViewById(R.id.pos);
            joining_on = itemView.findViewById(R.id.joining_on);
            package_id = itemView.findViewById(R.id.package_id);
            upgd_on = itemView.findViewById(R.id.upgd_on);
            status = itemView.findViewById(R.id.status);
        }
    }
}
