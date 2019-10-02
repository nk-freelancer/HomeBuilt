package com.example.lenovo.homebuilt.fragment;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.lenovo.homebuilt.activity.InteriorDesignerActivity;
import com.example.lenovo.homebuilt.R;
import com.example.lenovo.homebuilt.activity.ArchitectActivity;
import com.example.lenovo.homebuilt.activity.BookServiceActivity;
import com.example.lenovo.homebuilt.activity.ModularKitchenActivity;
import com.example.lenovo.homebuilt.activity.PainterActivity;
import com.example.lenovo.homebuilt.activity.RateCardActivity;
import com.example.lenovo.homebuilt.activity.SolarPlantingActivity;
import com.example.lenovo.homebuilt.activity.VastuConsultantActivity;


public class ServiceBottomSheetFragment extends BottomSheetDialogFragment {
    static String mString, mlocation;

    static ServiceBottomSheetFragment newInstance(String data, String location) {
        mString = data;
        mlocation = location;
        ServiceBottomSheetFragment f = new ServiceBottomSheetFragment();
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.custom_bottom_sheet_service, container, false);
        Button book_service = (Button) view.findViewById(R.id.book_service);
        Button rate_card = (Button) view.findViewById(R.id.rate_card);

        book_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;

                switch (mString) {
                    case "Architect":
                        intent = new Intent(getActivity(), ArchitectActivity.class);
                        break;

                    case "Vastu Consultant":
                        intent = new Intent(getActivity(), VastuConsultantActivity.class);
                        break;

                    case "Interior Designer":
                        intent = new Intent(getActivity(), InteriorDesignerActivity.class);
                        break;

                    case "Painter":
                        intent = new Intent(getActivity(), PainterActivity.class);
                        break;


                    case "Modular Kitchen":
                        intent = new Intent(getActivity(), ModularKitchenActivity.class);
                        break;

                    case "Solar Planting":
                        intent = new Intent(getActivity(), SolarPlantingActivity.class);
                        break;

                    default:
                        intent = new Intent(getActivity(), BookServiceActivity.class);
                        break;
                }
                intent.putExtra("service", mString);
                intent.putExtra("location", mlocation);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                dismiss();
            }
        });

        rate_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RateCardActivity.class);
                intent.putExtra("service", mString);
                intent.putExtra("location", mlocation);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                dismiss();
            }
        });
        return view;
    }

}
