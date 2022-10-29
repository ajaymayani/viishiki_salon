package com.vishiki.salon.fragements;

import static com.vishiki.salon.SplashActivity.sp;
import static com.vishiki.salon.fragements.ServicesFragment.servicesArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.vishiki.salon.R;
import com.vishiki.salon.models.Services;


public class ProfileFragment extends Fragment {

    ImageView ivProfilePicture;
    TextView tvUsername, tvName, tvDOB, tvUsername1, tvPhoneNumber;
    String imageUrl, name, dob, username, phoneNumber;
    Button btnEdit;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        ivProfilePicture = view.findViewById(R.id.ivProfilePicture);
        tvUsername = view.findViewById(R.id.tvUsername);
        tvName = view.findViewById(R.id.tvName);
        tvDOB = view.findViewById(R.id.tvDOB);
        tvUsername1 = view.findViewById(R.id.tvUsername1);
        tvPhoneNumber = view.findViewById(R.id.tvPhoneNumber);
        btnEdit = view.findViewById(R.id.btnEdit);

        imageUrl = sp.getString("imageUrl", "https://www.kindpng.com/picc/m/24-248253_user-profile-default-image-png-clipart-png-download.png");
        name = sp.getString("name", "");
        dob = sp.getString("dob", "");
        username = sp.getString("username", "");
        phoneNumber = sp.getString("phoneNumber", "");

        Picasso.get().load(imageUrl).placeholder(R.drawable.logo).into(ivProfilePicture);

        tvUsername.setText("Hi, " + username);
        tvName.setText(name);
        tvDOB.setText(dob);
        tvUsername1.setText(username);
        tvPhoneNumber.setText(phoneNumber);


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container,new EditFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }
}