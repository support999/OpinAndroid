package com.amit.opinverse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AssingmentAdapter extends RecyclerView.Adapter<AssingmentAdapter.Holder> {

    Context context;
    List<AssignmentModel> assignmentModels;

    AssingmentAdapter(Context context, List<AssignmentModel> assignmentModels){
        this.context = context;
        this.assignmentModels = assignmentModels;
    }
    @NonNull
    @Override
    public AssingmentAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_assignment, parent, false);
        AssingmentAdapter.Holder holder = new AssingmentAdapter.Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AssingmentAdapter.Holder holder, int position) {
        final AssignmentModel assignmentModel = assignmentModels.get(position);
        holder.assignAssignmentData(assignmentModels.get(position));
        holder.assgnName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.extendedLayout.isEnabled()){
                    holder.extendedLayout.setVisibility(View.GONE);
                    holder.extendedLayout.setEnabled(false);
                    holder.assgnName.setBackgroundColor(context.getResources().getColor(android.R.color.white));
                }else {
                    holder.assgnName.setBackgroundColor(context.getResources().getColor(R.color.background_custom_layout));
                    holder.extendedLayout.setEnabled(true);
                    holder.extendedLayout.setVisibility(View.VISIBLE);
                    holder.assgnText.setText(assignmentModel.getAssgnText());
                    holder.assgnSubName.setText(assignmentModel.getAssgnSubName());
                    holder.ratingBar.setIsIndicator(true);
                    holder.ratingBar.setRating(assignmentModel.getProgress());
                    holder.assgnCertificate.setImageResource(assignmentModel.getAssgnCertificate());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return assignmentModels.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView assgnName, assgnSubName, assgnText;
        RatingBar ratingBar;
        ImageView assgnCertificate;
        LinearLayout extendedLayout;
        public Holder(@NonNull View itemView) {
            super(itemView);
            extendedLayout = itemView.findViewById(R.id.extendedLayout);;
            assgnName = itemView.findViewById(R.id.assgnName);
            assgnSubName = itemView.findViewById(R.id.assgnSubName);
            assgnText = itemView.findViewById(R.id.assgnText);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            assgnCertificate = itemView.findViewById(R.id.assgnCertificate);
        }

        void assignAssignmentData(AssignmentModel assignmentModel){
            extendedLayout.setEnabled(false);
            assgnName.setBackgroundColor(context.getResources().getColor(android.R.color.white));
            assgnName.setText(assignmentModel.getAssgnName());
        }
    }
}
