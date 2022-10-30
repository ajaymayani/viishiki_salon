package com.vishiki.salon.fragements;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.vishiki.salon.R;
import com.vishiki.salon.models.Services;

import java.util.ArrayList;
import java.util.HashMap;

public class ServicesFragment extends Fragment {

    LinearLayout mHairTrim, mHairColor, mSkinCare, mWaxing, mHairTexture, mBeardGrooming;
    LinearLayout fHairTrim, fHairColor, fSkinCare, fWaxing, fHairTexture, fNailCare;
    public static ArrayList<Services> servicesArrayList = new ArrayList<>();

    public ServicesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_services, container, false);
        bind(view);

        mHairTrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServiceDetailFragment  serviceDetailFragment = new ServiceDetailFragment();
                Bundle args = new Bundle();
                args.putString("sType", "mHairTrim");
                serviceDetailFragment.setArguments(args);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, serviceDetailFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        mHairColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServiceDetailFragment  serviceDetailFragment = new ServiceDetailFragment();
                Bundle args = new Bundle();
                args.putString("sType", "mHairColor");
                serviceDetailFragment.setArguments(args);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, serviceDetailFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        mSkinCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServiceDetailFragment  serviceDetailFragment = new ServiceDetailFragment();
                Bundle args = new Bundle();
                args.putString("sType", "mSkinCare");
                serviceDetailFragment.setArguments(args);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, serviceDetailFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        mWaxing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServiceDetailFragment  serviceDetailFragment = new ServiceDetailFragment();
                Bundle args = new Bundle();
                args.putString("sType", "mWaxing");
                serviceDetailFragment.setArguments(args);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, serviceDetailFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        mHairTexture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServiceDetailFragment  serviceDetailFragment = new ServiceDetailFragment();
                Bundle args = new Bundle();
                args.putString("sType", "mHairTexture");
                serviceDetailFragment.setArguments(args);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, serviceDetailFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        mBeardGrooming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServiceDetailFragment  serviceDetailFragment = new ServiceDetailFragment();
                Bundle args = new Bundle();
                args.putString("sType", "mBeardGrooming");
                serviceDetailFragment.setArguments(args);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, serviceDetailFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        fHairTrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServiceDetailFragment  serviceDetailFragment = new ServiceDetailFragment();
                Bundle args = new Bundle();
                args.putString("sType", "fHairTrim");
                serviceDetailFragment.setArguments(args);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, serviceDetailFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        fHairColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServiceDetailFragment  serviceDetailFragment = new ServiceDetailFragment();
                Bundle args = new Bundle();
                args.putString("sType", "fHairColor");
                serviceDetailFragment.setArguments(args);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, serviceDetailFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        fSkinCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServiceDetailFragment  serviceDetailFragment = new ServiceDetailFragment();
                Bundle args = new Bundle();
                args.putString("sType", "fSkinCare");
                serviceDetailFragment.setArguments(args);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, serviceDetailFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        fWaxing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServiceDetailFragment  serviceDetailFragment = new ServiceDetailFragment();
                Bundle args = new Bundle();
                args.putString("sType", "fWaxing");
                serviceDetailFragment.setArguments(args);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, serviceDetailFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        fHairTexture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServiceDetailFragment  serviceDetailFragment = new ServiceDetailFragment();
                Bundle args = new Bundle();
                args.putString("sType", "fHairTexture");
                serviceDetailFragment.setArguments(args);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, serviceDetailFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        fNailCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServiceDetailFragment  serviceDetailFragment = new ServiceDetailFragment();
                Bundle args = new Bundle();
                args.putString("sType", "fNailCare");
                serviceDetailFragment.setArguments(args);
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, serviceDetailFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }

    void bind(View view) {
        mHairTrim = view.findViewById(R.id.mHairTrim);
        mHairColor = view.findViewById(R.id.mHairColor);
        mSkinCare = view.findViewById(R.id.mSkinCare);
        mWaxing = view.findViewById(R.id.mWaxing);
        mHairTexture = view.findViewById(R.id.mHairTexture);
        mBeardGrooming = view.findViewById(R.id.mBeardGrooming);

        fHairTrim = view.findViewById(R.id.fHairTrim);
        fHairColor = view.findViewById(R.id.fHairColor);
        fSkinCare = view.findViewById(R.id.fSkinCare);
        fWaxing = view.findViewById(R.id.fWaxing);
        fHairTexture = view.findViewById(R.id.fHairTexture);
        fNailCare = view.findViewById(R.id.fNailCare);
    }

}