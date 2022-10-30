package com.vishiki.salon.fragements;

import static android.app.Activity.RESULT_OK;
import static com.vishiki.salon.SplashActivity.editor;
import static com.vishiki.salon.SplashActivity.sp;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
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
import com.squareup.picasso.Picasso;
import com.vishiki.salon.DashbordActivity;
import com.vishiki.salon.R;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class EditFragment extends Fragment {

    ImageView ivProfilePicture, ivSelectImage;
    EditText etName, etDOB, etPhoneNumber, etUsername, etPassword;
    TextView tvUsername;
    Uri imageUri;
    Button btnSave;
    ImageView ivEye;
    FirebaseFirestore db;
    private boolean isImageChanged = false;
    String username, name, imageUrl, dob, phoneNumber, password;

    public EditFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit, container, false);

        ivProfilePicture = view.findViewById(R.id.ivProfilePicture);
        ivSelectImage = view.findViewById(R.id.ivSelectImage);
        etName = view.findViewById(R.id.etName);
        etDOB = view.findViewById(R.id.etDOB);
        etPhoneNumber = view.findViewById(R.id.etPhoneNumber);
        tvUsername = view.findViewById(R.id.tvUsername);
        btnSave = view.findViewById(R.id.btnSave);
        etUsername = view.findViewById(R.id.etUsername);
        etPassword = view.findViewById(R.id.etPassword);
        ivEye = view.findViewById(R.id.ivEye);

        username = sp.getString("username", "default");
        imageUrl = sp.getString("imageUrl", "");
        dob = sp.getString("dob", "default");
        name = sp.getString("name", "default");
        password = sp.getString("password", "");
        phoneNumber = sp.getString("phoneNumber", "default");

        tvUsername.setText(username);
        etUsername.setText(username);
        etName.setText(name);
        etDOB.setText(dob);
        etPhoneNumber.setText(phoneNumber);
        etPassword.setText(password);
        db = FirebaseFirestore.getInstance();

        ivEye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etPassword.getInputType() == 129) {
                    ivEye.setImageDrawable(getActivity().getDrawable(R.drawable.ic_baseline_remove_red_eye_24));
                    etPassword.setInputType(131073);
                } else if (etPassword.getInputType() == 131073) {
                    ivEye.setImageDrawable(getActivity().getDrawable(R.drawable.hidden));
                    etPassword.setInputType(129);
                }
                Typeface typeface = ResourcesCompat.getFont(getActivity(), R.font.poppins_regular);
                etPassword.setTypeface(typeface);
            }
        });

        Picasso.get().load(imageUrl).into(ivProfilePicture);
        ivSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withContext(getActivity())
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

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String newName = etName.getText().toString();
                String newDOB = etDOB.getText().toString();
                String newPhoneNumber = etPhoneNumber.getText().toString();
                String newUsername = etUsername.getText().toString();
                String newPassword = etPassword.getText().toString();

                if (newName.isEmpty()) {
                    etName.setError("Name can't be empty");
                    return;
                } else if (newPhoneNumber.isEmpty()) {
                    etPhoneNumber.setError("Phone Number can't be empty");
                    return;
                } else if (newPhoneNumber.length() != 10) {
                    etPhoneNumber.setError("Phone Number must be 10 digit");
                    return;
                } else if (newDOB.isEmpty()) {
                    etDOB.setError("DOB can't be empty");
                    return;
                } else if (newUsername.isEmpty()) {
                    etUsername.setError("Username can't be empty");
                    return;
                } else if (newPassword.isEmpty()) {
//                    etPassword.setError("Password can't be empty");
                    etPassword.setBackground(getActivity().getDrawable(R.drawable.border_red));
                    Toast.makeText(getActivity(), "Password can't be empty", Toast.LENGTH_SHORT).show();
                    return;
                } else if (newPassword.length() < 6) {
//                    etPassword.setError("Password must be 6-12 character");
                    etPassword.setBackground(getActivity().getDrawable(R.drawable.border_red));
                    Toast.makeText(getActivity(), "Password must be 6-12 character", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    etPassword.setBackground(getActivity().getDrawable(R.drawable.purple_background));
                }


                if (isImageChanged) {
                    insertDataWithImage(newName, newDOB, newPhoneNumber,newUsername,newPassword);
                } else {
                    insertData(newName, newDOB, newPhoneNumber,newUsername,newPassword);
                }

            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK) {
            imageUri = data.getData();
            try {
                InputStream is = getActivity().getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                ivProfilePicture.setImageBitmap(bitmap);
                isImageChanged = true;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void insertDataWithImage(String newName, String newDOB, String newPhoneNumber, String newUsername, String newPassword) {
        if (imageUri != null) {
            ProgressDialog progressDialog
                    = new ProgressDialog(getActivity());
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

                                            Map<String, Object> updateUser = new HashMap<>();
                                            updateUser.put("name", newName);
                                            updateUser.put("dob", newDOB);
                                            updateUser.put("phoneNumber", newPhoneNumber);
                                            updateUser.put("imageUrl", uri.toString());
                                            updateUser.put("username", newUsername);
                                            updateUser.put("password", newPassword);

                                            db.collection("users")
                                                    .whereEqualTo("username", username)
                                                    .get()
                                                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                                        @Override
                                                        public void onSuccess(QuerySnapshot task) {
                                                            if (!task.isEmpty()) {
                                                                String documentId = task.getDocuments().get(0).getId();

                                                                db.collection("users")
                                                                        .document(documentId)
                                                                        .update(updateUser)
                                                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                            @Override
                                                                            public void onSuccess(Void unused) {
                                                                                editor.putString("name", newName);
                                                                                editor.putString("phoneNumber", newPhoneNumber);
                                                                                editor.putString("dob", newDOB);
                                                                                editor.putString("username", newUsername);
                                                                                editor.putString("password", newPassword);
                                                                                editor.putString("imageUrl", uri.toString());
                                                                                editor.apply();
                                                                                progressDialog.dismiss();
                                                                                Toast.makeText(getActivity(), "Profile update successfully...", Toast.LENGTH_SHORT).show();
                                                                                getActivity().startActivity(new Intent(getActivity(), DashbordActivity.class));
                                                                                getActivity().finishAffinity();
                                                                            }
                                                                        })
                                                                        .addOnFailureListener(new OnFailureListener() {
                                                                            @Override
                                                                            public void onFailure(@NonNull Exception e) {
                                                                                progressDialog.dismiss();
                                                                                Toast.makeText(getActivity(), "Failed : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        });
                                                            }
                                                        }
                                                    })
                                                    .addOnFailureListener(new OnFailureListener() {
                                                        @Override
                                                        public void onFailure(@NonNull Exception e) {
                                                            progressDialog.dismiss();
                                                            Toast.makeText(getActivity(), "Failed : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressDialog.dismiss();
                                            Toast.makeText(getActivity(), "Failed" + e.getMessage(), Toast.LENGTH_SHORT).show();
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
                                    .makeText(getActivity(),
                                            "Failed " + e.getMessage(), Toast.LENGTH_LONG)
                                    .show();

                        }
                    });
        } else {
            Toast.makeText(getActivity(), "Please select profile picture", Toast.LENGTH_SHORT).show();
        }
    }

    public void insertData(String newName, String newDOB, String newPhoneNumber, String newUsername, String newPassword) {
        HashMap<String, Object> newUser = new HashMap<>();
        newUser.put("name", newName);
        newUser.put("dob", newDOB);
        newUser.put("phoneNumber", newPhoneNumber);
        newUser.put("username", newUsername);
        newUser.put("password", newPassword);

        ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Profile updating....");
        dialog.show();

        db.collection("users")
                .whereEqualTo("username", username)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        if (!queryDocumentSnapshots.isEmpty()) {
                            String documentId = queryDocumentSnapshots.getDocuments().get(0).getId();

                            db.collection("users")
                                    .document(documentId)
                                    .update(newUser)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            editor.putString("name", newName);
                                            editor.putString("phoneNumber", newPhoneNumber);
                                            editor.putString("dob", newDOB);
                                            editor.putString("username", newUsername);
                                            editor.putString("password", newPassword);
                                            editor.apply();
                                            dialog.dismiss();
                                            Toast.makeText(getActivity(), "Profile update successfully...", Toast.LENGTH_SHORT).show();
                                            getActivity().startActivity(new Intent(getActivity(), DashbordActivity.class));
                                            getActivity().finishAffinity();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            dialog.dismiss();
                                            Toast.makeText(getActivity(), "Failed : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Failed : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}