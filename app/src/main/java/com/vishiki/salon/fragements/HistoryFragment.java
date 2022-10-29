package com.vishiki.salon.fragements;

import static com.vishiki.salon.SplashActivity.sp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.vishiki.salon.R;
import com.vishiki.salon.adapters.HistoryAdapter;
import com.vishiki.salon.models.Appointment;
import com.vishiki.salon.models.Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HistoryFragment extends Fragment {

    RecyclerView rvHistory;
    FirebaseFirestore db;
    TextView tvStatus;
    private String username;
    ArrayList<Appointment> appointmentArrayList = new ArrayList<>();

    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        rvHistory = view.findViewById(R.id.rvHistory);
        tvStatus = view.findViewById(R.id.tvStatus);

        db = FirebaseFirestore.getInstance();
        username = sp.getString("username", "default");

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        db.collection("appointments")
                .whereEqualTo("username", username)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (queryDocumentSnapshots.isEmpty()) {
                            progressDialog.dismiss();
                            tvStatus.setVisibility(View.VISIBLE);
                            rvHistory.setVisibility(View.GONE);
                        } else {
                            tvStatus.setVisibility(View.GONE);
                            rvHistory.setVisibility(View.VISIBLE);
                            progressDialog.dismiss();

                            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                Appointment appointment = new Appointment();
                                appointment.setUsername(document.getString("username"));
                                appointment.setStringHashMap((ArrayList<HashMap<String, Object>>) document.get("servicesList"));
                                appointment.setTotal(document.getString("total"));
                                appointment.setAppointmentDate(document.getString("appointmentDate"));

                                appointmentArrayList.add(appointment);
                            }
                            HistoryAdapter historyAdapter = new HistoryAdapter(getActivity(),appointmentArrayList);
                            rvHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
                            rvHistory.setAdapter(historyAdapter);
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