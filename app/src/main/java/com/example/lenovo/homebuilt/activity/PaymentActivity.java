package com.example.lenovo.homebuilt.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.homebuilt.IncDescQuantity;
import com.example.lenovo.homebuilt.R;
import com.example.lenovo.homebuilt.adapter.BSInstallationAdapter;
import com.example.lenovo.homebuilt.database.MyCartDb;
import com.example.lenovo.homebuilt.model.BSInstallation;
import com.example.lenovo.homebuilt.other.CheckInternet;
import com.example.lenovo.homebuilt.other.ContanstVar;
import com.example.lenovo.homebuilt.other.UDSharePref;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity implements IncDescQuantity, View.OnClickListener {

    private final String TAG = PaymentActivity.class.getSimpleName();
    Toolbar toolbar;
    ListView cartListView;
    TextView name, contact, address, view_cart, delivery_time, delivery_date,service_id, service_name;
    MyCartDb myCartDb;
    UDSharePref sharePref;
    ArrayList<BSInstallation> cartArrayList;
    BSInstallationAdapter adapter;
    String myLocation, myService,myServiceType,strEmail, strName, strContact, strDeliveryDate, strDeliveryTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        initVar();

        view_cart.setOnClickListener(this);
        delivery_time.setOnClickListener(this);
        delivery_date.setOnClickListener(this);

    }

    private void initVar() {
      toolbar = (Toolbar) findViewById(R.id.toolbar);
        cartListView = (ListView) findViewById(R.id.cartListView);
        name = (TextView) findViewById(R.id.name);
        view_cart = (TextView) findViewById(R.id.view_cart);
        contact = (TextView) findViewById(R.id.contact);
        address = (TextView) findViewById(R.id.address);
        delivery_date = (TextView) findViewById(R.id.delivery_date);
        delivery_time = (TextView) findViewById(R.id.delivery_time);
        service_id = (TextView) findViewById(R.id.service_id);
        service_name = (TextView) findViewById(R.id.service_name);
        myCartDb = new MyCartDb(this);
        cartArrayList = new ArrayList<>();
        sharePref = new UDSharePref(this);

        toolbar.setTitle("Cart");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        strEmail = sharePref.getEmail();
        strName = sharePref.getName();
        strContact = sharePref.getPhoneNo();
        myLocation = getIntent().getStringExtra("location");
        myLocation = getIntent().getStringExtra("location");
        myService = getIntent().getStringExtra("service");
        myServiceType = getIntent().getStringExtra("service_type");


        displayUserDetail(strName, strContact, myLocation,myService,myServiceType);
        displayCartList();
    }

    private void displayUserDetail(String strName, String strContact, String strLocation,String strServiceId, String strServiceName) {
        name.setText(strName);
        contact.setText(strContact);
        address.setText(strLocation);
        service_id.setText(strServiceId);
        service_name.setText(strServiceName);

    }

    private void displayCartList() {
        cartArrayList = myCartDb.fetchCartList();
        adapter = new BSInstallationAdapter(this, cartArrayList, this);
        cartListView.setAdapter(adapter);
    }

    @Override
    public void onIncQuantity(View v, int position) {
        Toast.makeText(this, " inc called", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDescQuantity(View v, int position) {
        Toast.makeText(this, " desc called", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.view_cart:
                showViewCart();
                break;

            case R.id.delivery_date:
                pickDeliveryDate();
                break;

            case R.id.delivery_time:
                pickDeliveryTime();
                break;
        }


    }

    private void pickDeliveryTime() {
        Calendar mCurrentTime = Calendar.getInstance();
        int hour = mCurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mCurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(PaymentActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                if (selectedHour >= 12) {
                    selectedHour -= 12;
                    delivery_time.setText(selectedHour + " : " + selectedMinute + " PM");

                } else
                    delivery_time.setText(selectedHour + " : " + selectedMinute + " AM");
                strDeliveryTime = delivery_time.getText().toString();
            }
        }, hour, minute, false);//Yes 24 hour time
        mTimePicker.show();
    }

    private void pickDeliveryDate() {

        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog;
        String myFormat = "MM/dd/yy";
        final SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myCalendar.set(year, monthOfYear, dayOfMonth, 0, 0);
                delivery_date.setText(sdf.format(myCalendar.getTime()));
                strDeliveryDate = delivery_date.getText().toString();
            }

        }, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }


    private void showViewCart() {
        String deliveryDate = delivery_date.getText().toString();
        String deliveryTime = delivery_time.getText().toString();

        if (validate(deliveryDate, deliveryTime)) {
            String finalCartList;
            if (!cartArrayList.isEmpty()) {
                finalCartList = makeCartJSON(cartArrayList);
                Log.d(TAG, finalCartList);
                UploadNewBooking(finalCartList);
            } else
                Toast.makeText(this, "No Item in Cart", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validate(String deliveryDate, String deliveryTime) {
        boolean valid = true;

        if (deliveryDate.isEmpty()) {
            valid = false;
            delivery_date.setHintTextColor(Color.RED);
        } else if (deliveryTime.isEmpty()) {
            valid = false;
            delivery_time.setHintTextColor(Color.RED);
        }
        return valid;
    }

    private String makeCartJSON(ArrayList<BSInstallation> cartArrayList) {
        JSONArray cartJSON = new JSONArray();
        JSONObject itemJson;
        for (int i = 0; i < cartArrayList.size(); i++) {
            itemJson = new JSONObject();
            try {
                itemJson.put("service_name", cartArrayList.get(i).getName());
                itemJson.put("quantity", cartArrayList.get(i).getQuantity());
                itemJson.put("price", cartArrayList.get(i).getPrice());

            } catch (JSONException e) {
                e.printStackTrace();
            }
            cartJSON.put(itemJson);
        }

        JSONObject final_object = new JSONObject();
        try {
            final_object.put("email",strEmail);
            final_object.put("user_name", strName);
            final_object.put("contact", strContact);
            final_object.put("location", myLocation);
            final_object.put("service", myService);
            final_object.put("service_type", myServiceType);
            final_object.put("delivery_date", strDeliveryDate);
            final_object.put("delivery_time", strDeliveryTime);
            final_object.put("cartList", cartJSON);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("final_object ", final_object.toString());
        return final_object.toString();
    }

    private void UploadNewBooking(final String finalCartList) {
        final ProgressDialog dialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        if (CheckInternet.isConnected(this)) {
            dialog.show();

            StringRequest request = new StringRequest(Request.Method.POST, ContanstVar.NEW_BOOKING, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    dialog.dismiss();
                    myCartDb.deleteAllData();
                    view_cart.setEnabled(false);
                    openConfirmationDialog();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(PaymentActivity.this,error.toString(), Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
                    params.put("new_booking",finalCartList);

                    return params;
                }
            };
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);

        } else {
            Toast.makeText(this, " Check Internet Connection ", Toast.LENGTH_SHORT).show();
        }
    }

    private void openConfirmationDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialog_view = inflater.inflate(R.layout.booking_confirmation_dialog, null);
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.setCancelable(true);
        dialog.setView(dialog_view);

        dialog_view.findViewById(R.id.view_order).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
