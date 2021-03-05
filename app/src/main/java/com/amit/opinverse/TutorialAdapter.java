package com.amit.opinverse;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TutorialAdapter extends RecyclerView.Adapter<TutorialAdapter.Holder> {
    AppCompatActivity activity;
    Context context;
    List<TutorialModel> tutorialModels;

    TutorialAdapter(AppCompatActivity appCompatActivity, Context context, List<TutorialModel> tutorialModels){
        this.context = context;
        this.activity = appCompatActivity;
        this.tutorialModels = tutorialModels;
    }
    @NonNull
    @Override
    public TutorialAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_tutorial, parent, false);
        TutorialAdapter.Holder holder = new TutorialAdapter.Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final TutorialAdapter.Holder holder, final int position) {
        holder.assignTutorialData(tutorialModels.get(position));
        holder.tutorialDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, ModuleActivity.class);
                intent.putExtra("moduleName", holder.tutorialName.getText().toString());
                CurrTutorial.tutorialModel = tutorialModels.get(position);
                activity.startActivity(intent);
                activity.overridePendingTransition(0,0);
            }
        });
    }

    @Override
    public int getItemCount() {
        return tutorialModels.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView tutorialName, tutorialDescription, tutorialDuration;
        ProgressBar tutorialProgress;
        public Holder(@NonNull View itemView) {
            super(itemView);

            tutorialName = itemView.findViewById(R.id.moduleName);
            tutorialDescription = itemView.findViewById(R.id.tutorialDesc);
            tutorialDuration = itemView.findViewById(R.id.tutorialLength);
            tutorialProgress = itemView.findViewById(R.id.tutorialProgress);
        }

        void assignTutorialData(TutorialModel tutorialModel){
            tutorialName.setText(tutorialModel.getTutorialName());
            tutorialDescription.setText(tutorialModel.getTutorialDescription());
            tutorialDuration.setText(tutorialModel.getTutorialDuration());
            tutorialProgress.setMax(tutorialModel.getTotalProgress());
            tutorialProgress.setProgress(tutorialModel.getTutorialProgress());
        }
    }
}
