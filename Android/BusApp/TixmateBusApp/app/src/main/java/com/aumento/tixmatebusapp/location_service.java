package com.aumento.tixmatebusapp;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aumento.tixmatebusapp.util.GlobalPreference;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class location_service extends Service implements LocationListener {

	GlobalPreference mGlobalPreference;
	private static final String TAG = "location_service";
	Handler m_Handler;

	Runnable mRunnable;

	private String url;
	private String bid;
	private String route_id;
	private LocationManager locationManager;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub

		return null;
	}



	@RequiresApi(api = Build.VERSION_CODES.M)
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub

		mGlobalPreference= new GlobalPreference(getApplicationContext());
		String ip = mGlobalPreference.RetriveIP();
		url = "http://"+ ip +"/tixmate/bus_tracker.php";
		bid = mGlobalPreference.RetriveBID();
		route_id = mGlobalPreference.RetriveRouteID();


		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

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


		m_Handler = new Handler();
		mRunnable = new Runnable(){
			@Override
			public void run() {

				m_Handler.postDelayed(mRunnable, 1000);// move this inside the run method
			}
		};
		mRunnable.run();


	}

	@RequiresApi(api = Build.VERSION_CODES.M)
	@Override
	public void onStart(Intent intent, int startId) {

		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

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

		final Handler handler = new Handler();
		final Runnable r = new Runnable() {
			public void run() {



				handler.postDelayed(this, 3000);
			}
		};
		handler.postDelayed(r,3000);
	}


	private void loadData(final double latitude, final double longitude) {

		final String area = getAddress(latitude,longitude);

		StringRequest stringRequest = new StringRequest(Request.Method.POST, url , new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {

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
				params.put("area", area);
				params.put("bus_id", bid);
				params.put("route_id", route_id);
				return params;
			}
		};

		RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
		requestQueue.add(stringRequest);

	}

	public String getAddress(double lat, double lng) {

		Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
		Address obj = null;
		try {
			List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
			obj = addresses.get(0);
			String add = obj.getAddressLine(0);
//			add = add + "\n" + obj.getSubLocality();

			Log.d(TAG, "sub locality	: " + obj.getSubLocality());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return obj.getSubLocality();
	}


	@Override
	public void onLocationChanged(Location location) {
		loadData(location.getLatitude(), location.getLongitude());
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
