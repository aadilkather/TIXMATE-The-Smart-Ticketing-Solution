package com.aumento.tixmate;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aumento.tixmate.Utils.GlobalPreference;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";
    EditText userFirstNameET, userLastNameET, userPhoneNumberET, userEmailET, userUsernameET, userPasswordET;
    TextView submitButtonTV;
    private String ip;
    private String token;
    private TextView getOTPButtonTV;
    private TextView OTPTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        GlobalPreference globalPreference = new GlobalPreference(getApplicationContext());
        ip = globalPreference.RetriveIP();

        init();

        getOTPButtonTV = findViewById(R.id.getOTPButtonTextView);
        getOTPButtonTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mobile = userPhoneNumberET.getText().toString();
                Random rand = new Random();
                token=String.format("%04d", rand.nextInt(10000));

                sendOTP(mobile);

            }
        });


        submitButtonTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String verify_token=OTPTV.getText().toString();

                if(verify_token.equals(token))
                {
                    signUp();
                }
                else{
                    Toast.makeText(SignupActivity.this, "Invalid Otp Code", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void sendOTP(String mobile){

        String API_URL="http://cloudviews.in/SMS/gateway.php?mobile="+ mobile +"&msg="+ this.token;
        StringRequest stringRequest=new StringRequest(Request.Method.GET, API_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(SignupActivity.this, "Message Has Been Send Sucessfully", Toast.LENGTH_SHORT).show();
                getOTPButtonTV.setText("Resend OTP");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SignupActivity.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }

    private void signUp() {


        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ip+"/tixmate/register.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d(TAG, "onResponse: "+response);

                Toast.makeText(SignupActivity.this, ""+response, Toast.LENGTH_SHORT).show();

                if(response.contains("Failed")){
                    Toast.makeText(SignupActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                }
                else {

                    Intent intent = new Intent(SignupActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error);
                Toast.makeText(SignupActivity.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("fname",userFirstNameET.getText().toString());
                params.put("lname",userLastNameET.getText().toString());
                params.put("phone",userPhoneNumberET.getText().toString());
                params.put("email",userEmailET.getText().toString());
                params.put("username",userUsernameET.getText().toString());
                params.put("password",userPasswordET.getText().toString());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(SignupActivity.this);
        requestQueue.add(stringRequest);

    }

    private void init() {

        userFirstNameET = (EditText) findViewById(R.id.userFirstNameTextView);
        userLastNameET = (EditText) findViewById(R.id.userLastNameTextView);
        userPhoneNumberET = (EditText) findViewById(R.id.userPhoneNumberTextView);
        userEmailET = (EditText) findViewById(R.id.userEmailTextView);
        userUsernameET = (EditText) findViewById(R.id.userUsernameTextView);
        userPasswordET = (EditText) findViewById(R.id.userPasswordTextView);
        submitButtonTV = (TextView) findViewById(R.id.submitButtonTextView);
        OTPTV = (TextView) findViewById(R.id.OTPTextView);

    }
}
