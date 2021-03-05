package com.amit.opinverse;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Invoices {
    public InvoiceData[] getInvoices() {
        InvoiceData[] data = new InvoiceData[20];
        List<InvoiceData> invoiceData = new ArrayList<>();

        final Constants constants = new Constants();
        constants.setUserAndLevel(CurrentUser.user.user_id, "1");

        return data;

    }
}
