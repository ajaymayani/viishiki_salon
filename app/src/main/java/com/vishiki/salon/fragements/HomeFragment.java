package com.vishiki.salon.fragements;


import static com.vishiki.salon.SplashActivity.editor;
import static com.vishiki.salon.SplashActivity.sp;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;
import com.vishiki.salon.LoginActivity;
import com.vishiki.salon.R;
import com.vishiki.salon.RegistrationActivity;
import com.vishiki.salon.models.Services;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    TextView tvUsername;
    LinearLayout llLogout, llServices, llHistory, llContactUs;
    ImageView ivProfilePicture;
    public static ArrayList<Services> servicesArrayList = new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tvUsername = view.findViewById(R.id.tvUsername);
        llLogout = view.findViewById(R.id.llLogout);
        llServices = view.findViewById(R.id.llServices);
        llHistory = view.findViewById(R.id.llHistory);
        llContactUs = view.findViewById(R.id.llContactUs);
        ivProfilePicture = view.findViewById(R.id.ivProfilePicture);

        Picasso.get().load(sp.getString("imageUrl", "https://www.kindpng.com/picc/m/24-248253_user-profile-default-image-png-clipart-png-download.png")).placeholder(R.drawable.logo).into(ivProfilePicture);
        tvUsername.setText(sp.getString("username", "default"));

        llLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.clear();
                editor.apply();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        });

        ivProfilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new ProfileFragment()).addToBackStack(null).commit();
            }
        });
        llServices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                servicesArrayList.clear();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new ServicesFragment()).addToBackStack(null).commit();
            }
        });

        llHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new HistoryFragment()).addToBackStack(null).commit();
            }
        });

        llContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dexter.withContext(getActivity())
                        .withPermission(Manifest.permission.CALL_PHONE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:"+1234567890));
                                startActivity(callIntent);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        return view;
    }
}