package com.amit.opinverse;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class TeamDialogAdapter extends RecyclerView.Adapter<TeamDialogAdapter.Holder> {
    Context context;
    List<Integer> user_icons;
    int level;
    Dialog dialog;
    TeamDialogAdapter(Context context, List<Integer> user_icons, int position, Dialog dialog){
        this.context = context;
        this.user_icons = user_icons;
        this.level = position;
        this.dialog = dialog;
    }
    @NonNull
    @Override
    public TeamDialogAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TeamDialogAdapter.Holder(LayoutInflater.from(context).inflate(R.layout.item_team_dialog, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
        holder.circular_image_dialog.setImageResource(user_icons.get(position));
        holder.circular_image_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(position == 0){
                    Intent intent = new Intent(context, TeamView.class);
                    intent.putExtra("level", level+"");
                    context.startActivity(intent);
                    dialog.dismiss();
                    //activity.finish();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return user_icons.size();
    }

    public class Holder extends RecyclerView.ViewHolder{
        CircleImageView circular_image_dialog;
        public Holder(@NonNull View itemView) {
            super(itemView);
            circular_image_dialog = itemView.findViewById(R.id.circular_image_dialog);
        }
    }
}
