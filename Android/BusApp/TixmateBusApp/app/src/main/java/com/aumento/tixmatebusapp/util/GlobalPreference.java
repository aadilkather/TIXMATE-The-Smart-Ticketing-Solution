package com.aumento.tixmatebusapp.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by MyPc on 09-09-2017.
 */

public class GlobalPreference {


    private SharedPreferences prefs;
    private Context context;
    SharedPreferences.Editor editor;

    public GlobalPreference(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences(Constants.SHARED_PREF, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public void addIP(String ip) {
        editor.putString(Constants.IP, ip);
        editor.apply();

    }
    public String RetriveIP() {
        return prefs.getString(Constants.IP, "");
    }

    public void addBID(String bid) {
        editor.putString(Constants.BID, bid);
        editor.apply();

    }
    public String RetriveBID() {
        return prefs.getString(Constants.BID, "");
    }

    public void addRouteID(String rid) {
        editor.putString(Constants.RID, rid);
        editor.apply();

    }
    public String RetriveRouteID() {
        return prefs.getString(Constants.RID, "");
    }



}
