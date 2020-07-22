package com.aumento.tixmate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aumento.tixmate.Adapter.TransactionListAdapter;
import com.aumento.tixmate.ModelClass.TransactionListModelClass;
import com.aumento.tixmate.Utils.GlobalPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyWalletActivity extends AppCompatActivity {

    private static final String TAG = "MyWalletActivity";

    private TextView walletAmountMainTV, walletAmountTV, walletStatusTV;
    private LinearLayout addMoneyLL;
    private RecyclerView transactionRV;
    private String ip;

    List<TransactionListModelClass> transactionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);

        GlobalPreference globalPreference = new GlobalPreference(MyWalletActivity.this);
        ip = globalPreference.RetriveIP();

        init();

        addMoneyLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MyWalletActivity.this,PaymentActivity.class);
                startActivity(intent);
            }
        });

        loadData();
    }

    private void loadData() {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ip+"/tixmate/getTransactionList.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                transactionList = new ArrayList<>();

                Log.d(TAG, "onResponse: "+response);

                if(response.contains("No wallet"))
                {
                    walletStatusTV.setVisibility(View.VISIBLE);
                    walletStatusTV.setText("Please add money to activate your wallet");
                }
                else {
                    walletStatusTV.setVisibility(View.GONE);

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray array = jsonObject.getJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            String id = object.getString("id");
                            String trans_num = object.getString("trans_num");
                            String bus_name = object.getString("bus_name");
                            String stop_name = object.getString("stop_name");
                            String amount = object.getString("amount");
                            String tdate = object.getString("tdate");
                            String status = object.getString("status");

                            transactionList.add(new TransactionListModelClass(id, trans_num, bus_name, stop_name, amount, tdate, status));
                        }

                        TransactionListAdapter adapter = new TransactionListAdapter(MyWalletActivity.this, transactionList);
                        transactionRV.setAdapter(adapter);

                        JSONArray jsonArray = jsonObject.getJSONArray("wallet");
                        JSONObject walobject = jsonArray.getJSONObject(0);
                        String amount = walobject.getString("amount");
                        if (Integer.parseInt(amount) == 0)
                            walletStatusTV.setVisibility(View.VISIBLE);
                        else
                            walletStatusTV.setVisibility(View.GONE);
                        walletAmountMainTV.setText("₹ " + amount);
                        walletAmountTV.setText("₹ " + amount);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: "+error);
                Toast.makeText(MyWalletActivity.this, ""+error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();
                params.put("uid","1");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(MyWalletActivity.this);
        requestQueue.add(stringRequest);

    }

    private void init() {

        walletAmountMainTV = (TextView) findViewById(R.id.walletAmountMainTextView);
        walletAmountTV = (TextView) findViewById(R.id.walletAmountTextView);
        walletStatusTV = (TextView) findViewById(R.id.walletStatusTextView);
        addMoneyLL = (LinearLayout) findViewById(R.id.addMoneyLinearLayout);

        transactionRV = (RecyclerView) findViewById(R.id.transactionRecyclerView);
        transactionRV.setLayoutManager(new LinearLayoutManager(this));
        transactionRV.setItemAnimator(new DefaultItemAnimator());
        transactionRV.setNestedScrollingEnabled(true);

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        loadData();
    }


}
