package com.vishiki.salon.adapters;


import static com.vishiki.salon.fragements.HomeFragment.servicesArrayList;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.vishiki.salon.R;
import com.vishiki.salon.models.Services;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {
    Activity activity;
    String[] services;
    int[] servicesPrice;
    int count = 0;

    public ServiceAdapter(Activity activity, String[] services, int[] servicesPrice) {
        this.activity = activity;
        this.services = services;
        this.servicesPrice = servicesPrice;
    }

    @NonNull
    @Override
    public ServiceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(activity).inflate(R.layout.service_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceAdapter.ViewHolder holder, int position) {
        int i = position;
        holder.tvServiceName.setText(services[position]);
        holder.tvServicePrice.setText("₹" + servicesPrice[position]);

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = 0;

                if (holder.checkBox.isChecked()) {
                    servicesArrayList.add(new Services(services[i], servicesPrice[i], i));
                } else {
                    for (Services service : servicesArrayList) {
                        if (i == service.getPosition()) {

                            break;
                        }
                        count++;
                    }
                    servicesArrayList.remove(count);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return services.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvServiceName, tvServicePrice;
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvServiceName = itemView.findViewById(R.id.tvServiceName);
            tvServicePrice = itemView.findViewById(R.id.tvServicePrice);
            checkBox = itemView.findViewById(R.id.checkbox);
        }
    }
}
