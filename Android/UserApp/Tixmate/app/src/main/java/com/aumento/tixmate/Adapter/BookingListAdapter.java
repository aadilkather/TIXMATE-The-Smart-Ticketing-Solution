package com.aumento.tixmate.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aumento.tixmate.BookingCompletedActivity;
import com.aumento.tixmate.ModelClass.BookingListModelClass;
import com.aumento.tixmate.ModelClass.BookingListModelClass;
import com.aumento.tixmate.R;
import com.aumento.tixmate.TicketBookingActivity;
import com.aumento.tixmate.TrackBusLocationActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class BookingListAdapter extends RecyclerView.Adapter<BookingListAdapter.MyViewHolder> {

    Context mtx;
    List<BookingListModelClass> bookingList;

    public BookingListAdapter(Context mtx, List<BookingListModelClass> bookingList) {
        this.mtx = mtx;
        this.bookingList = bookingList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.raw_tick_list_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final BookingListModelClass lists = bookingList.get(position);

        holder.tikbusNameTV.setText(lists.getBus_name());
        holder.tiksourcePlaceTV.setText(lists.getDropoff_point());
        holder.tikstartTimTV.setText(lists.getPickup_time());
        holder.tikdestinationPlaceTV.setText(lists.getPickup_point());
        holder.tikendTimeTV.setText(lists.getDropoff_time());
        holder.tikBookingDateTV.setText(lists.getBooking_date());
        holder.tikseatsTV.setText(lists.getSeats());

        String ptime = null;
        try {
            DateFormat f1 = new SimpleDateFormat("HH:mm:ss"); //HH for hour of the day (0 - 23)
            Date d = f1.parse(lists.getPickup_time());
            DateFormat f2 = new SimpleDateFormat("h:mm a");
            ptime = f2.format(d).toLowerCase();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        holder.tikTimeTV.setText(ptime);


        holder.viewTicketLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bundle extras = new Bundle();
                extras.putString("bid", lists.getId());
                extras.putString("bus_id", lists.getBus_id());
                extras.putString("user_id", lists.getUser_id());
                extras.putString("seats", lists.getSeats());
                extras.putString("pickup",lists.getPickup_point());
                extras.putString("pickup_time",lists.getPickup_time());
                extras.putString("dropoff",lists.getDropoff_point());
                extras.putString("price",lists.getPrice());
                extras.putString("bdate",lists.getBooking_date());
                extras.putString("status",lists.getStatus());

                Intent intent = new Intent(mtx, BookingCompletedActivity.class);
                intent.putExtras(extras);
                mtx.startActivity(intent);

            }
        });


        holder.tiktrackBusTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mtx, TrackBusLocationActivity.class);
                intent.putExtra("bus_id",lists.getBus_id());
                mtx.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return bookingList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tikbusNameTV, tiktrackBusTV, tikdestinationPlaceTV, tikstartTimTV, tiksourcePlaceTV, tikendTimeTV, tikBookingDateTV, tikTimeTV, tikseatsTV;
        private LinearLayout viewTicketLinearLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tikbusNameTV = (TextView) itemView.findViewById(R.id.tikbusNameTextView);
            tiktrackBusTV = (TextView) itemView.findViewById(R.id.tiktrackBusTextView);
            tikdestinationPlaceTV = (TextView) itemView.findViewById(R.id.tikdestinationPlaceTextView);
            tikstartTimTV = (TextView) itemView.findViewById(R.id.tikstartTimTextView);
            tiksourcePlaceTV = (TextView) itemView.findViewById(R.id.tiksourcePlaceTextView);
            tikendTimeTV = (TextView) itemView.findViewById(R.id.tikendTimeTextView);
            tikBookingDateTV = (TextView) itemView.findViewById(R.id.tikBookingDateTextView);
            tikTimeTV = (TextView) itemView.findViewById(R.id.tikTimeTextView);
            tikseatsTV = (TextView) itemView.findViewById(R.id.tikseatsTextView);

            viewTicketLinearLayout = (LinearLayout) itemView.findViewById(R.id.viewTicketLinearLayout);


        }
    }
}
