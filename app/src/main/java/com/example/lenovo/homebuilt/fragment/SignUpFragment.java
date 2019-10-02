package com.example.lenovo.homebuilt.fragment;


import android.app.ProgressDialog;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.homebuilt.R;
import com.example.lenovo.homebuilt.other.CheckInternet;
import com.example.lenovo.homebuilt.other.ContanstVar;
import com.example.lenovo.homebuilt.other.UDSharePref;

import java.util.HashMap;
import java.util.Map;

public class SignUpFragment extends Fragment implements View.OnClickListener {

    EditText first_name, last_name, email, password, phone_no;
    Button sign_up;
    UDSharePref udSharePref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        initVar(view);
        sign_up.setOnClickListener(this);

        return view;
    }

    private void initVar(View view) {

        first_name = (EditText) view.findViewById(R.id.first_name);
        last_name = (EditText) view.findViewById(R.id.last_name);
        email = (EditText) view.findViewById(R.id.email);
        password = (EditText) view.findViewById(R.id.password);
        phone_no = (EditText) view.findViewById(R.id.mobile);
        sign_up = (Button) view.findViewById(R.id.sign_up);

        udSharePref = new UDSharePref(getContext());
    }

    @Override
    public void onClick(View v) {
        String strFirstName = first_name.getText().toString();
        String strLastName = last_name.getText().toString();
        String strEmail = email.getText().toString();
        String strPassword = password.getText().toString();
        String strPhoneNo = phone_no.getText().toString();

        if (!validate(strFirstName, strLastName, strEmail, strPassword, strPhoneNo)) {
            onSignUpFailed();
        } else {
            onSignUpSuccess(strFirstName, strLastName, strEmail, strPassword, strPhoneNo);
        }
    }

    private boolean validate(String strFirstName, String strLastName, String strEmail, String strPassword, String strPhoneNo) {
        boolean valid = true;

        if (strFirstName.isEmpty()) {
            valid = false;
            Snackbar.make(getActivity().findViewById(android.R.id.content), "First Name is empty", Snackbar.LENGTH_SHORT).show();
        } else if (strLastName.isEmpty()) {
            valid = false;
            Snackbar.make(getActivity().findViewById(android.R.id.content), "Last Name is Empty", Snackbar.LENGTH_SHORT).show();
        } else if (strEmail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(strEmail).matches()) {
            valid = false;
            Snackbar.make(getActivity().findViewById(android.R.id.content), "Email invalid", Snackbar.LENGTH_SHORT).show();
        } else if (strPassword.isEmpty()) {
            valid = false;
            Snackbar.make(getActivity().findViewById(android.R.id.content), "Password is Empty", Snackbar.LENGTH_SHORT).show();
        } else if (strPhoneNo.length() != 10) {
            valid = false;
            Snackbar.make(getActivity().findViewById(android.R.id.content), "Mobile number invalid", Snackbar.LENGTH_SHORT).show();
        }

        return valid;
    }

    private void onSignUpFailed() {

    }

    private void onSignUpSuccess(final String strFirstName, final String strLastName, final String strEmail, final String strPassword, final String strPhoneNo) {
        final ProgressDialog dialog = new ProgressDialog(getContext(), ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        if (CheckInternet.isConnected(getContext())) {
            dialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, ContanstVar.REGISTER, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    dialog.dismiss();
                    successResponse(response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    dialog.dismiss();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put(ContanstVar.NAME, strFirstName + " " + strLastName);
                    params.put(ContanstVar.EMAIL, strEmail);
                    params.put(ContanstVar.CONTACT, strPhoneNo);
                    params.put(ContanstVar.PASSWORD, strPassword);
                    return params;
                }
            };
            RequestQueue queue = Volley.newRequestQueue(getContext());
            queue.add(request);
        }
        else {
            Snackbar.make(getActivity().findViewById(android.R.id.content), "Check internet connection", Snackbar.LENGTH_LONG).show();
        }
    }

    private void successResponse(String response) {
        if (response.matches("0")) {
            Snackbar.make(getActivity().findViewById(android.R.id.content), "Registered Successfully", Snackbar.LENGTH_LONG).show();
            FragmentTransaction ft = getFragmentManager().beginTransaction()
                    .replace(R.id.frame_container, new LoginFragment());
            ft.commit();
        } else {
            Snackbar.make(getActivity().findViewById(android.R.id.content), "Not Register", Snackbar.LENGTH_LONG).show();
        }
    }
}
