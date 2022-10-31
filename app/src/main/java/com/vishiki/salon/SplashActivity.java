package com.vishiki.salon;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.vishiki.salon.admin.AdminDashboardActivity;

public class SplashActivity extends AppCompatActivity {


    public static SharedPreferences sp;
    public static SharedPreferences.Editor editor;
    ImageView ivLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sp = getSharedPreferences("credential", MODE_PRIVATE);
        editor = sp.edit();

        ivLogo = findViewById(R.id.ivLogo);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash_animation);
        ivLogo.startAnimation(animation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (sp.getString("username", "").equals("admin")) {
                    startActivity(new Intent(SplashActivity.this, AdminDashboardActivity.class));
                    finish();
                } else if (!sp.getString("username", "default").equals("default")) {
                    startActivity(new Intent(SplashActivity.this, DashbordActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }
        }, 2500);
    }
}