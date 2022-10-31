package com.vishiki.salon.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.vishiki.salon.R;
import com.vishiki.salon.models.User;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    FragmentActivity activity;
    ArrayList<User> userArrayList;

    public UserAdapter(FragmentActivity activity, ArrayList<User> userArrayList) {
        this.activity = activity;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.user_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.ViewHolder holder, int position) {
        User user = userArrayList.get(position);
        holder.tvName.setText(user.getName());
        holder.tvUsername.setText(user.getUsername());
        holder.tvDOB.setText(user.getDob());
        holder.tvPhoneNumber.setText(user.getPhoneNumber());
        Picasso.get().load(user.getImageUrl()).placeholder(R.drawable.logo).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvName,tvUsername,tvDOB,tvPhoneNumber;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            tvName = itemView.findViewById(R.id.tvName);
            tvUsername =itemView.findViewById(R.id.tvUsername);
            tvDOB = itemView.findViewById(R.id.tvDOB);
            tvPhoneNumber =itemView.findViewById(R.id.tvPhoneNumber);
        }
    }
}
