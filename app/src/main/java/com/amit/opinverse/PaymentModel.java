package com.amit.opinverse;

public class PaymentModel {
    String payment_id, payout_amt, payable_amt, payment_type, total_ovr_cash, payment_user, total_team_ovr_cash, total_revenue, admin_charges, tds, txn_date, status;
    PaymentModel(String payment_id, String payout_amt, String  payable_amt, String  payment_type, String  total_ovr_cash, String  payment_user, String  total_team_ovr_cash, String  total_revenue, String  admin_charges, String  tds, String  txn_date, String  status){
        this.payment_id = payment_id;
        this.payout_amt = payout_amt;
        this.payable_amt = payable_amt;
        this.payment_type = payment_type;
        this.total_ovr_cash = total_ovr_cash;
        this.payment_user = payment_user;
        this.total_team_ovr_cash = total_team_ovr_cash;
        this.total_revenue = total_revenue;
        this.admin_charges = admin_charges;
        this.tds = tds;
        this.txn_date =  txn_date;
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String getAdmin_charges() {
        return admin_charges;
    }

    public String getPayable_amt() {
        return payable_amt;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public String getPayment_user() {
        return payment_user;
    }

    public String getPayout_amt() {
        return payout_amt;
    }

    public String getTds() {
        return tds;
    }

    public String getTotal_ovr_cash() {
        return total_ovr_cash;
    }

    public String getTotal_revenue() {
        return total_revenue;
    }

    public String getTotal_team_ovr_cash() {
        return total_team_ovr_cash;
    }

    public String getTxn_date() {
        return txn_date;
    }
}
