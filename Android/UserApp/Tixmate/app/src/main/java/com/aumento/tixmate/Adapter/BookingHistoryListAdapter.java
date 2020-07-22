package com.aumento.tixmate.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aumento.tixmate.BookingCompletedActivity;
import com.aumento.tixmate.ModelClass.BookingListModelClass;
import com.aumento.tixmate.R;
import com.aumento.tixmate.TrackBusLocationActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BookingHistoryListAdapter extends RecyclerView.Adapter<BookingHistoryListAdapter.MyViewHolder> {

    Context mtx;
    List<BookingListModelClass> bookingList;

    public BookingHistoryListAdapter(Context mtx, List<BookingListModelClass> bookingList) {
        this.mtx = mtx;
        this.bookingList = bookingList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.raw_tick_hist_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final BookingListModelClass lists = bookingList.get(position);

        holder.histbusNameTV.setText(lists.getBus_name());
        holder.histsourcePlaceTV.setText(lists.getDropoff_point());
        holder.histstartTimTV.setText(lists.getPickup_time());
        holder.histdestinationPlaceTV.setText(lists.getPickup_point());
        holder.histendTimeTV.setText(lists.getDropoff_time());
        holder.histBookingDateTV.setText(lists.getBooking_date());
        holder.histseatsTV.setText(lists.getSeats());
        holder.histtrackBusTV.setText(lists.getPrice());
        String ptime = null;
        try {
            DateFormat f1 = new SimpleDateFormat("HH:mm:ss"); //HH for hour of the day (0 - 23)
            Date d = f1.parse(lists.getPickup_time());
            DateFormat f2 = new SimpleDateFormat("h:mm a");
            ptime = f2.format(d).toLowerCase();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.histTimeTV.setText(ptime);

    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView histbusNameTV, histtrackBusTV, histdestinationPlaceTV, histstartTimTV, histsourcePlaceTV, histendTimeTV, histBookingDateTV, histTimeTV, histseatsTV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            histbusNameTV = (TextView) itemView.findViewById(R.id.histbusNameTextView);
            histtrackBusTV = (TextView) itemView.findViewById(R.id.histtrackBusTextView);
            histdestinationPlaceTV = (TextView) itemView.findViewById(R.id.histdestinationPlaceTextView);
            histstartTimTV = (TextView) itemView.findViewById(R.id.histstartTimTextView);
            histsourcePlaceTV = (TextView) itemView.findViewById(R.id.histsourcePlaceTextView);
            histendTimeTV = (TextView) itemView.findViewById(R.id.histendTimeTextView);
            histBookingDateTV = (TextView) itemView.findViewById(R.id.histBookingDateTextView);
            histTimeTV = (TextView) itemView.findViewById(R.id.histTimeTextView);
            histseatsTV = (TextView) itemView.findViewById(R.id.histseatsTextView);
        }
    }
}
