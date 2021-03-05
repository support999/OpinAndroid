package com.amit.opinverse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PaymentHistoryAdapter extends RecyclerView.Adapter<PaymentHistoryAdapter.Holder> {
    Context context;
    List<PaymentModel> paymentModels;

    PaymentHistoryAdapter(Context context, List<PaymentModel> paymentModels){
        this.context = context;
        this.paymentModels = paymentModels;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PaymentHistoryAdapter.Holder(LayoutInflater.from(context).inflate(R.layout.item_payment_history, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        PaymentModel paymentModel = paymentModels.get(position);
        holder.payment_id.setText(paymentModel.payment_user);
        holder.payout_amt.setText("Payout Amount : "+paymentModel.payout_amt);
        holder.payable_amt.setText(paymentModel.payable_amt);
        holder.payment_type.setText("Payment Type : "+paymentModel.payment_type);
        holder.total_ovr_cash.setText(paymentModel.total_ovr_cash);
        holder.payment_user.setText(paymentModel.payment_user);
        holder.total_team_ovr_cash.setText(paymentModel.total_team_ovr_cash);
        holder.total_revenue.setText(paymentModel.total_revenue);
        holder.admin_charges.setText(paymentModel.admin_charges);
        holder.tds.setText(paymentModel.getTds());
        holder.txn_date.setText(paymentModel.txn_date);
    }

    @Override
    public int getItemCount() {
        return paymentModels.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView payment_id, payout_amt, payable_amt, payment_type, total_ovr_cash, payment_user, total_team_ovr_cash, total_revenue, admin_charges, tds, txn_date, status;
        public Holder(@NonNull View itemView) {
            super(itemView);
            payment_id = itemView.findViewById(R.id.user_id);
            payout_amt = itemView.findViewById(R.id.payout_amt);
            payable_amt = itemView.findViewById(R.id.payable_amt);
            payment_type = itemView.findViewById(R.id.payment_type);
            total_ovr_cash = itemView.findViewById(R.id.total_ovr_cash);
            payment_user = itemView.findViewById(R.id.user_name);
            total_team_ovr_cash = itemView.findViewById(R.id.total_team_ovr_cash);
            total_revenue = itemView.findViewById(R.id.total_revenue);
            admin_charges = itemView.findViewById(R.id.admin_charges);
            tds = itemView.findViewById(R.id.tds);
            txn_date = itemView.findViewById(R.id.txn_date);
        }
    }
}
