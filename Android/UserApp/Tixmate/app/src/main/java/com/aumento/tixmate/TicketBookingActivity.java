package com.aumento.tixmate;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aumento.tixmate.Utils.GlobalPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TicketBookingActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "TicketBookingActivity";

    TextView busNameTV, busNoTV, sourceNameTV, startTimeTV, destPlaceTV, stopTimeTV, seatNumberTV, priceTV;
    ImageView itemminus, itemplus;
    Button payBT;

    private String start_name;
    private String dest_name;
    private String route_id;
    private String bus_id;
    private String findPriceUrl;
    private int count = 1;
    private String bookingUrl;
    private int priceSum;
    private int price = 1;
    private String user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_booking);

        GlobalPreference globalPreference = new GlobalPreference(TicketBookingActivity.this);
        String ip = globalPreference.RetriveIP();
        user_id = globalPreference.RetriveUID();

        findPriceUrl = "http://"+ ip + "/tixmate/findPrice.php";
        bookingUrl = "http://"+ ip + "/tixmate/booking.php";

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        bus_id = extras.getString("bus_id");
        start_name = extras.getString("start_name");
        String start_time = extras.getString("start_time");
        dest_name = extras.getString("dest_name");
        String dest_time = extras.getString("dest_time");
        route_id = extras.getString("route_id");

        init();
        loadData();

        sourceNameTV.setText(start_name);
        startTimeTV.setText(start_time);
        destPlaceTV.setText(dest_name);
        stopTimeTV.setText(dest_time);
    }

    private void bookBus() {

        StringRequest addToCartRequest = new StringRequest(Request.Method.POST, bookingUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: bus book: "+response);



                if(response.equals("insufficient Balance"))
                {
                    Toast.makeText(TicketBookingActivity.this, "Insufficient balance. Add money to wallet", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(TicketBookingActivity.this, MyWalletActivity.class);
                    startActivity(intent);
                }
                else if(response.equals("No Wallet Available"))
                {
                    Toast.makeText(TicketBookingActivity.this, "No wallet available. Please create a wallet", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(TicketBookingActivity.this, MyWalletActivity.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(TicketBookingActivity.this, BookingCompletedActivity.class);
                    intent.putExtra("response", response);
                    startActivity(intent);
                    finish();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("bus_id",bus_id);
                params.put("user_id",user_id);
                params.put("seats", seatNumberTV.getText().toString());
                params.put("pickout",start_name);
                params.put("dropoff",dest_name);
                params.put("route_id",route_id);
                params.put("price",priceTV.getText().toString());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(addToCartRequest);

    }

    private void loadData() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, findPriceUrl , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);

                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray array = object.getJSONArray("data");
                    JSONObject jsonObject = array.getJSONObject(0);
                    String busName = jsonObject.getString("busName");
                    String busNo = jsonObject.getString("busNo");
                    String price = jsonObject.getString("price");
                    priceSum = Integer.valueOf(price);
                    busNameTV.setText(busName);
                    busNoTV.setText(busNo);
                    priceTV.setText(price);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("source",start_name);
                params.put("destn",dest_name);
                params.put("bus_id",bus_id);
                params.put("route_id",route_id);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    private void init() {

        busNameTV = (TextView) findViewById(R.id.busNameTextView);
        busNoTV = (TextView) findViewById(R.id.busNoTextView);
        sourceNameTV = (TextView) findViewById(R.id.sourceNameTextView);
        startTimeTV = (TextView) findViewById(R.id.startTimeTextView);
        destPlaceTV = (TextView) findViewById(R.id.destPlaceTextView);
        stopTimeTV = (TextView) findViewById(R.id.stopTimeTextView);
        seatNumberTV = (TextView) findViewById(R.id.seatNumberTextView);
        priceTV = (TextView) findViewById(R.id.priceTextView);
        itemminus = (ImageView) findViewById(R.id.itemminus);
        itemminus.setOnClickListener(this);
        itemplus = (ImageView) findViewById(R.id.itemplus);
        itemplus.setOnClickListener(this);
        payBT = (Button) findViewById(R.id.payButton);
        payBT.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            //Add Quantity button
            case R.id.itemplus:

                count++;
                seatNumberTV.setText(String.valueOf(count));
                price = priceSum * count;
                priceTV.setText(String.valueOf(price));
                break;

            //minus Quantity button
            case R.id.itemminus:
                if(count > 1 ) {
                    price = price - priceSum;
                    count--;
                    seatNumberTV.setText(String.valueOf(count));
                    priceTV.setText(String.valueOf(price));
                }

                break;

            case R.id.payButton:

                bookBus();

                break;
        }

    }

}
