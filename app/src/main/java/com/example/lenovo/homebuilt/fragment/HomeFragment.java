package com.example.lenovo.homebuilt.fragment;


import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;

import android.provider.Settings;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import androidx.fragment.app.Fragment;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.homebuilt.R;
import com.example.lenovo.homebuilt.adapter.CustomerReviewSliderAdapter;
import com.example.lenovo.homebuilt.adapter.ServiceAdapter;
import com.example.lenovo.homebuilt.model.CustomReview;
import com.example.lenovo.homebuilt.model.Services;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;


public class HomeFragment extends Fragment implements LocationListener, View.OnClickListener {

    private static final String TAG = HomeFragment.class.getSimpleName();
    ArrayList<Services> serviceArrayList;
    RecyclerView trending_service_recycler, repair_service_recycler;
    private static int currentPage = 0;
    ArrayList<CustomReview> customReviewArrayList;
    ViewPager pager;
    CircleIndicator indicator;
    private GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    EditText search_service;
    TextView yourCurrentLocation;
    double latitude, longitude;
    LocationManager manager;
    RelativeLayout architectLayout, vastu_consultantLayout, plumberLayout, electricianLayout, interior_designerLayout, carpenterLayout, painterLayout,modular_kitchenLayout, solar_plantingLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initVar(view);
        displayServiceData();
        customTestimonialData();
        yourCurrentLocation.setOnClickListener(this);
        architectLayout.setOnClickListener(this);
        vastu_consultantLayout.setOnClickListener(this);
        plumberLayout.setOnClickListener(this);
        electricianLayout.setOnClickListener(this);
        interior_designerLayout.setOnClickListener(this);
        carpenterLayout.setOnClickListener(this);
        painterLayout.setOnClickListener(this);
        modular_kitchenLayout.setOnClickListener(this);
        solar_plantingLayout.setOnClickListener(this);

        if (!isGpsOn()) {
            showAlertDialog();
        } else {
            showLocation();
        }
        return view;
    }

    private void initVar(View view) {
        serviceArrayList = new ArrayList<>();
        customReviewArrayList = new ArrayList<>();
        manager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        search_service = (EditText) view.findViewById(R.id.search_service);
        yourCurrentLocation = (TextView) view.findViewById(R.id.yourCurrentLocation);
        trending_service_recycler = (RecyclerView) view.findViewById(R.id.trending_service_recycler_view);
        repair_service_recycler = (RecyclerView) view.findViewById(R.id.repair_service_recycler_view);

        architectLayout = (RelativeLayout) view.findViewById(R.id.architect_layout);
        vastu_consultantLayout = (RelativeLayout) view.findViewById(R.id.vastu_consultant_layout);
        plumberLayout = (RelativeLayout) view.findViewById(R.id.plumber_layout);
        electricianLayout  = (RelativeLayout) view.findViewById(R.id.electrician_layout);
        interior_designerLayout = (RelativeLayout) view.findViewById(R.id.interior_designer_layout);
        carpenterLayout = (RelativeLayout) view.findViewById(R.id.carpenter_layout);
        painterLayout = (RelativeLayout) view.findViewById(R.id.painter_layout);
        modular_kitchenLayout = (RelativeLayout) view.findViewById(R.id.modular_kitchen_layout);
        solar_plantingLayout = (RelativeLayout) view.findViewById(R.id.solar_planting_layout);

        pager = (ViewPager) view.findViewById(R.id.pager);
        indicator = (CircleIndicator) view.findViewById(R.id.indicator);

        trending_service_recycler.setHasFixedSize(true);
        repair_service_recycler.setHasFixedSize(true);

        LinearLayoutManager trendingLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager repairLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        trending_service_recycler.setLayoutManager(trendingLayoutManager);
        repair_service_recycler.setLayoutManager(repairLayoutManager);

    }

    private void customTestimonialData() {
        customReviewArrayList.add(new CustomReview(R.mipmap.architectur, "I utilized plumber services of HomBuilt.com and handyman made a decent showing with regards to likewise cost were moderate. The best part about them was that they were talented and I never needed to direct the handyman on any event"));
        customReviewArrayList.add(new CustomReview(R.mipmap.plumber, "Thank you HomBuilt.com! You more than your name! My home is remodeled superior to my desires! Keep it up)"));
        customReviewArrayList.add(new CustomReview(R.mipmap.flooring, "The services i avail were Architech and Interior designer. The team was higly qualified and were well aware about modern trends. My house is now converted from a normal apartment to a mansion by beautification from the team Hombuilt.com."));
        customReviewArrayList.add(new CustomReview(R.mipmap.compass, "Greetings! An incredible first affair. The craftsman was awesome with his correspondence and timing. But one feedback on one of his joke cracking except everything was great."));
        customReviewArrayList.add(new CustomReview(R.mipmap.carpenter, " utilized plumber services of HomBuilt.com and handyman made a decent showing with regards to likewise cost were moderate. The best part about them was that they were talented and I never needed to direct the handyman on any event."));
        customReviewArrayList.add(new CustomReview(R.mipmap.architectur, "The services i avail were Architech and Interior designer. The team was higly qualified and were well aware about modern trends. My house is now converted from a normal apartment to a mansion by beautification from the team Hombuilt.com."));
        customReviewArrayList.add(new CustomReview(R.mipmap.architectur, "Greetings! An incredible first affair. The craftsman was awesome with his correspondence and timing. But one feedback on one of his joke cracking except everything was great."));
        showCustomerReview();

    }

    private void showCustomerReview() {
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == customReviewArrayList.size()) {
                    currentPage = 0;
                }
                pager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 5000, 5000);
        pager.setAdapter(new CustomerReviewSliderAdapter(getContext(), customReviewArrayList));
        indicator.setViewPager(pager);
    }

    private void displayServiceData() {
        serviceArrayList.add(new Services(R.mipmap.notification, "Architect", 1199));
        serviceArrayList.add(new Services(R.mipmap.notification, "plumber", 1199));
        serviceArrayList.add(new Services(R.mipmap.notification, "vastu consultant", 1199));
        serviceArrayList.add(new Services(R.mipmap.notification, "Interior Designer", 1199));
        serviceArrayList.add(new Services(R.mipmap.notification, "Elctrician", 1199));
        serviceArrayList.add(new Services(R.mipmap.notification, "Carpenter", 1199));
        serviceArrayList.add(new Services(R.mipmap.notification, "Painter", 1199));
        serviceArrayList.add(new Services(R.mipmap.notification, "Solar Planting", 1199));

        Log.d("arrayList ", serviceArrayList.size() + "");

        trending_service_recycler.setAdapter(new ServiceAdapter(getContext(), serviceArrayList));

        repair_service_recycler.setAdapter(new ServiceAdapter(getContext(), serviceArrayList));
    }


    private void showLocation() {
        int resp = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getContext());
        if (resp == ConnectionResult.SUCCESS) {
            initGoogleAPIClient();
        } else {
            Log.e("", "Your Device doesn't support Google Play Services.");
        }

        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(1000)
                .setFastestInterval(1000);
    }

    private boolean isGpsOn() {
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private void showAlertDialog() {
        View dialog_view = View.inflate(getContext(), R.layout.gps_alert_dialog, null);

        Button enable_location = (Button) dialog_view.findViewById(R.id.enable_location);
        final AlertDialog dialog = new AlertDialog.Builder(getContext()).create();
        dialog.setView(dialog_view);
        dialog.setCancelable(false);

        enable_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                showLocation();

            }
        });
        dialog.show();
    }

    private void initGoogleAPIClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                .addApi(LocationServices.API)
                .addConnectionCallbacks(connectionAddListener)
                .addOnConnectionFailedListener(connectionFailedListener)
                .build();
        mGoogleApiClient.connect();
    }

    private GoogleApiClient.ConnectionCallbacks connectionAddListener =
            new GoogleApiClient.ConnectionCallbacks() {
                @Override
                public void onConnected(Bundle bundle) {
                    Log.i("", "onConnected");

                    Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                    if (location == null) {
                        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, HomeFragment.this);
                    } else {
                        displayCurrentLocation(location);
                    }
                }

                @Override
                public void onConnectionSuspended(int i) {

                    Log.e(TAG, "onConnectionSuspended");

                }
            };

    private GoogleApiClient.OnConnectionFailedListener connectionFailedListener =
            new GoogleApiClient.OnConnectionFailedListener() {
                @Override
                public void onConnectionFailed(ConnectionResult connectionResult) {
                    Log.e(TAG, "onConnectionFailed");
                }
            };

    private void displayCurrentLocation(Location location) {

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(getContext(), Locale.getDefault());

        latitude = location.getLatitude();
        longitude = location.getLongitude();

        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);

            if (addresses != null && addresses.size() > 0) {
                String address = addresses.get(0).getAddressLine(0);
                String city = addresses.get(0).getLocality();
                String state = addresses.get(0).getAdminArea();

                yourCurrentLocation.setText(address + " " + city + " " + state);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onLocationChanged(Location location) {
        //displayCurrentLocation(location);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.yourCurrentLocation:
                try {
                    Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN).build(getActivity());
                    startActivityForResult(intent, 1);
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.architect_layout:
                openBottomSheet("Architect");
                break;

            case R.id.vastu_consultant_layout:
                openBottomSheet("Vastu Consultant");
                break;

            case R.id.plumber_layout:
                openBottomSheet("Plumber");
                break;

            case R.id.electrician_layout:
                openBottomSheet("Electrician");
                break;

            case R.id.interior_designer_layout:
                openBottomSheet("Interior Designer");
                break;

            case R.id.carpenter_layout:
                openBottomSheet("Carpenter");
                break;

            case R.id.painter_layout:
                openBottomSheet("Painter");
                break;

            case R.id.modular_kitchen_layout:
                openBottomSheet("Modular Kitchen");
                break;

            case R.id.solar_planting_layout:
                openBottomSheet("Solar Planting");
                break;

        }

    }

    private void openBottomSheet(String service_name) {
        String name = String.valueOf(service_name);
        String yourLocation = yourCurrentLocation.getText().toString();
        final BottomSheetDialogFragment myBottomSheet = ServiceBottomSheetFragment.newInstance(name,yourLocation);
        myBottomSheet.show(getFragmentManager(), myBottomSheet.getTag());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getContext(), data);
                Log.e("Tag", "Place: " + place.getAddress() + place.getPhoneNumber());

                yourCurrentLocation.setText(place.getName() + ", " + place.getAddress());

            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getContext(), data);
                Log.e("Tag", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getContext(), " cancel", Toast.LENGTH_SHORT).show();
            }
        }
    }

}


