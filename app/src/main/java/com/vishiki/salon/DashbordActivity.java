package com.vishiki.salon;

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
import com.vishiki.salon.fragements.FAQFragment;
import com.vishiki.salon.fragements.HistoryFragment;
import com.vishiki.salon.fragements.HomeFragment;
import com.vishiki.salon.fragements.ProfileFragment;

public class DashbordActivity extends AppCompatActivity {


    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;
    ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashbord);

        drawerLayout = findViewById(R.id.drawer);
        toolbar = findViewById(R.id.toolbar);

//        setSupportActionBar(toolbar);
        navigationView = findViewById(R.id.navigationView);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();

        View headerLayout = navigationView.getHeaderView(0);
        ImageView imageView = headerLayout.findViewById(R.id.ivProfilePicture);
        TextView tvName = headerLayout.findViewById(R.id.tvName);

        Picasso.get().load(sp.getString("imageUrl", "https://www.kindpng.com/picc/m/24-248253_user-profile-default-image-png-clipart-png-download.png")).placeholder(R.drawable.logo).into(imageView);
        tvName.setText(sp.getString("name", "Default"));

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment temp = null;
                switch (item.getItemId()) {
                    case R.id.mHome:
                        temp = new HomeFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, temp).addToBackStack(null).commit();
                        break;
                    case R.id.mProfile:
                        temp = new ProfileFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, temp).addToBackStack(null).commit();
                        break;
                    case R.id.mHistory:
                        temp = new HistoryFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, temp).addToBackStack(null).commit();
                        break;
                    case R.id.mFAQs:
                        temp = new FAQFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.container, temp).addToBackStack(null).commit();
                        break;
                    case R.id.mLogout:
                        editor.clear();
                        editor.apply();
                        startActivity(new Intent(DashbordActivity.this, LoginActivity.class));
                        finish();
                        break;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

}