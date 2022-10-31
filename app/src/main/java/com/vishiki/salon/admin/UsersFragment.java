package com.vishiki.salon.admin;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.vishiki.salon.R;
import com.vishiki.salon.adapters.HistoryAdapter;
import com.vishiki.salon.adapters.UserAdapter;
import com.vishiki.salon.models.Appointment;
import com.vishiki.salon.models.User;

import java.util.ArrayList;
import java.util.HashMap;

public class UsersFragment extends Fragment {

    RecyclerView rvUsers;
    TextView tvStatus;
    FirebaseFirestore db;
    private ArrayList<User> userArrayList = new ArrayList<>();

    public UsersFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users, container, false);

        rvUsers = view.findViewById(R.id.rvUsers);
        tvStatus = view.findViewById(R.id.tvStatus);

        db = FirebaseFirestore.getInstance();

        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait...");
        progressDialog.show();

        db.collection("users")
                .whereNotEqualTo("username","admin")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot task) {
                        if (task.isEmpty()) {
                            progressDialog.dismiss();
                            tvStatus.setVisibility(View.VISIBLE);
                            rvUsers.setVisibility(View.GONE);
                        } else {
                            tvStatus.setVisibility(View.GONE);
                            rvUsers.setVisibility(View.VISIBLE);
                            progressDialog.dismiss();

                            for (QueryDocumentSnapshot document : task) {
                                User user = new User();
                                user.setName(document.getString("name"));
                                user.setDob(document.getString("dob"));
                                user.setImageUrl(document.getString("imageUrl"));
                                user.setPhoneNumber(document.getString("phoneNumber"));
                                user.setUsername(document.getString("username"));
                                userArrayList.add(user);
                            }
                            UserAdapter userAdapter = new UserAdapter(getActivity(),userArrayList);
                            rvUsers.setLayoutManager(new LinearLayoutManager(getActivity()));
                            rvUsers.setAdapter(userAdapter);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                        Toast.makeText(getActivity(), "Failed : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        return  view;
    }
}