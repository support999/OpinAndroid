package com.amit.opinverse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TeamViewAdapter extends RecyclerView.Adapter<TeamViewAdapter.Holder> {
    Context context;
    boolean expanded = false;
    List<String> team_member_name, team_member_contact, team_member_user_id, team_member_sponsor_id, team_member_sponsor_name, team_member_level, team_member_date_of_joining, team_member_upgrade_on, team_member_upgrade_package;
    TeamViewAdapter(List<String> team_member_name, List<String> team_member_contact, List<String> team_member_user_id, List<String> team_member_sponsor_id, List<String> team_member_sponsor_name, List<String> team_member_level,
                    List<String> team_member_date_of_joining, List<String> team_member_upgrade_on, List<String> team_member_upgrade_package, Context context){
        this.context = context;
        this.team_member_name = team_member_name;
        this.team_member_contact = team_member_contact;
        this.team_member_date_of_joining = team_member_date_of_joining;
        this.team_member_level = team_member_level;
        this.team_member_sponsor_id = team_member_sponsor_id;
        this.team_member_sponsor_name = team_member_sponsor_name;
        this.team_member_upgrade_on = team_member_upgrade_on;
        this.team_member_user_id = team_member_user_id;
        this.team_member_upgrade_package = team_member_upgrade_package;
    }
    @NonNull
    @Override
    public TeamViewAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TeamViewAdapter.Holder(LayoutInflater.from(context).inflate(R.layout.item_team_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TeamViewAdapter.Holder holder, int position) {
        holder.team_member_contact.setText(team_member_contact.get(position));
        holder.team_member_name.setText(team_member_name.get(position));
        holder.level.setText(team_member_level.get(position));
        holder.date_of_joining.setText(team_member_date_of_joining.get(position));
        holder.name.setText(team_member_name.get(position));
        holder.sponsor_name.setText(team_member_name.get(position));
        holder.sponsor_id.setText(team_member_sponsor_id.get(position));
        holder.upgrade_on.setText(team_member_upgrade_on.get(position));
        holder.upgrade_package.setText(team_member_upgrade_package.get(position));
        holder.user_id.setText(team_member_user_id.get(position));

        holder.expand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(expanded){
                    holder.expandLayout.setVisibility(View.GONE);
                    holder.expand.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24);

                }else{
                    holder.expandLayout.setVisibility(View.VISIBLE);
                    holder.expand.setImageResource(R.drawable.ic_baseline_arrow_drop_up_24);
                }
                expanded = !expanded;
            }
        });
    }

    @Override
    public int getItemCount() {
        return team_member_contact.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        TextView team_member_name, team_member_contact, name, user_id, sponsor_id, sponsor_name, level, date_of_joining, upgrade_package, upgrade_on;
        ImageView expand;
        LinearLayout expandLayout;
        public Holder(@NonNull View itemView) {
            super(itemView);
            team_member_name = itemView.findViewById(R.id.team_member_name);
            team_member_contact = itemView.findViewById(R.id.team_member_contact);
            name = itemView.findViewById(R.id.member_name);
            user_id = itemView.findViewById(R.id.member_user_id);
            sponsor_id = itemView.findViewById(R.id.member_sponsor_id);
            sponsor_name = itemView.findViewById(R.id.member_sponsor_name);
            level = itemView.findViewById(R.id.member_level);
            date_of_joining = itemView.findViewById(R.id.member_joining_on);
            upgrade_package = itemView.findViewById(R.id.member_upgrade_package);
            upgrade_on = itemView.findViewById(R.id.member_upgrade_on);
            expand = itemView.findViewById(R.id.expand);
            expandLayout = itemView.findViewById(R.id.expand_team);
        }
    }

    Filter getFilter(){
        return nameFilter;
    }

    private Filter nameFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<String> team_member_name_filtered, team_member_contact_filtered;
            team_member_contact_filtered = new ArrayList<>();
            team_member_name_filtered = new ArrayList<>();
            if (charSequence == null || charSequence.length() == 0) {
                team_member_name_filtered.addAll(team_member_name);
                team_member_contact_filtered.addAll(team_member_contact);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (String name : team_member_name) {
                    if (name.toLowerCase().contains(filterPattern)) {
                        team_member_name_filtered.add(name);
                        team_member_contact_filtered.add(team_member_contact.get(team_member_name.indexOf(name)));
                    }
                }
            }
            FilterResults results = new FilterResults();
            TeamViewWrapper teamViewWrapper = new TeamViewWrapper();
            teamViewWrapper.team_member_contact = team_member_contact_filtered;
            teamViewWrapper.team_member_name = team_member_name_filtered;
            results.values = teamViewWrapper;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
             team_member_name.clear();
             team_member_contact.clear();

             TeamViewWrapper teamViewWrapper = (TeamViewWrapper)filterResults.values;
             team_member_name.addAll(teamViewWrapper.team_member_name);
             team_member_contact.addAll(teamViewWrapper.team_member_contact);
        }
    };
}

class TeamViewWrapper{
    List<String> team_member_name;
    List<String> team_member_contact;
}
