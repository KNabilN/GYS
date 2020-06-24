package com.gys.android.gys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import io.paperdb.Paper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.gys.android.gys.AR.ArFirstActivity;
import com.gys.android.gys.Prevalent.Prevalent;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class PlaceDetActivity extends AppCompatActivity {

    public TextView txtName , txtDes , txtPrice , headname;
    public ImageView img;
    private Button addbtn , openAr , map;
    private String image , name , des , price ;
    private String id ;
    private ImageView sign_place ;

    //زود زرار الخريطة


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //declaire
        setContentView(R.layout.activity_place_det);
        txtName = findViewById(R.id.det_name);
        txtDes = findViewById(R.id.det_description);
        txtPrice = findViewById(R.id.det_price);
        img = findViewById(R.id.det_image);
        addbtn = findViewById(R.id.add_place_btn);
        sign_place = findViewById(R.id.sign_place_det);

        //to open camera
        findViewById(R.id.translator_btn_box).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivity(intent);
            }
        });
//        openAr = findViewById(R.id.open_AR);

        //map
//        map.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_VIEW);
////                intent.setData();
//
//
//            }
//        });


        //open Ar
//        openAr.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(PlaceDetActivity.this , ArFirstActivity.class);
//                startActivity(intent);
//                finish();
//
//            }
//        });


        Paper.init(this);
//        if (!Paper.book().read(Prevalent.LangKey).equals("sl") ){
//            sign_place.setVisibility(View.GONE);
//        }
        //get intent
        getData();
        //set data
        showData();

        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });

    }

    private void addToCart() {

       //cart reference
        DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference().child("Cart");
//map
        final HashMap<String,Object> cartMap = new HashMap<>();
        cartMap.put("pid",id);
        cartMap.put("name",name);
        cartMap.put("price",price);

        cartRef.child("User View").child(Paper.book().read(Prevalent.UserPhone).toString()).child("Places")
                .child(id).updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()){
                            Toast.makeText(PlaceDetActivity.this, "تم الإضافة", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(PlaceDetActivity.this, "حدث خطأ... بالرجاء المحاولة في وقت لاحق", Toast.LENGTH_SHORT).show();
                        }
                    }
                });



    }

    private void showData() {

        txtDes.setText(des);
        txtName.setText(name);
        txtPrice.setText("Price : " + price + " L.E ");
        Picasso.get().load(image).into(img);
    }

    private void getData() {

        name = getIntent().getStringExtra("name");
        price = getIntent().getStringExtra("price");
        des = getIntent().getStringExtra("des");
        image = getIntent().getStringExtra("image");
        id = getIntent().getStringExtra("id");

    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        Intent intent = new Intent(PlaceDetActivity.this , Home.class);
//        startActivity(intent);
//
//    }
public void back(View v){
        Intent intent = new Intent(PlaceDetActivity.this , Home.class);
        startActivity(intent);
        finish();
}
}
