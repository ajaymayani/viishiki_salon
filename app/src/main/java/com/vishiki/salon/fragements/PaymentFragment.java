package com.vishiki.salon.fragements;

import static com.vishiki.salon.SplashActivity.sp;
import static com.vishiki.salon.fragements.HomeFragment.servicesArrayList;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.vishiki.salon.R;
import com.vishiki.salon.models.Appointment;
import com.vishiki.salon.models.Services;

import java.util.ArrayList;

public class PaymentFragment extends Fragment {

    TextView tvUsername, tvServices, tvDate, tvTotal, tvServicesPrice;
    Button btnAppointment;
    Appointment appointment;
    FirebaseFirestore db;
    ArrayList<Services> servicesList = new ArrayList<>();

    public PaymentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_payment, container, false);

        tvUsername = view.findViewById(R.id.tvUsername);
        tvServices = view.findViewById(R.id.tvServices);
        tvDate = view.findViewById(R.id.tvDate);
        tvServicesPrice = view.findViewById(R.id.tvServicesPrice);
        tvTotal = view.findViewById(R.id.tvTotal);
        btnAppointment = view.findViewById(R.id.btnAppointment);
        appointment = new Appointment();

        String selectedDate = getArguments().getString("date");
        String username = sp.getString("username", "default");
        String name = sp.getString("name","default");
        String phoneNumber = sp.getString("phoneNumber","default");

        StringBuilder builder = new StringBuilder();
        StringBuilder builderPrice = new StringBuilder();

        int total = 0;

        tvUsername.setText("Username : " + username);
        tvDate.setText("Date : " + selectedDate);
        appointment.setUsername(username);
        for (Services s : servicesArrayList) {
            servicesList.add(s);

            builder.append("->" + s.getServiceName());
            builder.append("\n");
            builderPrice.append("₹" + s.getServicePrice());
            builderPrice.append("\n");
            total += s.getServicePrice();
        }
        appointment.setServicesList(servicesList);
        appointment.setAppointmentDate(selectedDate);

        tvServices.setText(builder.toString());
        tvServicesPrice.setText(builderPrice.toString());
        tvTotal.setText("Total : ₹" + total);
        appointment.setTotal(String.valueOf(total));
        appointment.setCompleted(false);
        appointment.setName(name);
        appointment.setPhoneNumber(phoneNumber);

        btnAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ProgressDialog progressDialog
                        = new ProgressDialog(getActivity());
                progressDialog.setTitle("Uploading...");
                progressDialog.show();

                db = FirebaseFirestore.getInstance();

                db.collection("appointments")
                        .add(appointment)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                progressDialog.dismiss();

//                                Toast.makeText(getActivity(), "Added", Toast.LENGTH_SHORT).show();
                                servicesArrayList.clear();

                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Toast.makeText(getActivity(), "Failed:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                servicesArrayList.clear();
                                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();

                            }
                        });
            }
        });
        return view;
    }
}