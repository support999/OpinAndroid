package com.amit.opinverse;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AssignmentTaskAdapter extends RecyclerView.Adapter<AssignmentTaskAdapter.Holder> {
    Context context;
    int size;

    AssignmentTaskAdapter(Context context, int size){
        this.context = context;
        this.size = size;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_assignment_task, parent, false);
        AssignmentTaskAdapter.Holder holder = new AssignmentTaskAdapter.Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "OPIN VERSE");
                context.startActivity(Intent.createChooser(intent, "Share using..."));
            }
        });
    }

    @Override
    public int getItemCount() {
        return size;
    }

    public class Holder extends RecyclerView.ViewHolder {
        ImageView shareBtn, likeBtn;
        public Holder(@NonNull View itemView) {
            super(itemView);

            shareBtn = itemView.findViewById(R.id.shareBtn);
            likeBtn = itemView.findViewById(R.id.likeBtn);
        }
    }
}
