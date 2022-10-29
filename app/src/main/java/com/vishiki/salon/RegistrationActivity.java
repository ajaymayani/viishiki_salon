package com.vishiki.salon;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.vishiki.salon.models.User;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class RegistrationActivity extends AppCompatActivity {

    EditText etName, etPhoneNumber, etDOB, etUsername, etPassword;
    Button btnRegister;
    ImageView ivSelectImage, ivProfilePicture;
    Uri imageUri;
    Bitmap bitmap;
    InputStream is;
    FirebaseFirestore db;
    TextView tvSignIn;
    ImageView ivEye;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        etName = findViewById(R.id.etName);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etDOB = findViewById(R.id.etDOB);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnRegister = findViewById(R.id.btnRegister);
        ivSelectImage = findViewById(R.id.ivSelectImage);
        ivProfilePicture = findViewById(R.id.ivProfilePicture);
        tvSignIn = findViewById(R.id.tvSignIn);
        ivEye = findViewById(R.id.ivEye);

        etDOB.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 2) {
                    etDOB.append("/");
                } else if (charSequence.length() == 5) {
                    etDOB.append("/");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

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

            }
        });


        ivSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withContext(RegistrationActivity.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(intent, 101);
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

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                String phonenumber = etPhoneNumber.getText().toString();
                String dob = etDOB.getText().toString();
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                if (name.isEmpty()) {
                    etName.setError("Name can't be empty");
                } else if (phonenumber.isEmpty()) {
                    etPhoneNumber.setError("Phone Number can't be empty");
                } else if (phonenumber.length() != 10) {
                    etPhoneNumber.setError("Phone Number must be 10 digit");
                } else if (dob.isEmpty()) {
                    etDOB.setError("DOB can't be empty");
                } else if (username.isEmpty()) {
                    etUsername.setError("Username can't be empty");
                } else if (password.isEmpty()) {
//                    etPassword.setError("Password can't be empty");
                    etPassword.setBackground(getDrawable(R.drawable.border_red));
                    Toast.makeText(RegistrationActivity.this, "Password can't be empty", Toast.LENGTH_SHORT).show();
                } else if (password.length() < 6) {
//                    etPassword.setError("Password must be 6-12 character");
                    etPassword.setBackground(getDrawable(R.drawable.border_red));
                    Toast.makeText(RegistrationActivity.this, "Password must be 6-12 character", Toast.LENGTH_SHORT).show();
                }else{
                    etPassword.setBackground(getDrawable(R.drawable.border_black));
                }

                if (!name.isEmpty() && phonenumber.length() == 10 && !dob.isEmpty() && !username.isEmpty() && password.length() >= 6) {
                    if (imageUri != null) {
                        ProgressDialog progressDialog
                                = new ProgressDialog(RegistrationActivity.this);
                        progressDialog.setTitle("Uploading...");
                        progressDialog.show();

                        FirebaseStorage storage = FirebaseStorage.getInstance();
                        StorageReference storageRef = storage.getReference();

                        StorageReference ref = storageRef.child("users_profile_pic/" + username + ".jpg");

                        ref.putFile(imageUri)
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                        ref.getDownloadUrl()
                                                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        User user = new User(name, phonenumber, dob, username, password, String.valueOf(uri));

                                                        db = FirebaseFirestore.getInstance();

                                                        db.collection("users")
                                                                .add(user)
                                                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                                                    @Override
                                                                    public void onSuccess(DocumentReference documentReference) {
                                                                        progressDialog.dismiss();
                                                                        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                                                                        finish();
                                                                    }
                                                                }).addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        Toast.makeText(RegistrationActivity.this, "Failed:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                    }
                                                                });


                                                        /*FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                                        DatabaseReference databaseReference = firebaseDatabase.getReference("users");
                                                        databaseReference.push().setValue(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                            @Override
                                                            public void onSuccess(Void unused) {
                                                                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                                                                finish();
                                                            }
                                                        }).addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toast.makeText(RegistrationActivity.this, "Something went to wrong", Toast.LENGTH_SHORT).show();
                                                            }
                                                        });*/
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        progressDialog.dismiss();
                                                        Toast.makeText(RegistrationActivity.this, "Failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                    }
                                })
                                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                                        double progress
                                                = (100.0
                                                * snapshot.getBytesTransferred()
                                                / snapshot.getTotalByteCount());
                                        progressDialog.setMessage(
                                                "Uploaded "
                                                        + (int) progress + "%");
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressDialog.dismiss();
                                        Log.e("log", "Error : " + e.getMessage());
                                        Toast
                                                .makeText(RegistrationActivity.this,
                                                        "Failed " + e.getMessage(), Toast.LENGTH_LONG)
                                                .show();

                                    }
                                });
                    } else {
                        Toast.makeText(RegistrationActivity.this, "Please select profile picture", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK) {
            imageUri = data.getData();
            try {
                is = getContentResolver().openInputStream(imageUri);
                bitmap = BitmapFactory.decodeStream(is);
                ivProfilePicture.setImageBitmap(bitmap);
                Toast.makeText(this, "Upload from gallery", Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


}