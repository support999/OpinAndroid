package com.amit.opinverse;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SocialCategoryAdapter extends RecyclerView.Adapter<SocialCategoryAdapter.Holder> {
    Context context;
    List<YoutubeCategoryModel> youtubeCategoryModelList;
    AppCompatActivity appCompatActivity;
    int category;

    SocialCategoryAdapter(AppCompatActivity appCompatActivity, Context context, List<YoutubeCategoryModel> models, int category){
        this.context = context;
        this.youtubeCategoryModelList = models;
        this.appCompatActivity = appCompatActivity;
        this.category = category;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_youtube, parent, false);
        SocialCategoryAdapter.Holder holder = new SocialCategoryAdapter.Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, int position) {
        final YoutubeCategoryModel model = youtubeCategoryModelList.get(position);
        holder.youtubeName.setText(model.getYoutubeCategoryName());
        holder.youtubeDesc.setText(model.getYoutubeCategoryDesc());
        //Drawable d = new BitmapDrawable(appCompatActivity.getResources(), model.getImageBitmap());
        //holder.youtubeImage.setBackground(d);
        holder.youtube_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "This will open a new page", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(appCompatActivity, ActivitySocialFeed.class);
                intent.putExtra("name", model.youtubeCategoryName);
                intent.putExtra("category", category);
                appCompatActivity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return youtubeCategoryModelList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView youtubeName, youtubeDesc;
        ImageView youtubeImage;
        ConstraintLayout youtube_layout;
        public Holder(@NonNull View itemView) {
            super(itemView);
            youtubeImage = itemView.findViewById(R.id.youtubeCategoryImage);
            youtubeName = itemView.findViewById(R.id.youtubeCategoryName);
            youtubeDesc = itemView.findViewById(R.id.youtubeCategoryDesc);
            youtube_layout = itemView.findViewById(R.id.youtube_layout);
        }
    }
}
