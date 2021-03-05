package com.amit.opinverse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ContactsRecycler extends RecyclerView.Adapter<ContactsRecycler.Holder> {
    Context context;
    List<Contacts> contacts;
    ContactsRecycler(Context context, List<Contacts> contacts){
        this.context = context;
        this.contacts = contacts;
    }
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ContactsRecycler.Holder(LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Contacts contact = contacts.get(position);
        StringBuilder ph_nos = new StringBuilder();
        holder.contact_name.setText(contact.name);
        for (int i = 0; i<contact.numbers.size(); i++){
            ph_nos.append(contact.numbers.get(i)).append("\n");
        }
        try {
            holder.contact_number.setText(contact.numbers.get(0));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView contact_name, contact_number;
        public Holder(@NonNull View itemView) {
            super(itemView);
            contact_name = itemView.findViewById(R.id.contact_name);
            contact_number = itemView.findViewById(R.id.contact_number);
        }
    }
}
