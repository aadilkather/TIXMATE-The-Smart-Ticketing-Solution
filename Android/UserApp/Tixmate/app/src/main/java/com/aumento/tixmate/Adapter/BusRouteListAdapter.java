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
import com.aumento.tixmate.ModelClass.BusRouteModelClass;
import com.aumento.tixmate.R;
import com.aumento.tixmate.TicketBookingActivity;
import com.aumento.tixmate.TrackBusLocationActivity;

import java.util.List;

public class BusRouteListAdapter extends RecyclerView.Adapter<BusRouteListAdapter.MyViewHolder> {

    Context mtx;
    List<BusRouteModelClass> busRouteList;

    public BusRouteListAdapter(Context mtx, List<BusRouteModelClass> busRouteList) {
        this.mtx = mtx;
        this.busRouteList = busRouteList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.raw_bus_route, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final BusRouteModelClass lists = busRouteList.get(position);

        int size = getItemCount() - 1;


        holder.stopNameTextView.setText(lists.getStop_name());
        holder.stopTimeTextView.setText(lists.getStop_time());

        if(position == size) {
            holder.stopImageView.getLayoutParams().height = 40;
            holder.stopImageView.getLayoutParams().width = 40;
            ViewGroup.MarginLayoutParams marginParams = new ViewGroup.MarginLayoutParams(holder.stopImageView.getLayoutParams());
            marginParams.setMargins(20, 0, 0, 0);
            holder.stopImageView.setImageDrawable(mtx.getDrawable(R.drawable.destroute));
        }
    }

    @Override
    public int getItemCount() {
        return busRouteList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private ImageView stopImageView;
        private TextView stopNameTextView, stopTimeTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            stopImageView = (ImageView) itemView.findViewById(R.id.stopImageView);
            stopNameTextView = (TextView) itemView.findViewById(R.id.stopNameTextView);
            stopTimeTextView = (TextView) itemView.findViewById(R.id.stopTimeTextView);
        }
    }
}
