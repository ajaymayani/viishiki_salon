package com.vishiki.salon.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.vishiki.salon.R;
import com.vishiki.salon.models.Appointment;

import java.util.ArrayList;
import java.util.HashMap;

public class SaleAdapter extends RecyclerView.Adapter<SaleAdapter.ViewHolder> {
    FragmentActivity activity;
    ArrayList<Appointment> saleArrayList;
    HashMap<String, String> hashMap = new HashMap<>();

    ArrayList<String> serviceList;
    ArrayList<String> priceList;


    public SaleAdapter(FragmentActivity activity, ArrayList<String> serviceList, ArrayList<String> priceList, HashMap<String, String> hashMap) {
        this.activity = activity;
        this.serviceList = serviceList;
        this.priceList = priceList;
        this.hashMap = hashMap;
    }

    @NonNull
    @Override
    public SaleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.sale_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull SaleAdapter.ViewHolder holder, int position) {
        holder.tvServices.setText(serviceList.get(position).toString());

        if (hashMap.get(serviceList.get(position)) != null) {
            int count = Integer.parseInt(hashMap.get(serviceList.get(position)));
            holder.tvServicesPrice.setText("₹ " + (Integer.parseInt(priceList.get(position)) * count));
        } else {
            holder.tvServicesPrice.setText("₹ " + priceList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvServices, tvServicesPrice;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvServices = itemView.findViewById(R.id.tvServices);
            tvServicesPrice = itemView.findViewById(R.id.tvServicesPrice);
            linearLayout = itemView.findViewById(R.id.linearLayout);
        }
    }
}
