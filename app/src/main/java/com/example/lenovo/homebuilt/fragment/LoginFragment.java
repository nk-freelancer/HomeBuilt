package com.example.lenovo.homebuilt.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.lenovo.homebuilt.R;
import com.example.lenovo.homebuilt.activity.HomeScreenActivity;

import com.example.lenovo.homebuilt.other.CheckInternet;
import com.example.lenovo.homebuilt.other.ContanstVar;
import com.example.lenovo.homebuilt.other.UDSharePref;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginFragment extends Fragment implements View.OnClickListener {

    private final String TAG = LoginFragment.class.getSimpleName();
    LoginButton facebook_login;
    ImageButton show_password;
    EditText id, password;
    Button log_in,facebook,google;
    TextView forget_password;
    UDSharePref userSharePref;
    CallbackManager callbackManager;
    int count = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        initVar(view);

        log_in.setOnClickListener(this);
        facebook.setOnClickListener(this);
        show_password.setOnClickListener(this);
        return view;
    }

    private void initVar(View view) {
        id = (EditText) view.findViewById(R.id.id);
        password = (EditText) view.findViewById(R.id.password);
        show_password = (ImageButton) view.findViewById(R.id.show_password);
        log_in = (Button) view.findViewById(R.id.log_in);
        forget_password = (TextView) view.findViewById(R.id.forget_password);
        facebook = (Button) view.findViewById(R.id.facebook);
        google = (Button) view.findViewById(R.id.google);
        facebook_login = (LoginButton) view.findViewById(R.id.fb_login_button);
        //google_login = (LoginButton) view.findViewById(R.id.google_login_button);
        userSharePref = new UDSharePref(getContext());

        facebookRegister();
    }

    @Override
    public void onClick(View v) {
        String strId = id.getText().toString();
        String strPassword = password.getText().toString();
        switch (v.getId()) {
            case R.id.log_in:

                onLogin(strId, strPassword);
                break;

            case R.id.show_password:
                onShowPasswordButtonClick(count++);
                break;

            case R.id.facebook:

                facebook_login.performClick();
                break;

            case R.id.google:
                googleRegister();
                //google_login.performClick();
                break;
        }

    }

    private void googleRegister() {

    }

    private void facebookRegister() {
        callbackManager = CallbackManager.Factory.create();
        facebook_login.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                id.setText("Success");
            }

            @Override
            public void onCancel() {
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
                id.setText("Failed");
            }

            @Override
            public void onError(FacebookException error) {

                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    private void onLogin(String strId, String strPassword) {

        if (!validate(strId, strPassword)) {
            onLogInFailed();
        } else {

            onLogInSuccess(strId, strPassword);
        }

    }

    private void onShowPasswordButtonClick(int count) {
        if (count % 2 == 0) {
            password.setTransformationMethod(PasswordTransformationMethod.getInstance());
            show_password.setImageResource(R.drawable.ic_visibility);
        } else {
            password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            show_password.setImageResource(R.drawable.ic_visibility_off);
        }

    }

    private boolean validate(String strId, String strPassword) {
        boolean valid = true;

        if (strId.isEmpty()) {
            valid = false;
            Snackbar.make(getActivity().findViewById(android.R.id.content), " Email/Mobile empty ", Snackbar.LENGTH_LONG).show();
        } else if (strPassword.isEmpty()) {
            valid = false;
            Snackbar.make(getActivity().findViewById(android.R.id.content), " Password empty ", Snackbar.LENGTH_LONG).show();
        }
        return valid;
    }

    private void onLogInFailed() {

    }

    private void onLogInSuccess(final String strId, final String strPassword) {
        final ProgressDialog dialog = new ProgressDialog(getContext(), ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("Please wait...");
        dialog.setCancelable(false);
        if (CheckInternet.isConnected(getContext())) {
            dialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, ContanstVar.LOGIN, new Response.Listener<String>() {
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
                    params.put(ContanstVar.ID, strId);
                    params.put(ContanstVar.PASSWORD, strPassword);
                    return params;
                }
            };
            RequestQueue queue = Volley.newRequestQueue(getContext());
            queue.add(request);
        } else
            Snackbar.make(getActivity().findViewById(android.R.id.content), "Check Internet Connection", Snackbar.LENGTH_LONG).show();
    }

    private void successResponse(String response) {
        Log.d(TAG, response);
        try {
            JSONObject userJSON = new JSONObject(response);
            JSONObject user_detail = userJSON.getJSONObject(ContanstVar.SERVER_RESPONSE);
            String name = user_detail.getString(ContanstVar.NAME);
            String email = user_detail.getString(ContanstVar.EMAIL);
            String contact = user_detail.getString(ContanstVar.CONTACT);
            userSharePref.storeUserData(email, name, contact, true);

            startActivity(new Intent(getContext(), HomeScreenActivity.class));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}