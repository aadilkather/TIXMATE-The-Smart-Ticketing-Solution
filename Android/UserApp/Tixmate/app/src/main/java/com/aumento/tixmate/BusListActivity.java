package com.aumento.tixmate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.aumento.tixmate.Adapter.BusListAdapter;
import com.aumento.tixmate.ModelClass.BusDetailsModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BusListActivity extends AppCompatActivity {

    List<BusDetailsModelClass> busList;
    BusListAdapter busListAdapter;
    RecyclerView busListRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_list);

        Intent intent = getIntent();
        String response = intent.getStringExtra("response");

        busListRV = (RecyclerView) findViewById(R.id.busListRecyclerView);
        busListRV.setLayoutManager(new LinearLayoutManager(BusListActivity.this));
        busListRV.setItemAnimator(new DefaultItemAnimator());

        loadBusList(response);
    }

    private void loadBusList(String response) {
        busList = new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("data");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String id = object.getString("bus_id");
                String bus_name = object.getString("bus_name");
                String start_time = object.getString("start_time");
                String end_time = object.getString("arr_time");
                String seats = object.getString("bus_seats");
                String price = object.getString("bus_name");
                String src_location = object.getString("src_location");
                String dest_location = object.getString("dest_location");
                String route_id = object.getString("route_id");

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm");

                String time = null;
                try {
                    Date date1 = simpleDateFormat.parse(start_time);
                    Date date2 = simpleDateFormat.parse(end_time);

                    long difference = date2.getTime() - date1.getTime();
                    int days = (int) (difference / (1000 * 60 * 60 * 24));
                    int hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
                    int min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
                    int sec = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60) / (1000 * 60);
                    hours = (hours < 0 ? -hours : hours);
                    Log.i("======= Hours", days + " :: " + min + " :: " + hours);

                    if(hours != 0)
                        time = hours + "hrs " + min + "min ";
                    else
                        time = min + "min ";

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                busList.add(new BusDetailsModelClass(id, bus_name, start_time, end_time, seats, time, src_location, dest_location,route_id));
            }
            busListAdapter = new BusListAdapter(BusListActivity.this,busList);
            busListRV.setAdapter(busListAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
