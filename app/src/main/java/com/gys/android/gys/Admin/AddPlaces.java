package com.gys.android.gys.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gys.android.gys.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;


public class AddPlaces extends AppCompatActivity {

        private String name, description, price, saveCD, saveCT, placeRanKey,downloadImageUrl
                , govern;
        TextView textView;
        EditText placeName, placeDes, placePrice;
        Button create;
        ProgressDialog progressDialog;
        ImageView imageView;
        private static final int GallaryPich = 1;
        private Uri imageUri;
        private StorageReference ImagesRef;
        private DatabaseReference placesRef;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_places);

            //initialize
            textView = findViewById(R.id.addplacesgovname);
            placeName = findViewById(R.id.place_new_name);
            placeDes = findViewById(R.id.place_new_desc);
            placePrice = findViewById(R.id.place_new_price);
            imageView = findViewById(R.id.place_new_image);
            create = findViewById(R.id.place_create);
            progressDialog = new ProgressDialog(this);

            //firebase reference
            placesRef = FirebaseDatabase.getInstance().getReference().child("Places");

            //Image storage
            ImagesRef = FirebaseStorage.getInstance().getReference().child("Places Images");
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //choose photo from gallery method
                    openGallary();
                }
            });
            create.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //check data
                    validateData();
                }
            });

            govern = getIntent().getStringExtra("gov");
            textView.setText(govern);

        }

        private void validateData() {
            name = placeName.getText().toString();
            description = placeDes.getText().toString();
            price = placePrice.getText().toString();

            if (imageUri == null) {
                Toast.makeText(this, "Image", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(name) || TextUtils.isEmpty(price)
                    || TextUtils.isEmpty(description)) {
                Toast.makeText(this, "Data", Toast.LENGTH_SHORT).show();
            } else {

                //save data
                storeInfo();
            }

        }

        private void storeInfo() {
            progressDialog.setTitle("New Place ...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            //getting date and time

            saveCD = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

            saveCT =  new SimpleDateFormat("HH:MM:SS a", Locale.getDefault()).format(new Date());


            placeRanKey = saveCD + saveCT;

            //path of the image
            final StorageReference path = ImagesRef.child(imageUri.getLastPathSegment() + placeRanKey + ".jpg");
            final UploadTask uploadTask = path.putFile(imageUri);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AddPlaces.this, e.toString(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(AddPlaces.this, "good", Toast.LENGTH_SHORT).show();

                    Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();


                            }
                            downloadImageUrl = path.getDownloadUrl().toString();
                            return path.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()){
                                downloadImageUrl = task.getResult().toString();
                                Toast.makeText(AddPlaces.this, "got Url", Toast.LENGTH_SHORT).show();
                                saveInfoToDatabase();
                                //image is okay now the whole data

                            }else {
                                Toast.makeText(AddPlaces.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    });
                }
            });

        }

        private void saveInfoToDatabase() {
            HashMap<String,Object> newPlacesMap = new HashMap<>();
            newPlacesMap.put("pid", placeRanKey);
            newPlacesMap.put("date", saveCD);
            newPlacesMap.put("time", saveCT);
            newPlacesMap.put("pname", name);
            newPlacesMap.put("description", description);
            newPlacesMap.put("price", price);
            newPlacesMap.put("image", downloadImageUrl);
            placesRef.child(govern).child(placeRanKey).updateChildren(newPlacesMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(AddPlaces.this, "Place added successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddPlaces.this , Admin.class);
                        startActivity(intent);
                        finish();
                        progressDialog.dismiss();
                    }else {
                        Toast.makeText(AddPlaces.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            });
        }

        private void openGallary() {
            //image getter intent
            Intent galleryIntent = new Intent();
            galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
            galleryIntent.setType("image/*");
            //onActivity result
            startActivityForResult(galleryIntent, GallaryPich);

        }

        //back after getting image
        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == GallaryPich && resultCode == RESULT_OK && data != null) {

                imageUri = data.getData();
                imageView.setImageURI(imageUri);

            }
        }
    }