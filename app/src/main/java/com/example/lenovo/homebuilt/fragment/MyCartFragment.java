package com.example.lenovo.homebuilt.fragment;


import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
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
import com.example.lenovo.homebuilt.activity.LoginActivity;
import com.example.lenovo.homebuilt.activity.PaymentActivity;
import com.example.lenovo.homebuilt.adapter.BSInstallationAdapter;
import com.example.lenovo.homebuilt.database.MyCartDb;
import com.example.lenovo.homebuilt.model.BSInstallation;
import com.example.lenovo.homebuilt.other.CheckInternet;
import com.example.lenovo.homebuilt.other.ContanstVar;
import com.example.lenovo.homebuilt.other.UDSharePref;
import com.example.lenovo.homebuilt.util.ProgressDialogBox;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyCartFragment extends Fragment implements IncDescQuantity, View.OnClickListener  {

    TextView cart_price, view_cart;
    ListView repair_service_list;
    ArrayList<BSInstallation> bsInstallationArrayList;
    ArrayList<BSInstallation> selected_bsArrayList;
    ArrayList<String> select_ListItem;
    TextView tv_quantity, tv_quantity_text;
    double actual_service_price;
    int total_quantity = 0;
    static double total_price = 0;
    MyCartDb myCartDb;
    String myLocation,myService;
    UDSharePref sharePref;
    ProgressDialogBox dialog;
    View view ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_cart, container, false);
        initVar(view);
        view_cart.setOnClickListener(this);
        return view;
    }
    private void initVar(View view) {
        repair_service_list = (ListView) view.findViewById(R.id.repair_service_list);
        cart_price = (TextView) view.findViewById(R.id.cart_price);
        tv_quantity_text = (TextView) view.findViewById(R.id.cart_text);
        view_cart = (TextView) view.findViewById(R.id.view_cart);
        bsInstallationArrayList = new ArrayList<>();
        selected_bsArrayList = new ArrayList<>();
        select_ListItem = new ArrayList<>();
        myCartDb = new MyCartDb(getContext());
        dialog = new ProgressDialogBox(getContext());
        sharePref = new UDSharePref(getContext());

        myLocation = getActivity().getIntent().getStringExtra("location");
        myService = getActivity().getIntent().getStringExtra("service");

        fetchServiceList(myService);
    }

    private void fetchServiceList(final String myService) {
        if (CheckInternet.isConnected(getContext())) {
            dialog.showDialog("Please wait...");

            StringRequest request = new StringRequest(Request.Method.POST, ContanstVar.SERVICE_LIST, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    dialog.dismissDialog();
                    showServiceList(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.dismissDialog();
                    Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("service_name", myService);
                    return params;
                }
            };
            RequestQueue queue = Volley.newRequestQueue(getContext());
            queue.add(request);
        } else
            Toast.makeText(getContext(), "Check Internet Connection", Toast.LENGTH_SHORT).show();
    }

    private void showServiceList(String response) {
        try {
            JSONObject serviceJSON = new JSONObject(response);
            JSONArray serviceArray = serviceJSON.getJSONArray(ContanstVar.SERVER_RESPONSE);
            for (int i = 0; i < serviceArray.length(); i++) {
                JSONObject object = serviceArray.getJSONObject(i);
                String service_item = object.getString(ContanstVar.SERVICE_NAME);
                String installation_charge = object.getString(ContanstVar.INSTALLATION_CHARGE);
                String repair_charge = object.getString(ContanstVar.REPAIR_CHARGE);
                if (repair_charge.isEmpty() || installation_charge.isEmpty()) {
                    repair_charge = "0";
                    installation_charge = "0";
                }
                bsInstallationArrayList.add(new BSInstallation(service_item, 0,
                        Double.parseDouble(repair_charge)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (bsInstallationArrayList.isEmpty())
            repair_service_list.setEmptyView(view.findViewById(R.id.emptyElement));
        else
            repair_service_list.setAdapter(new BSInstallationAdapter(getContext(), bsInstallationArrayList, this));

    }

    @Override
    public void onIncQuantity(View v, int position) {
        tv_quantity = (TextView) v.findViewById(R.id.quantity);
        bsInstallationArrayList.get(position).setQuantity(bsInstallationArrayList.get(position).getQuantity() + 1);
        actual_service_price = bsInstallationArrayList.get(position).getPrice();
        total_price = total_price + actual_service_price;
        total_quantity += 1;


        updateCart(total_quantity, total_price, position);
        String strItemName = bsInstallationArrayList.get(position).getName();
        if (select_ListItem.contains(strItemName)) {

            int qty = bsInstallationArrayList.get(position).getQuantity();
            double pr = bsInstallationArrayList.get(position).getQuantity() * bsInstallationArrayList.get(position).getPrice();
            for (int i = 0; i < selected_bsArrayList.size(); i++) {
                if (selected_bsArrayList.get(i).getName().equals(strItemName)) {

                    selected_bsArrayList.get(i).setQuantity(qty);
                    selected_bsArrayList.get(i).setPrice(pr);

                    myCartDb.updateCart(selected_bsArrayList.get(i).getName(), qty, pr);

                }
            }

        } else {
            select_ListItem.add(bsInstallationArrayList.get(position).getName());
            selected_bsArrayList.add(new BSInstallation(bsInstallationArrayList.get(position).getName(),
                    bsInstallationArrayList.get(position).getQuantity(),
                    bsInstallationArrayList.get(position).getPrice()));

            myCartDb.addData(bsInstallationArrayList.get(position).getName()
                    , bsInstallationArrayList.get(position).getQuantity(),
                    bsInstallationArrayList.get(position).getPrice());
        }
    }

    @Override
    public void onDescQuantity(View v, int position) {
        tv_quantity = (TextView) v.findViewById(R.id.quantity);

        if (bsInstallationArrayList.get(position).getQuantity() <= 0) {
            Toast.makeText(getContext(), "Quantity would not less than zero", Toast.LENGTH_SHORT).show();
        } else {
            bsInstallationArrayList.get(position).setQuantity(bsInstallationArrayList.get(position).getQuantity() - 1);
            actual_service_price = bsInstallationArrayList.get(position).getPrice();
            total_price = total_price - actual_service_price;
            total_quantity -= 1;

            String strItemName = bsInstallationArrayList.get(position).getName();
            for (int i = 0; i < selected_bsArrayList.size(); i++) {
                if (selected_bsArrayList.get(i).getName().equals(strItemName)) {
                    int qty = selected_bsArrayList.get(i).getQuantity() - 1;
                    double pr = selected_bsArrayList.get(i).getPrice() - bsInstallationArrayList.get(position).getPrice();
                    selected_bsArrayList.get(i).setQuantity(qty);
                    selected_bsArrayList.get(i).setPrice(pr);

                    myCartDb.updateCart(bsInstallationArrayList.get(position).getName()
                            , bsInstallationArrayList.get(position).getQuantity(),
                            bsInstallationArrayList.get(position).getPrice());
                }
            }

            updateCart(total_quantity, total_price, position);
        }
    }

    private void updateCart(int total_quantity, double total_price, int position) {
        tv_quantity_text.setText(String.valueOf(total_quantity + " ITEMS IN CARTS"));
        tv_quantity.setText(String.valueOf(bsInstallationArrayList.get(position).getQuantity()));
        cart_price.setText(String.valueOf(total_price));

        if (bsInstallationArrayList.get(position).getQuantity() == 0) {
            String strItemName = bsInstallationArrayList.get(position).getName();

            for (int i = 0; i < selected_bsArrayList.size(); i++) {
                String selectedItem = selected_bsArrayList.get(i).getName();
                if (selected_bsArrayList.get(i).getName().equals(strItemName)) {
                    myCartDb.deleteParticularCart(selectedItem);
                    select_ListItem.remove(i);
                    selected_bsArrayList.remove(i);
                }
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (sharePref.getStatus()) {
            Intent intent = new Intent(getContext(), PaymentActivity.class);
            intent.putExtra("service",myService);
            intent.putExtra("location", myLocation);
            intent.putExtra("service_type","repair");
            startActivity(intent);
        }
        else {
            startActivity(new Intent(getContext(), LoginActivity.class));
            getActivity().finish();
        }
    }
}
