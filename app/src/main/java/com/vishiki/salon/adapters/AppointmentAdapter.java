package com.vishiki.salon.adapters;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.vishiki.salon.R;
import com.vishiki.salon.admin.AppointmentFragment;
import com.vishiki.salon.models.Appointment;

import java.util.ArrayList;
import java.util.HashMap;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {
    FragmentActivity activity;
    ArrayList<Appointment> appointmentArrayList;
    FirebaseFirestore db;

    public AppointmentAdapter(FragmentActivity activity, ArrayList<Appointment> appointmentArrayList) {
        this.activity = activity;
        this.appointmentArrayList = appointmentArrayList;
        db = FirebaseFirestore.getInstance();
    }

    @NonNull
    @Override
    public AppointmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.appoitnment_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentAdapter.ViewHolder holder, int position) {
        int pos = position;

        Appointment appointment = appointmentArrayList.get(position);
        if (appointment.isCompleted()) {
            holder.btnDelete.setVisibility(View.GONE);
            holder.btnComplete.setVisibility(View.GONE);
        } else {
            holder.btnDelete.setVisibility(View.VISIBLE);
            holder.btnComplete.setVisibility(View.VISIBLE);
        }
        holder.tvAppointmentDate.setText("Date : " + appointment.getAppointmentDate());
        holder.tvTotal.setText("Total : ₹" + appointment.getTotal());
        holder.tvUsername.setText(appointment.getName());
        holder.tvPhoneNumber.setText(appointment.getPhoneNumber());

        StringBuilder builder = new StringBuilder();
        StringBuilder builderPrice = new StringBuilder();

        holder.tvPhoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withContext(activity)
                        .withPermission(Manifest.permission.CALL_PHONE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:" + holder.tvPhoneNumber.getText().toString()));
                                activity.startActivity(callIntent);
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

        for (int i = 0; i < appointment.getStringHashMap().size(); i++) {
            HashMap<String, Object> s = appointment.getStringHashMap().get(i);
            builder.append("->" + s.get("serviceName"));
            builder.append("\n");
            builderPrice.append("₹" + s.get("servicePrice"));
            builderPrice.append("\n");
        }

        holder.tvServices.setText(builder.toString());
        holder.tvServicesPrice.setText(builderPrice.toString());

        holder.btnComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog dialog = new ProgressDialog(activity);
                dialog.setMessage("Please wait...");
                dialog.show();

                db.collection("appointments")
                        .whereEqualTo("name", appointment.getName())
                        .whereEqualTo("appointmentDate", appointment.getAppointmentDate())
                        .whereEqualTo("total", appointment.getTotal())
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot task) {
                                String documentId = task.getDocuments().get(0).getId();
                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("completed", true);

                                db.collection("appointments")
                                        .document(documentId)
                                        .update(hashMap)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                dialog.dismiss();
                                                Toast.makeText(activity, "Completed Successfully..", Toast.LENGTH_SHORT).show();
                                                activity.getSupportFragmentManager()
                                                        .beginTransaction()
                                                        .replace(R.id.adminContainer, new AppointmentFragment())
                                                        .commit();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                dialog.dismiss();
                                                Toast.makeText(activity, "Failed :" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                dialog.dismiss();
                                Toast.makeText(activity, "Failed :" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog dialog = new ProgressDialog(activity);
                dialog.setMessage("Please wait...");
                dialog.show();

                db.collection("appointments")
                        .whereEqualTo("name", appointment.getName())
                        .whereEqualTo("appointmentDate", appointment.getAppointmentDate())
                        .whereEqualTo("total", appointment.getTotal())
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                            @Override
                            public void onSuccess(QuerySnapshot task) {
                                String documentId = task.getDocuments().get(0).getId();
                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("completed", true);

                                db.collection("appointments")
                                        .document(documentId)
                                        .delete()
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                dialog.dismiss();
                                                Toast.makeText(activity, "Delete Successfully..", Toast.LENGTH_SHORT).show();
                                                activity.getSupportFragmentManager()
                                                        .beginTransaction()
                                                        .replace(R.id.adminContainer, new AppointmentFragment())
                                                        .commit();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                dialog.dismiss();
                                                Toast.makeText(activity, "Failed :" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                dialog.dismiss();
                                Toast.makeText(activity, "Failed :" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }

    @Override
    public int getItemCount() {
        return appointmentArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvUsername, tvAppointmentDate, tvServices, tvServicesPrice, tvTotal, tvPhoneNumber;
        Button btnComplete, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvAppointmentDate = itemView.findViewById(R.id.tvAppointmentDate);
            tvServices = itemView.findViewById(R.id.tvServices);
            tvServicesPrice = itemView.findViewById(R.id.tvServicesPrice);
            tvTotal = itemView.findViewById(R.id.tvTotal);
            tvPhoneNumber = itemView.findViewById(R.id.tvPhoneNumber);
            btnComplete = itemView.findViewById(R.id.btnComplete);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
