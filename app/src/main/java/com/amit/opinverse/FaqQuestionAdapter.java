package com.amit.opinverse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FaqQuestionAdapter extends RecyclerView.Adapter<FaqQuestionAdapter.Holder> {
    Context context;
    List<String> questions;
    List<String> answers;
    FaqQuestionAdapter(Context context, List<String> questions, List<String> answers){
        this.context = context;
        this.questions = questions;
        this.answers = answers;
    }
    @NonNull
    @Override
    public FaqQuestionAdapter.Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FaqQuestionAdapter.Holder(LayoutInflater.from(context).inflate(R.layout.item_faq_questions, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FaqQuestionAdapter.Holder holder, int position) {
        holder.faq_qs.setText(questions.get(position));
        holder.faq_ans.setText(answers.get(position));
        holder.expandedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.expandedLayout.isEnabled()){
                    holder.expandedLayout.setVisibility(View.GONE);
                    holder.expandedLayout.setEnabled(false);
                    holder.expandedBtn.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_arrow_drop_down_24));
                }else{
                    holder.expandedBtn.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_baseline_arrow_drop_up_24));
                    holder.expandedLayout.setEnabled(true);
                    holder.expandedLayout.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView faq_qs, faq_ans;
        LinearLayout expandedLayout;
        ImageView expandedBtn;
        public Holder(@NonNull View itemView) {
            super(itemView);
            faq_ans = itemView.findViewById(R.id.answer_text);
            faq_qs = itemView.findViewById(R.id.faq_question);
            expandedBtn = itemView.findViewById(R.id.expandBtn);
            expandedLayout = itemView.findViewById(R.id.expandedLayout);
        }
    }
}
