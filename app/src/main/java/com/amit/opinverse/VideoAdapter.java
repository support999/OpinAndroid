package com.amit.opinverse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.Holder> {
    Context context;
    int category;
    VideoAdapter(Context context, int category){
        this.context = context;
        this.category = category;
    }
    @NonNull
    @Override
    public VideoAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        if(category == 1) {
            v = LayoutInflater.from(context).inflate(R.layout.item_youtube_video, parent, false);
        }else{
            v = LayoutInflater.from(context).inflate(R.layout.item_facebook_feed, parent, false);
        }
        VideoAdapter.Holder holder = new VideoAdapter.Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull VideoAdapter.Holder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class Holder extends RecyclerView.ViewHolder {
        public Holder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
