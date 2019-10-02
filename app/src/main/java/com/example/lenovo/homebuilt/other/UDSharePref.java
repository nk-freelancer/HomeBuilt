package com.example.lenovo.homebuilt.other;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class UDSharePref  {
    private final String TAG = UDSharePref.class.getSimpleName();
    private Context mContext;
    private SharedPreferences sp ;
    private SharedPreferences.Editor editor;

    public UDSharePref(Context mContext) {
        this.mContext = mContext;
        sp = this.mContext.getSharedPreferences(ContanstVar.USER_DATA_FILE,Context.MODE_PRIVATE);
    }

    public void storeUserData(String email,String name,String phone,boolean status) {
        editor = sp.edit();
        editor.putString(ContanstVar.EMAIL,email);
        editor.putString(ContanstVar.NAME,name);
        editor.putString(ContanstVar.CONTACT,phone);
        editor.putBoolean(ContanstVar.STATUS,status);
        editor.apply();
        Log.d(TAG,"stored");
    }

    public String getName() {
        return sp.getString(ContanstVar.NAME,"HomBuilt.com");
    }

    public String getEmail() {
        return sp.getString(ContanstVar.EMAIL,"");
    }

    public String getPhoneNo() {
        return sp.getString(ContanstVar.CONTACT,"");
    }

    public boolean getStatus() {
        return sp.getBoolean(ContanstVar.STATUS,false);
    }

    public void resetLogin(){
        editor = sp.edit();
        editor.clear();
        editor.apply();
    }


}
