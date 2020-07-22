package com.aumento.tixmate;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
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
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BookingCompletedActivity extends AppCompatActivity {

    private static final String TAG = "BookingCompletedActivit";
    private static final String CHANNEL_ID = "channel_1";

    private ImageView imageView;
    private TextView ticketsourceNameTextView, ticketdestPlaceTextView, ticketpricTextView, ticketbusNameTextView, ticketbusNoTextView, ticketseatsTextView, ticketpassangerNameTextView, ticketTimeTextView, ticketDestinationTextView;

    private String detailsUrl;
    private String bus_id;
    private String user_id;
    private String bus_name;
    private String pickup;
    private String pickup_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_completed);

        createNotificationChannel();

        GlobalPreference globalPreference = new GlobalPreference(BookingCompletedActivity.this);
        String ip = globalPreference.RetriveIP();
        user_id = globalPreference.RetriveUID();

        detailsUrl = "http://"+ ip +"/tixmate/bookedTicket.php";
        init();
        imageView = findViewById(R.id.imageView);

        Intent intent = getIntent();
        if (intent.hasExtra("response"))
        {
            String response = intent.getStringExtra("response");
            loadData(response);
            loadDetails(bus_id, user_id);
        }
        else {

            Bundle b = intent.getExtras();
            String bid = b.getString("bid");
            bus_id = b.getString("bus_id");
            user_id = b.getString("user_id");
            String seats = b.getString("seats");
            String pickup = b.getString("pickup");
            String pickup_time = b.getString("pickup_time");
            String dropoff = b.getString("dropoff");
            String price = b.getString("price");
            String bdate = b.getString("bdate");
            String status = b.getString("status");

//            Toast.makeText(this, ""+pickup_time, Toast.LENGTH_SHORT).show();

            Log.d(TAG, "onCreate: "+ pickup_time);

            String ptime = null;
            try {
                DateFormat f1 = new SimpleDateFormat("HH:mm:ss"); //HH for hour of the day (0 - 23)
                Date d = f1.parse(pickup_time);
                DateFormat f2 = new SimpleDateFormat("h:mm a");
                ptime = f2.format(d).toLowerCase();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            ticketsourceNameTextView.setText(pickup);
            ticketdestPlaceTextView.setText(dropoff);
            ticketDestinationTextView.setText(dropoff);
            ticketseatsTextView.setText(seats);
            ticketpricTextView.setText("₹ " + price);
            ticketTimeTextView.setText(ptime);

            String qrData = "{\"data\":[{\"bid\":\"" + bid + "\"," +
                    "\"bus_id\":\"" + bus_id + "\"," +
                    "\"user_id\":\"" + user_id + "\"," +
                    "\"seats\":\"" + seats + "\"," +
                    "\"pickup\":\"" + pickup + "\"," +
                    "\"dropoff\":\"" + dropoff + "\"," +
                    "\"price\":\"" + price + "\"," +
                    "\"bdate\":\"" + bdate + "\"," +
                    "\"status\":\"" + status + "\"" +
                    "}]}";

            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            try {
                BitMatrix bitMatrix = multiFormatWriter.encode(qrData, BarcodeFormat.QR_CODE, 400, 400);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                imageView.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }

            loadDetails(bus_id, user_id);

//            setNotification(pickup_time);
        }

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void init() {

        ticketpassangerNameTextView = (TextView) findViewById(R.id.ticketpassangerNameTextView);
        ticketDestinationTextView = (TextView) findViewById(R.id.ticketDestinationTextView);
        ticketsourceNameTextView = (TextView) findViewById(R.id.ticketsourceNameTextView);
        ticketdestPlaceTextView = (TextView) findViewById(R.id.ticketdestPlaceTextView);
        ticketbusNameTextView = (TextView) findViewById(R.id.ticketbusNameTextView);
        ticketbusNoTextView = (TextView) findViewById(R.id.ticketbusNoTextView);
        ticketseatsTextView = (TextView) findViewById(R.id.ticketseatsTextView);
        ticketTimeTextView = (TextView) findViewById(R.id.ticketTimeTextView);
        ticketpricTextView = (TextView) findViewById(R.id.ticketpricTextView);
    }

    private void loadData(String response) {

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray array = jsonObject.getJSONArray("data");
            JSONObject object = array.getJSONObject(0);
            String bid = object.getString("id");
            bus_id = object.getString("bus_id");
            user_id = object.getString("user_id");
            String seats = object.getString("seats");
            pickup = object.getString("pickup");
            pickup_time = object.getString("pickup_time");
            String dropoff = object.getString("dropoff");
            String price = object.getString("price");
            String status = object.getString("status");
            String bdate = object.getString("bdate");
            String btime = object.getString("btime");

            setNotification(pickup_time,pickup);

            Log.d(TAG, "loadData: "+pickup_time);

            ticketsourceNameTextView.setText(pickup);
            ticketdestPlaceTextView.setText(dropoff);
            ticketDestinationTextView.setText(dropoff);
            ticketseatsTextView.setText(seats);
            ticketTimeTextView.setText(pickup_time);
            ticketpricTextView.setText("Rs "+price);

            String qrData = " {\"bid\":\""+ bid +"\"," +
                    "\"bus_id\":\""+ bus_id +"\"," +
                    "\"user_id\":\""+ user_id +"\"," +
                    "\"seats\":\""+ seats +"\"," +
                    "\"pickup\":\""+ pickup +"\"," +
                    "\"dropoff\":\""+ dropoff +"\"," +
                    "\"price\":\""+ price +"\"," +
                    "\"bdate\":\""+ bdate +"\"," +
                    "\"status\":\""+ status +"\"," +
                    "}";

            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            try {
                BitMatrix bitMatrix = multiFormatWriter.encode(qrData, BarcodeFormat.QR_CODE,200,200);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                imageView.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setNotification(String pickup_time, String pickup) {

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy");
        String formattedDate = df.format(c);

        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy, HH:mm");
        formatter.setLenient(false);

        Date curDate = new Date();
        long curMillis = curDate.getTime();
        String curTime = formatter.format(curDate);

        Log.d(TAG, "setNotification: "+formattedDate+", "+pickup_time);
        String oldTime = formattedDate+", "+pickup_time;

        Log.d(TAG, "setNotification: "+oldTime);
        Date oldDate = null;
        try {
            oldDate = formatter.parse(oldTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long oldMillis = oldDate.getTime()- 30 * 60 * 1000;

        String hrs = null;
        try {
            SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
            SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
            Date _24HourDt = _24HourSDF.parse(pickup_time);
            hrs = _12HourSDF.format(_24HourDt);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        final int id = (int) System.currentTimeMillis();
        Intent intent = new Intent(BookingCompletedActivity.this, Notification.class);
        intent.putExtra("title",bus_name);
        intent.putExtra("msg","Your "+ bus_name + " Bus will arrive at "+pickup+" at "+hrs);
        PendingIntent p1 = PendingIntent.getBroadcast(getApplicationContext(), id, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager a = (AlarmManager) getSystemService(ALARM_SERVICE);
        a.set(AlarmManager.RTC, oldMillis, p1);
//        a.setRepeating(AlarmManager.RTC, oldMillis, AlarmManager.INTERVAL_DAY, p1);

    }


    private void loadDetails(final String bus_id, final String user_id) {

        StringRequest addToCartRequest = new StringRequest(Request.Method.POST, detailsUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, "onResponse: "+response);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray array = jsonObject.getJSONArray("data");
                    JSONObject object = array.getJSONObject(0);
                    bus_name = object.getString("bus_name");
                    String bus_no = object.getString("bus_no");
                    String user_name = object.getString("user_name");

                    ticketbusNameTextView.setText(bus_name);
                    ticketbusNoTextView.setText(bus_no);
                    ticketpassangerNameTextView.setText(user_name);

                } catch (JSONException e) {
                    e.printStackTrace();
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
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(addToCartRequest);

    }
}
