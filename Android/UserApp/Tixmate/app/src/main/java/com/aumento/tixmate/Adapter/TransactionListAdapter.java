package com.aumento.tixmate.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aumento.tixmate.BookingCompletedActivity;
import com.aumento.tixmate.ModelClass.TransactionListModelClass;
import com.aumento.tixmate.R;
import com.aumento.tixmate.TrackBusLocationActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TransactionListAdapter extends RecyclerView.Adapter<TransactionListAdapter.MyViewHolder> {

    Context mtx;
    List<TransactionListModelClass> transactionList;

    public TransactionListAdapter(Context mtx, List<TransactionListModelClass> transactionList) {
        this.mtx = mtx;
        this.transactionList = transactionList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.raw_trans_list, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final TransactionListModelClass lists = transactionList.get(position);

        if(lists.getBus_name().equals(""))
            holder.titleTV.setText("Wallet");
        else
            holder.titleTV.setText(lists.getBus_name());
        holder.dateTV.setText(lists.getTdate());

        if(lists.getStatus().equals("added"))
        {
            holder.amountTV.setTextColor(mtx.getColor(R.color.add));
            holder.transIV.setImageDrawable(mtx.getDrawable(R.drawable.up));
            holder.amountTV.setText("+₹"+lists.getAmount());

        }
        else if(lists.getStatus().equals("deduct"))
        {
            holder.amountTV.setTextColor(mtx.getColor(R.color.deduct));
            holder.transIV.setImageDrawable(mtx.getDrawable(R.drawable.down));
            holder.amountTV.setText("-₹"+lists.getAmount());
        }

    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView titleTV, dateTV, amountTV;
        private ImageView transIV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTV = (TextView) itemView.findViewById(R.id.titleTextView);
            dateTV = (TextView) itemView.findViewById(R.id.dateTextView);
            amountTV = (TextView) itemView.findViewById(R.id.amountTextView);
            transIV = (ImageView) itemView.findViewById(R.id.transImageView);

        }
    }
}
