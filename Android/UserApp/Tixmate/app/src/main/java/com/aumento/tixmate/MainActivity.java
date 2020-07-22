package com.aumento.tixmate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private CardView myTicketTV, profileV, walletCV;
    private Button seachBT;
    private EditText dateET;
    private AutoCompleteTextView destinationPlaceET;
    private AutoCompleteTextView sourcePlaceET;

    private List<String> sourcePlaces;
    private List<String> destnPlaces;
    private String findBusUrl;
    private String findDestUrl;
    private String findPlacesUrl;
    private int f =0;

    private ProgressDialog progressDialog;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GlobalPreference globalPreference = new GlobalPreference(MainActivity.this);
        String ip = globalPreference.RetriveIP();
        findBusUrl = "http://"+ ip + "/tixmate/findBus.php";
        findDestUrl = "http://"+ ip + "/tixmate/get_destination.php";
        findPlacesUrl = "http://"+ ip + "/tixmate/getPlaces.php";

        init();

        loadDatas();

        ArrayAdapter<String> destnAdapter = new ArrayAdapter<String>(this,
                android.R.layout.select_dialog_item, destnPlaces);

        destinationPlaceET.setThreshold(2);
        destinationPlaceET.setAdapter(destnAdapter);

        sourcePlaceET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Toast.makeText(MainActivity.this, ""+sourcePlaceET.getText().toString(), Toast.LENGTH_SHORT).show();

                for(int i = 0 ; i < sourcePlaces.size(); i++)
                {
                    Log.d(TAG, "afterTextChanged: "+i);

                    if(sourcePlaceET.getText().toString().equals(sourcePlaces.get(i))){
                        progressDialog = ProgressDialog.show(MainActivity.this, "Please wait", null, true, true);
                        loadDestination();
                    }
                }

                Log.d(TAG, "afterTextChanged: *******************************************");

            }
        });


        dateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,int monthOfYear, int dayOfMonth) {
                        dateET.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        seachBT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findBus();
            }
        });

    }

    private void findBus() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, findBusUrl , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);

                Intent intent = new Intent(MainActivity.this,BusListActivity.class);
                intent.putExtra("response",response);
                startActivity(intent);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("source",sourcePlaceET.getText().toString());
                params.put("destn",destinationPlaceET.getText().toString());
                params.put("sdate",dateET.getText().toString());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void loadDestination() {

        destnPlaces = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, findDestUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();


                try {
                    Log.d(TAG, "onResponse: dest "+response);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonSourceArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonSourceArray.length(); i++)
                    {
                        JSONObject sobject = jsonSourceArray.getJSONObject(i);
                        String dest = sobject.getString("stop_name");

                        Log.d(TAG, "onResponse: destn "+ destnPlaces.size());

                        if ( destnPlaces.size() != 0) {
                            for (int j = 0; j < destnPlaces.size(); j++) {
                                if (destnPlaces.get(j).equals(dest))
                                    f = 1;
                            }

                            if( f != 1) {
                                destnPlaces.add(dest);
                                f = 0;
                                Log.d(TAG, "destn place: " + dest);

                            }
                        }
                        else{
                            destnPlaces.add(dest);
                            f = 0;
                        }

                        Log.d(TAG, "onCreate: " + sobject.getString("stop_name"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                ArrayAdapter<String> destnAdapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.select_dialog_item, destnPlaces);

                destinationPlaceET.setThreshold(2);
                destinationPlaceET.setAdapter(destnAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<>();
                param.put("source",sourcePlaceET.getText().toString());
                return param;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    private void loadDatas() {

        sourcePlaces = new ArrayList<>();
        destnPlaces = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, findPlacesUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.d(TAG, "onResponse: "+response);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonSourceArray = jsonObject.getJSONArray("places");

                    Log.d(TAG, "JSON SIZE: "+jsonSourceArray.length());

                    for (int i = 0; i < jsonSourceArray.length(); i++)
                    {
                        JSONObject sobject = jsonSourceArray.getJSONObject(i);
                        String source = sobject.getString("stop_name");

                        Log.d(TAG, "onResponse: "+sourcePlaces.size());
                        Log.d(TAG, "Stop Name: "+source);


                        if ( sourcePlaces.size() != 0) {
                            for (int j = 0; j < sourcePlaces.size(); j++) {
                                Log.d(TAG, "checking: "+ sourcePlaces.get(j)+" != " +source );
                                if (sourcePlaces.get(j).equals(source)) {
                                    f = 1;
                                    Log.d(TAG, "checking: "+" F = 1" );
                                }
                            }

                            Log.d(TAG, "checking: "+"f =" + f );
                            Log.d(TAG, "checking: "+"========================================================================" );

                            if( f != 1) {
                                sourcePlaces.add(source);
                                f = 0;
                                Log.d(TAG, "source place: " + source);

                            }
                        }
                        else{
                             sourcePlaces.add(source);
                        }

                        f =0;

                    }

                    sourcePlaces.add("Fortkochi");

                    ArrayAdapter<String> sourceAdapter = new ArrayAdapter<String>(getApplicationContext(),
                            android.R.layout.select_dialog_item, sourcePlaces);

                    sourcePlaceET.setThreshold(2);
                    sourcePlaceET.setAdapter(sourceAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);

    }

    private void init() {
        seachBT = (Button) findViewById(R.id.searchButton);
        sourcePlaceET = (AutoCompleteTextView) findViewById(R.id.sourcePlaceEditText);
        destinationPlaceET = (AutoCompleteTextView) findViewById(R.id.destinationPlaceEditText);
        dateET = (EditText) findViewById(R.id.dateEditText);

        myTicketTV = (CardView) findViewById(R.id.myTicketTV);
        myTicketTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this,MyBookingsActivity.class);
                startActivity(intent);
            }
        });

        profileV = (CardView) findViewById(R.id.profileV);
        profileV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this,ProfileActivity.class);
                startActivity(intent);
            }
        });

        CardView ticketHistCV = (CardView) findViewById(R.id.ticketHistCV);
        ticketHistCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this,TicketHistoryActivity.class);
                startActivity(intent);
            }
        });

        CardView walletCV = (CardView) findViewById(R.id.walletCV);
        walletCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this,MyWalletActivity.class);
                startActivity(intent);
            }
        });
    }

}
