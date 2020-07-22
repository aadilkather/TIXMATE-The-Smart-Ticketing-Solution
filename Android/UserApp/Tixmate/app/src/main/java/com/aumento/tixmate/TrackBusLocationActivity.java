package com.aumento.tixmate;

import androidx.fragment.app.FragmentActivity;

import android.location.Location;
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
import com.aumento.tixmate.Utils.GlobalPreference;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TrackBusLocationActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "TrackBusLocationActivit";

    private GoogleMap mMap;
    private String getBusLocation;
    private String bus_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GlobalPreference globalPreference = new GlobalPreference(TrackBusLocationActivity.this);
        String ip = globalPreference.RetriveIP();

        getBusLocation = "http://"+ ip +"/tixmate/getBusLocation.php";
//        getBusLocation = "http://192.168.1.3/tixmate/getBusLocation.php";

        bus_id = getIntent().getStringExtra("bus_id");

        setContentView(R.layout.activity_track_bus_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        loadData();
    }

    private void loadData() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, getBusLocation , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);

                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray array = object.getJSONArray("data");
                    JSONObject jsonObject = array.getJSONObject(0);
                    double latitude = jsonObject.getDouble("latitude");
                    double longitude = jsonObject.getDouble("longitude");

                    mMap.clear();

                    LatLng latLng = new LatLng(latitude,longitude);
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14.0f));
                    mMap.addMarker( new MarkerOptions().position(latLng));


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
                params.put("bus_id",bus_id);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        googleMap.getUiSettings();
        googleMap.setMyLocationEnabled(true);

        googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                Toast.makeText(TrackBusLocationActivity.this, ""+location.getLatitude()+" "+location.getLongitude(), Toast.LENGTH_SHORT).show();
                loadData();
            }
        });

    }
}
