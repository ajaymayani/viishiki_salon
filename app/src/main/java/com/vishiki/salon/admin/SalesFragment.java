package com.vishiki.salon.admin;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import com.vishiki.salon.adapters.SaleAdapter;
import com.vishiki.salon.models.Appointment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;


public class SalesFragment extends Fragment {

    private final ArrayList<Appointment> saleArrayList = new ArrayList<>();
    RecyclerView rvSales;
    TextView tvStatus, tvTotal;
    LinearLayout ll;
    FirebaseFirestore db;
    int total;


    public SalesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sales, container, false);

        rvSales = view.findViewById(R.id.rvSales);
        tvStatus = view.findViewById(R.id.tvStatus);
        ll = view.findViewById(R.id.linearLayout);
        tvTotal = view.findViewById(R.id.tvTotal);

        db = FirebaseFirestore.getInstance();

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");
        progressDialog.show();
        db.collection("appointments").whereEqualTo("completed", true).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (queryDocumentSnapshots.isEmpty()) {
                    progressDialog.dismiss();
                    tvStatus.setVisibility(View.VISIBLE);
                    ll.setVisibility(View.GONE);
                    rvSales.setVisibility(View.GONE);
                } else {
                    tvStatus.setVisibility(View.GONE);
                    rvSales.setVisibility(View.VISIBLE);
                    ll.setVisibility(View.VISIBLE);
                    progressDialog.dismiss();

                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Appointment appointment = new Appointment();
                        appointment.setStringHashMap((ArrayList<HashMap<String, Object>>) document.get("servicesList"));
                        appointment.setTotal(document.getString("total"));
                        saleArrayList.add(appointment);
                    }
                    calculateSales();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(getActivity(), "Failed :" + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void calculateSales() {
        ArrayList<String> serviceList = new ArrayList<>();
        ArrayList<String> priceList = new ArrayList<>();

        ArrayList<String> list = new ArrayList<>();
        ArrayList<String> list2 = new ArrayList<>();

        Collections.sort(saleArrayList, new Comparator<Appointment>() {
            @Override
            public int compare(Appointment appointment, Appointment t1) {
                return appointment.getTotal().compareTo(t1.getTotal());
            }
        });

        for (Appointment appointment : saleArrayList) {
            total += Integer.parseInt(appointment.getTotal());
            for (int i = 0; i < appointment.getStringHashMap().size(); i++) {
                HashMap<String, Object> s = appointment.getStringHashMap().get(i);
                list.add(s.get("serviceName").toString());
                list2.add(s.get("servicePrice").toString());
            }
        }

        int t = 0;
        int count = 1;
        HashMap<String, String> hashMap = new HashMap<>();

        for (int i = 0; i < list.size(); i++) {
            t = 0;

            if (!serviceList.contains(list.get(i))) {
                serviceList.add(list.get(i));
                t += Integer.parseInt(list2.get(i));
            } else {
                count++;
                hashMap.put(list.get(i), String.valueOf(count));
                continue;
            }
            priceList.add(String.valueOf(t));
            count = 1;
        }
        tvTotal.setText("₹ " + total);
        SaleAdapter saleAdapter = new SaleAdapter(getActivity(), serviceList, priceList, hashMap);
        rvSales.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvSales.setAdapter(saleAdapter);
    }
}