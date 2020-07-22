package com.aumento.tixmate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aumento.tixmate.Adapter.BookingHistoryListAdapter;
import com.aumento.tixmate.Adapter.BusRouteListAdapter;
import com.aumento.tixmate.ModelClass.BookingListModelClass;
import com.aumento.tixmate.ModelClass.BusRouteModelClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BusRouteActivity extends AppCompatActivity {

    private static final String TAG = "BusRouteActivity";
    RecyclerView busRouteRV;
    List<BusRouteModelClass> busRouteList;
    private BusRouteListAdapter listAdapter;
    private TextView stopNameTV;
    private TextView stopTimeTV;
    private String route_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_route);

        route_id = getIntent().getStringExtra("route_id");

        init();

        loadData();
    }

    private void loadData() {

        busRouteList = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.43.95/tixmate/getBusRoute.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                    try {
                        Log.d(TAG, "onResponse: " + response);
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonSourceArray = jsonObject.getJSONArray("data");
                        int i;
                        for (i = 0; i < jsonSourceArray.length(); i++) {
                            JSONObject sobject = jsonSourceArray.getJSONObject(i);
                            String id = sobject.getString("id");
                            String stop_name = sobject.getString("stop_name");
                            String stop_time = sobject.getString("time");

                            busRouteList.add(new BusRouteModelClass(id,stop_name,stop_time));
                        }

                       /* JSONObject sobject = jsonSourceArray.getJSONObject(i);
                        String sname = sobject.getString("stop_name");
                        String stime = sobject.getString("time");

                        stopNameTV.setText(sname);
                        stopTimeTV.setText(stime);*/

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    listAdapter = new BusRouteListAdapter(BusRouteActivity.this, busRouteList);
                    busRouteRV.setAdapter(listAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<>();
                param.put("route_id",route_id);
                return param;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    private void init() {

        busRouteRV = (RecyclerView) findViewById(R.id.busRouteRecyclerView);
        busRouteRV.setLayoutManager(new LinearLayoutManager(this));
        busRouteRV.setItemAnimator(new DefaultItemAnimator());
        busRouteRV.setNestedScrollingEnabled(true);

        stopNameTV = (TextView) findViewById(R.id.stopNameTextView);
        stopTimeTV= (TextView) findViewById(R.id.stopTimeTextView);

    }
}
