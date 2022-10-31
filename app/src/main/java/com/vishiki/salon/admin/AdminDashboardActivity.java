package com.vishiki.salon.admin;

import static com.vishiki.salon.SplashActivity.editor;
import static com.vishiki.salon.SplashActivity.sp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;
import com.vishiki.salon.LoginActivity;
import com.vishiki.salon.R;

public class AdminDashboardActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.navigationView);
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportFragmentManager().beginTransaction().replace(R.id.adminContainer, new AdminHomeFragment()).commit();

        View headerLayout = navigationView.getHeaderView(0);
        ImageView imageView = headerLayout.findViewById(R.id.ivProfilePicture);
        TextView tvName = headerLayout.findViewById(R.id.tvName);

        Picasso.get().load(R.drawable.logo).into(imageView);
        tvName.setText(sp.getString("username", "Default"));

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment temp = null;
                switch (item.getItemId()) {
                    case R.id.mHome:
                        temp = new AdminHomeFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.adminContainer, temp).addToBackStack(null).commit();
                        break;
                    case R.id.mProfile:
                        temp = new AdminHomeFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.adminContainer, temp).addToBackStack(null).commit();
                        break;
                    case R.id.mAppointment:
                        temp = new AppointmentFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.adminContainer, temp).addToBackStack(null).commit();
                        break;
                    case R.id.mUsers:
                        temp = new UsersFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.adminContainer, temp).addToBackStack(null).commit();
                        break;
                    case R.id.mLogout:
                        editor.clear();
                        editor.apply();
                        startActivity(new Intent(AdminDashboardActivity.this, LoginActivity.class));
                        finish();
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }
}