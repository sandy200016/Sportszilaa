package com.example.sportszilla;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class contactAdapter extends RecyclerView.Adapter<contactAdapter.ViewHolder> implements Filterable {
    List<contactList> phoneList;
    List<contactList> phoneListAll;


    public contactAdapter(List<contactList> phoneList) {

        this.phoneList = phoneList;
        phoneListAll=new ArrayList<>(phoneList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.contact_row,parent,false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        contactList phone=phoneList.get(position);
        holder.contactName.setText(phone.getContactName());
        holder.contactPhone.setText(phone.getPhoneNo());
        holder.contactPerson.setText(phone.getContactPerson());
        boolean isExpanded =phoneList.get(position).isExpanded();
        holder.expandableMenu.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

        holder.whatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });



    }



    @Override
    public int getItemCount() {
        return phoneList.size();
    }

    @Override
    public Filter getFilter() {

        return filterPhone;
    }

    private Filter filterPhone=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            String searchText=constraint.toString().toLowerCase();
            List<contactList> tempData=new ArrayList<>();
            if(searchText.length()==0 || searchText.isEmpty()){
                tempData.addAll(phoneListAll);

            }
            else {

                for (contactList data:phoneListAll){
                    if(data.getContactName().toLowerCase().contains(searchText)){
                        tempData.add(data);

                    }

                }

            }

            FilterResults filterResults=new FilterResults();
            filterResults.values=tempData;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            phoneList.clear();
            phoneList.addAll((Collection<? extends contactList>) results.values);
            notifyDataSetChanged();


        }

    };

    class ViewHolder extends RecyclerView.ViewHolder{
        RelativeLayout heading,expandableMenu;
        TextView contactName,contactPhone,contactPerson;
        ImageView whatsApp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            heading=itemView.findViewById(R.id.contact_heading);
            expandableMenu=itemView.findViewById(R.id.expandableMenu);
            contactName=itemView.findViewById(R.id.contactName);
            contactPhone=itemView.findViewById(R.id.contactNumber);
            contactPerson=itemView.findViewById(R.id.contactPerson);
            whatsApp=itemView.findViewById(R.id.whatsapp);



            heading.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    contactList phone1=phoneList.get(getAdapterPosition());
                    phone1.setExpanded(!phone1.isExpanded());
                    notifyItemChanged(getAdapterPosition());


                }
            });


        }
    }


}
