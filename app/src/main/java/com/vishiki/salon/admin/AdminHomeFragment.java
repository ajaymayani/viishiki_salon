package com.vishiki.salon.admin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.vishiki.salon.R;


public class AdminHomeFragment extends Fragment {

    LinearLayout llAppointment,llUsers,llHistory,llSales;

    public AdminHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_admin_home, container, false);

        llAppointment = view.findViewById(R.id.llAppointment);
        llUsers= view.findViewById(R.id.llUsers);
        llHistory = view.findViewById(R.id.llHistory);
        llSales = view.findViewById(R.id.llSales);

        llAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.adminContainer,new AppointmentFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        llUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.adminContainer,new UsersFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
        llHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.adminContainer,new AdminHistoryFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
        llSales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.adminContainer,new SalesFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });



        return view;
    }
}