package com.aumento.tixmate.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aumento.tixmate.BusRouteActivity;
import com.aumento.tixmate.ModelClass.BusDetailsModelClass;
import com.aumento.tixmate.R;
import com.aumento.tixmate.TicketBookingActivity;
import com.aumento.tixmate.TrackBusLocationActivity;

import java.util.List;

public class BusListAdapter extends RecyclerView.Adapter<BusListAdapter.MyViewHolder> {

    Context mtx;
    List<BusDetailsModelClass> busList;

    public BusListAdapter(Context mtx, List<BusDetailsModelClass> busList) {
        this.mtx = mtx;
        this.busList = busList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bus_list_card, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final BusDetailsModelClass lists = busList.get(position);

        holder.busNameTV.setText(lists.getBus_name());
        holder.startTimeTV.setText(lists.getStart_time());
        holder.endTimeTV.setText(lists.getEnd_time());
        holder.seatsTV.setText(lists.getSeats());
        holder.startPlaceTV.setText(lists.getStart_place());
        holder.destPlaceTV.setText(lists.getDest_place());
        holder.travelTimeTV.setText(lists.getTravel_time());



        holder.bookBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle extras = new Bundle();
                extras.putString("bus_id",lists.getBus_id());
                extras.putString("start_name", lists.getStart_place());
                extras.putString("start_time", lists.getStart_time());
                extras.putString("dest_name", lists.getDest_place());
                extras.putString("dest_time", lists.getEnd_time());
                extras.putString("route_id", lists.getRoute_id());

                Intent intent = new Intent(mtx, TicketBookingActivity.class);
                intent.putExtras(extras);
                mtx.startActivity(intent);

            }
        });


        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mtx, BusRouteActivity.class);
                intent.putExtra("route_id",lists.getRoute_id());
                mtx.startActivity(intent);

            }
        });
        holder.trackBusTV.setOnClickListener(new View.OnClickListener() {
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
        return busList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView startTimeTV, endTimeTV, seatsTV, startPlaceTV, destPlaceTV, busNameTV, travelTimeTV, trackBusTV;
        private Button bookBT;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            startTimeTV = (TextView) itemView.findViewById(R.id.startTimTextView);
            endTimeTV = (TextView) itemView.findViewById(R.id.endTimeTextView);
            seatsTV = (TextView) itemView.findViewById(R.id.seatsTextView);
            startPlaceTV = (TextView) itemView.findViewById(R.id.sourcePlaceTextView);
            destPlaceTV = (TextView) itemView.findViewById(R.id.destinationPlaceTextView);
            busNameTV = (TextView) itemView.findViewById(R.id.busNameTextView);
            travelTimeTV = (TextView) itemView.findViewById(R.id.travelTimeTextView);
            bookBT = (Button) itemView.findViewById(R.id.bookTicketButton);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);

            trackBusTV = (TextView) itemView.findViewById(R.id.trackBusTextView);
        }
    }
}
