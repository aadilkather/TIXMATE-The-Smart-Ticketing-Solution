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
import com.aumento.tixmate.ModelClass.BookingListModelClass;
import com.aumento.tixmate.Utils.GlobalPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TicketHistoryActivity extends AppCompatActivity {

    private static final String TAG = "MyBookingsActivity";
    RecyclerView myBookingTicketRV;
    private String user_id;
    private String getTicketUrl;
    private List<BookingListModelClass> bookingList;
    private BookingHistoryListAdapter listAdapter;

    TextView nodataTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_history);

        GlobalPreference globalPreference = new GlobalPreference(TicketHistoryActivity.this);
        globalPreference.addUID("1");
        user_id = globalPreference.RetriveUID();
        String ip = globalPreference.RetriveIP();
        getTicketUrl = "http://"+ ip + "/tixmate/ticketHistory.php";

        myBookingTicketRV = (RecyclerView) findViewById(R.id.historyTicketRecyclerView);
        myBookingTicketRV.setLayoutManager(new LinearLayoutManager(this));
        myBookingTicketRV.setNestedScrollingEnabled(true);
        myBookingTicketRV.setItemAnimator(new DefaultItemAnimator());

        nodataTV = findViewById(R.id.nodataTV);
        nodataTV.setVisibility(View.GONE);

        loadData();
    }

    private void loadData() {

        bookingList = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, getTicketUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.equals("No data"))
                {
                    nodataTV.setVisibility(View.VISIBLE);
                    myBookingTicketRV.setVisibility(View.GONE);
                }
                else {

                    try {
                        Log.d(TAG, "onResponse: " + response);
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonSourceArray = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jsonSourceArray.length(); i++) {
                            JSONObject sobject = jsonSourceArray.getJSONObject(i);
                            String bus_name = sobject.getString("bus_name");
                            String pickup_point = sobject.getString("pickup_point");
                            String dropoff_point = sobject.getString("dropoff_point");
                            String pickup_time = sobject.getString("pickup_time");
                            String dropoff_time = sobject.getString("dropoff_time");
                            String booking_date = sobject.getString("booking_date");
                            String price = sobject.getString("price");
                            String seats = sobject.getString("seats");

                            bookingList.add(new BookingListModelClass("", "", user_id, bus_name, "", pickup_point, dropoff_point, pickup_time,
                                    dropoff_time, booking_date, "₹" + price, seats, ""));


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    listAdapter = new BookingHistoryListAdapter(TicketHistoryActivity.this, bookingList);
                    myBookingTicketRV.setAdapter(listAdapter);

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<>();
                param.put("user_id",user_id);
                return param;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

}
