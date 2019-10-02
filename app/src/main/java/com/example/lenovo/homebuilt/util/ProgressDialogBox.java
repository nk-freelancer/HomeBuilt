package com.example.lenovo.homebuilt.util;


import android.app.ProgressDialog;
import android.content.Context;

public class ProgressDialogBox {

    private ProgressDialog dialog;
    private Context context;

    public ProgressDialogBox(Context context) {
        this.context = context;
    }

    public void showDialog(String message){
        dialog = new ProgressDialog(context,ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(message);
        dialog.setCancelable(false);
        dialog.show();
    }

    public void dismissDialog(){
        dialog.dismiss();
    }
}
