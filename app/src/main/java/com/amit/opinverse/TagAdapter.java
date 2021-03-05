package com.amit.opinverse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.Holder> {
    List<String> tags;
    Context context;
    int access;
    TagAdapter(Context context, List<String> tags, int access){
        this.tags = tags;
        this.context = context;
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TagAdapter.Holder(LayoutInflater.from(context).inflate(R.layout.item_tag, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.tag_text.setText(tags.get(position));
        holder.tag_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tags.remove(position);
                if(access == 1) {
                    TagsListClass.delete(position);
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return tags.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView tag_text;
        ImageView tag_cancel;
        public Holder(@NonNull View itemView) {
            super(itemView);
            tag_cancel = itemView.findViewById(R.id.tag_cancel);
            tag_text = itemView.findViewById(R.id.tag_text);
        }
    }
}
