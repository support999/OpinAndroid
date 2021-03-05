package com.amit.opinverse;

public class Constants {
    String email, password, user_id, level, subscriptionId;
    public String user_login;
    public String user_team;
    public String subscription_info;
    public static String user_register = "http://opinverse.com:8000/api-user-login/";
    public static String fetch_tutorials = "http://opinverse.com:8000/api-get-set-tutorial/";
    public static String update_payment_status = "http://opinverse.com:8000/api-user-payment_transaction/";
    public static String create_wallet_url = "http://opinverse.com:8000/api-user-wallet/";
    public static String fetch_qa = "http://opinverse.com:8000/api-user-qa/";
    public static String get_module = "http://opinverse.com:8000/api-get-add-module/";
    public String generate_checksumhash_url;
    Constants(){
    }

    void setEmailAndPassword(String email, String password){
        this.email = email;
        this.password = password;
        user_login = "http://opinverse.com:8000/api-user-login/?email="+email+"&password="+password;
    }

    void setUserAndLevel(String user_id, String level){
        this.user_id = user_id;
        this.level = level;
        user_team = "http://opinverse.com:8000/api-user-team-level/?user_id="+user_id+"&level="+level;
    }

    void setSubscriptionId(String subscriptionId){
        this.subscriptionId = subscriptionId;
        subscription_info = "http://opinverse.com:8000/api-get_subscriptions/?subscription_id="+subscriptionId;
    }

    void setOrderId(String orderId, String customer_id, String txn_amount){
        generate_checksumhash_url = "http://opinverse.com:8000/get_paytm_checksum_test/?order_id="+orderId+"&cust_id="+customer_id+"&txn_amount="+txn_amount;
    }
}
