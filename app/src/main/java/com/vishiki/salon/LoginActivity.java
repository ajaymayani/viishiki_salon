package com.vishiki.salon;

import static com.vishiki.salon.SplashActivity.editor;
import static com.vishiki.salon.SplashActivity.sp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.vishiki.salon.admin.AdminDashboardActivity;
import com.vishiki.salon.models.User;

public class LoginActivity extends AppCompatActivity {

    EditText etUsername, etPassword;
    Button btnLogin;
    TextView tvSignUp;
    String userName, password;
    FirebaseFirestore db;
    ImageView ivEye;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvSignUp = findViewById(R.id.tvSignUp);
        ivEye = findViewById(R.id.ivEye);

        if (!sp.getString("name", "default").equals("default")) {
            goToDashboard();
        }

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
                finish();
            }
        });

        ivEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etPassword.getInputType() == 129) {
                    ivEye.setImageDrawable(getDrawable(R.drawable.ic_baseline_remove_red_eye_24));
                    etPassword.setInputType(131073);
                } else if (etPassword.getInputType() == 131073) {
                    ivEye.setImageDrawable(getDrawable(R.drawable.hidden));
                    etPassword.setInputType(129);
                }

                Typeface typeface = ResourcesCompat.getFont(LoginActivity.this, R.font.poppins_regular);
                etPassword.setTypeface(typeface);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName = etUsername.getText().toString();
                password = etPassword.getText().toString();

                if (userName.isEmpty()) {
                    etUsername.setError("username can't be empty");
//                    etUsername.setBackground(getDrawable(R.drawable.border_red));
//                    Toast.makeText(LoginActivity.this, "Username can't be empty", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
//                    etPassword.setError("Password can't be empty");
                    etPassword.setBackground(getDrawable(R.drawable.border_red));
                    Toast.makeText(LoginActivity.this, "Password can't be empty", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 6) {
//                    etPassword.setError("Password must be 6-12 character");
                    etPassword.setBackground(getDrawable(R.drawable.border_red));
                    Toast.makeText(LoginActivity.this, "Password must be 6-12 character", Toast.LENGTH_SHORT).show();
                } else {
                    etPassword.setBackground(getDrawable(R.drawable.border_black));
                }

                if (!userName.isEmpty() && password.length() >= 6) {
                    progressDialog = new ProgressDialog(LoginActivity.this);
                    progressDialog.setMessage("Please wait...");
                    progressDialog.show();

                    db = FirebaseFirestore.getInstance();
                    db.collection("users")
                            .whereEqualTo("username", userName)
                            .whereEqualTo("password", password)
                            .get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    if (queryDocumentSnapshots.isEmpty()) {
                                        progressDialog.dismiss();
                                        Toast.makeText(LoginActivity.this, "Invalid Credential", Toast.LENGTH_SHORT).show();
                                    } else {
                                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                                            String username = document.getString("username");
                                            String password = document.getString("password");

                                            if (username.equals("admin") && password.equals("admin@123")) {

                                                editor.putString("username", username);
                                                editor.putString("password", password);
                                                editor.apply();
                                                goToAdminDashboard();
                                            } else {
                                                User user = new User();
                                                user.setName(document.getString("name"));
                                                user.setPhoneNumber(document.getString("phoneNumber"));
                                                user.setDob(document.getString("dob"));
                                                user.setUsername(username);
                                                user.setPassword(password);
                                                user.setImageUrl(document.getString("imageUrl"));

                                                editor.putString("name", user.getName());
                                                editor.putString("phoneNumber", user.getPhoneNumber());
                                                editor.putString("dob", user.getDob());
                                                editor.putString("username", user.getUsername());
                                                editor.putString("password", user.getPassword());
                                                editor.putString("imageUrl", user.getImageUrl());
                                                editor.apply();
                                                goToDashboard();
                                            }
                                            progressDialog.dismiss();
                                        }
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(LoginActivity.this, "Failed:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
    }

    public void goToDashboard() {
        startActivity(new Intent(LoginActivity.this, DashbordActivity.class));
        finish();
    }
    public void goToAdminDashboard() {
        startActivity(new Intent(LoginActivity.this, AdminDashboardActivity.class));
        finish();
    }


}