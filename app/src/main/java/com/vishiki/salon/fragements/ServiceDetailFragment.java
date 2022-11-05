package com.vishiki.salon.fragements;

import static com.vishiki.salon.fragements.HomeFragment.servicesArrayList;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.vishiki.salon.R;
import com.vishiki.salon.adapters.ServiceAdapter;

import java.util.Calendar;


public class ServiceDetailFragment extends Fragment {


    RecyclerView rvServiceDetails;
    ImageView imageView;
    String[] service;
    int[] servicePrice;
    Button btnConfirm, btnBack;
    TextView tvHeaderTitle;

    public ServiceDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_service_detail, container, false);
        imageView = view.findViewById(R.id.imageView);
        tvHeaderTitle = view.findViewById(R.id.tvHeaderTitle);
        rvServiceDetails = view.findViewById(R.id.rvServiceDetails);
        btnConfirm = view.findViewById(R.id.btnConfirm);
        btnBack = view.findViewById(R.id.btnBack);
        //servicesArrayList.clear();
        String sType = getArguments().getString("sType");

        if (sType.equals("mHairTrim")) {
            tvHeaderTitle.setText(getString(R.string.hair_trim));
            imageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.haircytmale));
            service = getResources().getStringArray(R.array.mHairTrim);
            servicePrice = getResources().getIntArray(R.array.mHairTrimPrice);
        } else if (sType.equals("mHairColor")) {
            tvHeaderTitle.setText(getString(R.string.hair_color));
            imageView.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.haircolor));
            service = getResources().getStringArray(R.array.hairColor);
            servicePrice = getResources().getIntArray(R.array.hairColorPrice);
        } else if (sType.equals("mSkinCare")) {
            tvHeaderTitle.setText(getString(R.string.skin_care));
            Picasso.get().load("https://www.shutterstock.com/image-photo/barber-applies-black-charcoal-mask-600w-1690328941.jpg").placeholder(R.drawable.logo).into(imageView);
            service = getResources().getStringArray(R.array.mSkinCare);
            servicePrice = getResources().getIntArray(R.array.mSkinCarePrice);

        } else if (sType.equals("mWaxing")) {
            tvHeaderTitle.setText(getString(R.string.waxing));
            Picasso.get().load("https://img.freepik.com/premium-photo/young-man-receiving-waxing-chest-by-young-female-cosmetologist-beauty-salon-portrait-young-female-cosmetologist-during-waxing-male-chest_141172-4422.jpg").placeholder(R.drawable.logo).into(imageView);
            service = getResources().getStringArray(R.array.mWaxing);
            servicePrice = getResources().getIntArray(R.array.mWaxingPrice);

        } else if (sType.equals("mHairTexture")) {
            tvHeaderTitle.setText(getString(R.string.hair_texture));
            Picasso.get().load("https://www.msccollege.edu/wp-content/uploads/2020/11/hair-design-for-men-1024x683.jpg").placeholder(R.drawable.logo).into(imageView);
            service = getResources().getStringArray(R.array.hairTexture);
            servicePrice = getResources().getIntArray(R.array.hairTexturePrice);

        } else if (sType.equals("mBeardGrooming")) {
            tvHeaderTitle.setText(getString(R.string.beard_grooming));
            Picasso.get().load("https://media.istockphoto.com/photos/man-getting-his-beard-trimmed-with-electric-razor-picture-id872361244?k=20&m=872361244&s=612x612&w=0&h=xacxRAgqgXGCN4BAp3dSPZq_jK_irb24I6nZM2P6mNU=").placeholder(R.drawable.logo).into(imageView);
            service = getResources().getStringArray(R.array.mBeardGrooming);
            servicePrice = getResources().getIntArray(R.array.mBeardGroomingPrice);

        } else if (sType.equals("fHairTrim")) {
            tvHeaderTitle.setText(getString(R.string.hair_trim));
            Picasso.get().load("https://previews.123rf.com/images/innareznik/innareznik2009/innareznik200900176/156060875-little-girl-having-her-hair-cut-little-girl-sitting-in-beauty-hair-salon-style-for-children-.jpg").placeholder(R.drawable.logo).into(imageView);
            service = getResources().getStringArray(R.array.fHairTrim);
            servicePrice = getResources().getIntArray(R.array.fHairTrimPrice);

        } else if (sType.equals("fHairColor")) {
            tvHeaderTitle.setText(getString(R.string.hair_color));
            Picasso.get().load("https://iconichairdressing.com/wp-content/uploads/2019/09/foils-1024x1024.jpg").placeholder(R.drawable.logo).into(imageView);
            service = getResources().getStringArray(R.array.fHairColor);
            servicePrice = getResources().getIntArray(R.array.fHairColorPrice);

        } else if (sType.equals("fSkinCare")) {
            tvHeaderTitle.setText(getString(R.string.skin_care));
            Picasso.get().load("https://thumbs.dreamstime.com/b/beautiful-young-woman-facial-mask-beauty-salon-girl-getting-treatment-care-beautician-spa-197454753.jpg").placeholder(R.drawable.logo).into(imageView);
            service = getResources().getStringArray(R.array.fSkinCare);
            servicePrice = getResources().getIntArray(R.array.fSkinCarePrice);

        } else if (sType.equals("fWaxing")) {
            tvHeaderTitle.setText(getString(R.string.waxing));
            Picasso.get().load("https://img.freepik.com/premium-photo/therapist-waxing-womans-leg-spa-center_13339-278416.jpg?w=2000").placeholder(R.drawable.logo).into(imageView);
            service = getResources().getStringArray(R.array.fWaxing);
            servicePrice = getResources().getIntArray(R.array.fWaxingPrice);

        } else if (sType.equals("fHairTexture")) {
            tvHeaderTitle.setText(getString(R.string.hair_texture));
            Picasso.get().load("https://media-cldnry.s-nbcnews.com/image/upload/t_fit-1500w,f_auto,q_auto:best/newscms/2015_50/892076/hairdresser-today-tease-151209.jpg").placeholder(R.drawable.logo).into(imageView);
            service = getResources().getStringArray(R.array.hairTexture);
            servicePrice = getResources().getIntArray(R.array.hairTexturePrice);
        } else if (sType.equals("fNailCare")) {
            tvHeaderTitle.setText(getString(R.string.nail_care));
            Picasso.get().load("https://media.istockphoto.com/photos/woman-getting-her-nails-done-in-salon-by-manicure-worker-picture-id1345345926?b=1&k=20&m=1345345926&s=170667a&w=0&h=pYRQu5cqKfgDiiUTrwDX6w8PH27G-wOR5w-eu2H4-2I=").placeholder(R.drawable.logo).into(imageView);
            service = getResources().getStringArray(R.array.fNailCare);
            servicePrice = getResources().getIntArray(R.array.fNailCarePrice);
        }

        ServiceAdapter adapter = new ServiceAdapter(getActivity(), service, servicePrice);
        rvServiceDetails.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvServiceDetails.setAdapter(adapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, new ServicesFragment())
                        .commit();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (servicesArrayList.size() != 0) {
                    final Calendar myCalendar = Calendar.getInstance();
                    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int day) {
                            myCalendar.set(Calendar.YEAR, year);
                            myCalendar.set(Calendar.MONTH, month);
                            myCalendar.set(Calendar.DAY_OF_MONTH, day);
//                        Toast.makeText(getActivity(), ""+day+"/"+(month+1)+"/"+year, Toast.LENGTH_SHORT).show();
                            String selectedDate = day + "/" + (month + 1) + "/" + year;

                            PaymentFragment paymentFragment = new PaymentFragment();
                            Bundle args = new Bundle();
                            args.putString("date", selectedDate);
                            paymentFragment.setArguments(args);
                            getActivity().getSupportFragmentManager()
                                    .beginTransaction()
                                    .replace(R.id.container, paymentFragment)
                                    .commit();
                        }
                    };
                    new DatePickerDialog(getActivity(), date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("Alert");
                    builder.setMessage("Please select anyone service");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    builder.show();
                }
            }
        });


        return view;
    }
}