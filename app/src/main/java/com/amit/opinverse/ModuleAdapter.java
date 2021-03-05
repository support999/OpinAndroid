package com.amit.opinverse;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ModuleAdapter extends RecyclerView.Adapter<ModuleAdapter.Holder> {
    List<ModuleModel> moduleModels;
    AppCompatActivity activity;
    Context context;

    ModuleAdapter(AppCompatActivity appCompatActivity, Context context, List<ModuleModel> moduleModels){
        this.context  =context;
        this.activity = appCompatActivity;
        this.moduleModels = moduleModels;
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_module, parent, false);
        ModuleAdapter.Holder holder = new ModuleAdapter.Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position) {
        holder.assignModuleData(moduleModels.get(position));
        holder.videoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CurrentModule.moduleModel = moduleModels.get(position);
                activity.startActivity(new Intent(activity, VideoActivity.class));
                activity.overridePendingTransition(0, 0);
            }
        });
        holder.taskBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.startActivity(new Intent(activity, AssignmentTaskActivity.class));
                activity.overridePendingTransition(0, 0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return moduleModels.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView moduleName, moduleDuration, moduleTask;
        ProgressBar moduleProgress;
        RatingBar moduleRating;
        ImageView videoBtn, taskBtn;
        public Holder(@NonNull View itemView) {
            super(itemView);

            moduleName = itemView.findViewById(R.id.moduleName);
            moduleDuration = itemView.findViewById(R.id.moduleDuration);
            moduleTask = itemView.findViewById(R.id.moduleTask);

            moduleProgress = itemView.findViewById(R.id.moduleProgress);
            moduleRating = itemView.findViewById(R.id.moduleRating);
            videoBtn = itemView.findViewById(R.id.videoBtn);

            taskBtn = itemView.findViewById(R.id.taskBtn);
        }

        void assignModuleData(ModuleModel moduleModel){
            moduleName.setText(moduleModel.getModuleName());
            moduleDuration.setText(moduleModel.getModuleDuration());
            moduleTask.setText(moduleModel.getModuleTasksDone()+"/"+moduleModel.getModuleTotalTasks()+" Tasks Done");
            moduleProgress.setProgress(moduleModel.getModuleProgress());
            moduleRating.setRating(moduleModel.getModuleRating());
            moduleRating.setIsIndicator(true);
        }
    }
}
