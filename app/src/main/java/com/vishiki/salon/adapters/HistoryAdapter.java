package com.vishiki.salon.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.vishiki.salon.R;
import com.vishiki.salon.models.Appointment;
import com.vishiki.salon.models.Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    FragmentActivity activity;
    ArrayList<Appointment> appointmentArrayList;

    public HistoryAdapter(FragmentActivity activity, ArrayList<Appointment> appointmentArrayList) {
        this.activity = activity;
        this.appointmentArrayList = appointmentArrayList;
    }

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.history_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.ViewHolder holder, int position) {
        Appointment appointment = appointmentArrayList.get(position);
        holder.tvAppointmentDate.setText("Date : " + appointment.getAppointmentDate());
        holder.tvTotal.setText("Total : ₹" + appointment.getTotal());
        StringBuilder builder = new StringBuilder();
        StringBuilder builderPrice = new StringBuilder();

        for(int i=0;i<appointment.getStringHashMap().size();i++)
        {
            HashMap<String,Object> s = appointment.getStringHashMap().get(i);
            builder.append("->"+ s.get("serviceName"));
            builder.append("\n");
            builderPrice.append("₹"+s.get("servicePrice"));
            builderPrice.append("\n");
        }

        holder.tvServices.setText(builder.toString());
        holder.tvServicesPrice.setText(builderPrice.toString());
    }

    @Override
    public int getItemCount() {
        return appointmentArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvAppointmentDate, tvServices,tvServicesPrice, tvTotal;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvAppointmentDate = itemView.findViewById(R.id.tvAppointmentDate);
            tvServices = itemView.findViewById(R.id.tvServices);
            tvServicesPrice = itemView.findViewById(R.id.tvServicesPrice);
            tvTotal = itemView.findViewById(R.id.tvTotal);
        }
    }
}
