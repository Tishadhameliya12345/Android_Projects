package com.exportdatainexcel2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {

    public List<Customer> customersList;

    public CustomerAdapter(List<Customer> customersList) {
        this.customersList = customersList;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.customer_card,parent,false);
        return new CustomerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder holder, int position) {
        Customer customer=customersList.get(position);
        holder.tvName.setText(customer.getName());
        holder.tvEmail.setText(customer.getEmail());
        holder.tvPhone.setText(customer.getPhone());
        holder.tvAddress.setText(customer.getAddress());
        holder.tvAge.setText(String.valueOf(customer.getAge()));
    }

    @Override
    public int getItemCount() {
        return customersList.size();
    }

    public static class CustomerViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvEmail, tvPhone, tvAddress, tvAge;

        public CustomerViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName=itemView.findViewById(R.id.txtName);
            tvEmail = itemView.findViewById(R.id.txtEmail);
            tvPhone = itemView.findViewById(R.id.txtPhone);
            tvAddress = itemView.findViewById(R.id.txtAddress);
            tvAge = itemView.findViewById(R.id.txtAge);
        }
    }
}
