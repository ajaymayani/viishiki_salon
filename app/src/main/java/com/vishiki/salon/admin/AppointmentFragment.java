package com.vishiki.salon.admin;

import static com.vishiki.salon.SplashActivity.sp;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.vishiki.salon.R;
import com.vishiki.salon.adapters.AppointmentAdapter;
import com.vishiki.salon.adapters.HistoryAdapter;
import com.vishiki.salon.models.Appointment;

import java.util.ArrayList;
import java.util.HashMap;


public class AppointmentFragment extends Fragment {

    RecyclerView rvAppointment;
    TextView tvStatus;
    FirebaseFirestore db;
    private ArrayList<Appointment> appointmentArrayList = new ArrayList<>();

    public AppointmentFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_appointment, container, false);

        rvAppointment = view.findViewById(R.id.rvAppointment);
        tvStatus = view.findViewById(R.id.tvStatus);

        db = FirebaseFirestore.getInstance();

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        db.collection("appointments")
                .whereEqualTo("completed",false)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.isEmpty()) {
                            progressDialog.dismiss();
                            tvStatus.setVisibility(View.VISIBLE);
                            rvAppointment.setVisibility(View.GONE);
                        } else {
                            tvStatus.setVisibility(View.GONE);
                            rvAppointment.setVisibility(View.VISIBLE);
                            progressDialog.dismiss();

                            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                Appointment appointment = new Appointment();
                                appointment.setUsername(document.getString("username"));
                                appointment.setStringHashMap((ArrayList<HashMap<String, Object>>) document.get("servicesList"));
                                appointment.setTotal(document.getString("total"));
                                appointment.setAppointmentDate(document.getString("appointmentDate"));
                                appointment.setCompleted(document.getBoolean("completed"));
                                appointment.setName(document.getString("name"));
                                appointment.setPhoneNumber(document.getString("phoneNumber"));

                                appointmentArrayList.add(appointment);
                            }
                            AppointmentAdapter appointmentAdapter = new AppointmentAdapter(getActivity(),appointmentArrayList);
                            rvAppointment.setLayoutManager(new LinearLayoutManager(getActivity()));
                            rvAppointment.setAdapter(appointmentAdapter);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Failed :" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        return view;
    }
}