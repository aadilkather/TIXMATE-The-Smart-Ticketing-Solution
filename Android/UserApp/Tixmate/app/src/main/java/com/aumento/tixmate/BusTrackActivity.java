package com.aumento.tixmate;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class BusTrackActivity extends AppCompatActivity implements LocationListener {

    private static final String TAG = "BusTrackActivity";

    private LocationManager locationManager;
    private boolean isGPSEnabled = false;
    private boolean isNetworkEnabled = false;
    private boolean isGPSTrackingEnabled = false;
    private String provider_info;
    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_track);


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        provider_info = LocationManager.GPS_PROVIDER;
        provider_info = LocationManager.NETWORK_PROVIDER;
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 10, this);


    }

    @Override
    public void onLocationChanged(Location location) {

        Toast.makeText(this, ""+location.getLatitude()+" "+location.getLongitude(), Toast.LENGTH_SHORT).show();
        loadData(location.getLatitude(), location.getLongitude());

    }

    private void loadData(final double latitude, final double longitude) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://192.168.1.3/tixmate/bus_tracker.php" , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(BusTrackActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onResponse: "+response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("lat", String.valueOf(latitude));
                params.put("lon", String.valueOf(longitude));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
