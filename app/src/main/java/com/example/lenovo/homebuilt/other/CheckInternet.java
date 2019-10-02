package com.example.lenovo.homebuilt.other;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class CheckInternet {
     private static boolean _isConnected = false;

    public static boolean isConnected(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        _isConnected = activeNetInfo != null && activeNetInfo.isConnectedOrConnecting();
        return _isConnected;
    }
}
