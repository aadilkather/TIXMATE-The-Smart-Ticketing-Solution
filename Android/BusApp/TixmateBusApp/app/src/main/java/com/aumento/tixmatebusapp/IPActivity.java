package com.aumento.tixmatebusapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aumento.tixmatebusapp.util.GlobalPreference;

public class IPActivity extends AppCompatActivity {

    private GlobalPreference mGlobalPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip);

        mGlobalPreference= new GlobalPreference(getApplicationContext());
        getIP();
    }

    public void getIP(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("    Enter Your IP Address    ");

        final EditText input = new EditText(IPActivity.this);
        input.setText(mGlobalPreference.RetriveIP());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        input.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), "Text entered is " + input.getText().toString(), Toast.LENGTH_SHORT).show();

                mGlobalPreference.addIP(input.getText().toString());

                input.setText(input.getText().toString());
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();

            }
        });
        builder.show();

    }

}
