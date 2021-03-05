package com.amit.opinverse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.Holder> {
    Context context;
    List<FaqModel> faqModels;

    FaqAdapter(Context context, List<FaqModel> faqModels){
        this.context = context;
        this.faqModels = faqModels;
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_faq, parent, false);
        FaqAdapter.Holder holder = new FaqAdapter.Holder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, int position) {
        final FaqModel faqModel = faqModels.get(position);
        holder.expandedLayout.setEnabled(false);
        holder.faqHead.setText(faqModel.getFaqHead());
        List<String> questions = new ArrayList<>();
        List<String> answers = new ArrayList<>();
        List<FaqQuestionModel> faqQuestionModels = faqModel.getFaqQuestionModel();
        for (FaqQuestionModel questionModel : faqQuestionModels) {
            questions.add(questionModel.question);
            answers.add(questionModel.answer);
        }
        RecyclerView.OnItemTouchListener mScrollTouchListener = new RecyclerView.OnItemTouchListener() {

            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                int action = e.getAction();
                switch (action) {
                    case MotionEvent.ACTION_MOVE:
                        rv.getParent().requestDisallowInterceptTouchEvent(true);
                        break;
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        };
        FaqQuestionAdapter faqQuestionAdapter = new FaqQuestionAdapter(context, questions, answers);
        holder.faq_qs_recycler.setAdapter(faqQuestionAdapter);
        holder.faq_qs_recycler.setLayoutManager(new LinearLayoutManager(context));
        holder.faq_qs_recycler.addOnItemTouchListener(mScrollTouchListener);
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
        return faqModels.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView faqHead, expandedText, faqQs;
        ImageView expandedBtn;
        LinearLayout expandedLayout;
        ImageView attachment;
        RecyclerView faq_qs_recycler;
        TextView expanded_list_faq;
        public Holder(@NonNull View itemView) {
            super(itemView);
            faqHead = itemView.findViewById(R.id.faqHead);
            expandedBtn = itemView.findViewById(R.id.expandBtn);
            expandedLayout = itemView.findViewById(R.id.expandedLayout);
            faq_qs_recycler = itemView.findViewById(R.id.faq_question_recycler);
        }
    }
}
