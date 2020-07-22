package com.aumento.tixmatebusapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aumento.tixmatebusapp.util.GlobalPreference;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Button scanButton;
    private IntentIntegrator qrScan;
    private String url;
    private String bus_id;
    private String response;
    private String bookId;
    private PopupWindow window;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GlobalPreference globalPreference = new GlobalPreference(MainActivity.this);
/*        globalPreference.addIP("192.168.43.95");
        globalPreference.addBID("1");
        globalPreference.addRouteID("2");*/


        bus_id = globalPreference.RetriveBID();
        String ip = globalPreference.RetriveIP();
        url = "http://"+ ip +"/tixmate/scanTicket.php";

        Log.d(TAG, "onCreate: "+bus_id+"  "+ip+"   \n "+url);

        Intent in=new Intent(getApplicationContext(), location_service.class);
        startService(in);

        scanButton = (Button) findViewById(R.id.scanButton);
        //intializing scan object
        qrScan = new IntentIntegrator(this);
        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qrScan.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {

            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            }

            else {
                //if qr contains data
                response = result.getContents().toString();
                Log.d(TAG, "onActivityResult: "+response);
//                if(response.contains("{\"bid\""))
                    parseJData(response);
//                else
//                    Toast.makeText(this, "Invalid Data", Toast.LENGTH_SHORT).show();
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void parseJData(String response) {

        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray array = jsonObject.getJSONArray("data");
            JSONObject object = array.getJSONObject(0);
            bookId = object.getString("bid");
            Log.d(TAG, "onActivityResult: "+bookId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        checkTicket();


    }

    public void checkTicket(){

        StringRequest stringRequest = new StringRequest(StringRequest.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MainActivity.this, response, Toast.LENGTH_LONG).show();
                Log.d(TAG, "onResponse: "+response);

                ShowPopupWindow(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("bookId",bookId);
                params.put("bus_id","1");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
    }

    private void ShowPopupWindow(String response) {

        LayoutInflater inflater = (LayoutInflater) MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.popupwindow, null);
        window = new PopupWindow(layout, 600, 600, true);

        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setOutsideTouchable(true);
        window.showAtLocation(layout, Gravity.CENTER, Gravity.CENTER, Gravity.CENTER);
        //  window.showAtLocation(layout, 17, 100, 100);

        ImageView iv = (ImageView) layout.findViewById(R.id.imageView);
        TextView tv = (TextView) layout.findViewById(R.id.textView);

        if(response.contains("Invalid Ticket"))
        {
            iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_cancel_black_24dp));
            tv.setText(response);
        }
        else if(response.contains("valid Ticket"))
        {
            iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_check_circle_black_24dp));
            tv.setText(response);
        }
        else if(response.contains("Already Scaned"))
        {
            iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_do_not_disturb_on_black_24dp));
            tv.setText("Used Ticket");
        }



        ImageView close = (ImageView) layout.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, " button call press ");
                window.dismiss();
            }

        });

        Button ok = (Button) layout.findViewById(R.id.ok);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, " button call press ");
                window.dismiss();
            }

        });

    }

}
