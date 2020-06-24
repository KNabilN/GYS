package com.gys.android.gys;

import androidx.appcompat.app.AppCompatActivity;
import io.paperdb.Paper;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gys.android.gys.Prevalent.Prevalent;
import com.gys.android.gys.Registration.LoginActivity;

public class Prices extends AppCompatActivity {
private String subType;
private LinearLayout signLay , langLay;
    private TextView trip , places , sub , tripdet , subdet , placesdet;
    private LinearLayout tripL , placesL , subL;
    private Button signPlaces , signSub;
    private ImageView monthImg , subImg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prices);

        //اللي بنضغط عليهم
        trip = findViewById(R.id.trip_txt);
        places = findViewById(R.id.places_txt);
        sub = findViewById(R.id.sub_txt);
        //بنغير لغتهم
        tripdet = findViewById(R.id.trip_det);
        placesdet = findViewById(R.id.places_det);
        subdet = findViewById(R.id.sub_det);

        //اللي بنظهرهم
        tripL = findViewById(R.id.trip_layout);
        placesL = findViewById(R.id.buy_places_layout);
        subL = findViewById(R.id.sub_layout);

        //بتوع لغة الااشارة
        signPlaces = findViewById(R.id.sign_places);
        signSub = findViewById(R.id.sign_sub);
        monthImg = findViewById(R.id.month_img);
        subImg = findViewById(R.id.places_img);



        //check lang
        Paper.init(this);

        //check sub chosen
        String subCheck = Paper.book().read(Prevalent.subKey);
        if (subCheck != "") {
            if (!TextUtils.isEmpty(subCheck)) {
                Intent intent = new Intent(Prices.this, Home.class);
                startActivity(intent);
                finish();
            }
        }

        String lang = Paper.book().read(Prevalent.LangKey);
        signLay = findViewById(R.id.sign_layout);
        langLay = findViewById(R.id.lang_layout);
        if (lang.equalsIgnoreCase("sl") ){

            signLay.setVisibility(View.VISIBLE);
            langLay.setVisibility(View.GONE);

        }else if (lang.equalsIgnoreCase("ar")){
            signLay.setVisibility(View.GONE);
            langLay.setVisibility(View.VISIBLE);
        }else if (lang.equalsIgnoreCase("en")){
            //change texts
            signLay.setVisibility(View.GONE);
            langLay.setVisibility(View.VISIBLE);
            trip.setText("A trip");
            places.setText("Places");
            sub.setText("Monthly Subscription");
            placesdet.setText("");
            subdet.setText("");
            tripdet.setText("");
        }


        //listeners
//        trip.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                subType = "trip";
//
//                if (tripL.getVisibility() == View.VISIBLE) {
//                    tripL.setVisibility(View.GONE);
//                } else {
//                    tripL.setVisibility(View.VISIBLE);
//                    subL.setVisibility(View.GONE);
//                    placesL.setVisibility(View.GONE);
//                }
//            }
//        });
        //دول اما يبقى لغة عريبة يعني للكفيف
        places.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subType = "places";
                if (placesL.getVisibility() == View.VISIBLE) {
                    placesL.setVisibility(View.GONE);
                } else {
                    placesL.setVisibility(View.VISIBLE);
                    subL.setVisibility(View.GONE);
                }
            }
        });
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subType = "sub";

                if (subL.getVisibility() == View.VISIBLE) {
                    subL.setVisibility(View.GONE);
                } else {
                    subL.setVisibility(View.VISIBLE);
                    placesL.setVisibility(View.GONE);
                }

            }
        });
//دول اما يبقوا لغة اشارة
        monthImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subType = "places";
                if (signSub.getVisibility() == View.VISIBLE) {
                    signSub.setVisibility(View.GONE);
                } else {
                    signSub.setVisibility(View.VISIBLE);
                    signPlaces.setVisibility(View.GONE);
                }
            }
        });
        subImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subType = "sub";

                if (signPlaces.getVisibility() == View.VISIBLE) {
                    signPlaces.setVisibility(View.GONE);
                } else {
                    signPlaces.setVisibility(View.VISIBLE);
                    signSub.setVisibility(View.GONE);
                }

            }
        });

    }
    public void signout(View v) {
        Paper.book().destroy();
        Intent intent = new Intent(Prices.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
    public void home(View v) {
        Paper.book().write(Prevalent.subKey, subType);
        Intent intent = new Intent(Prices.this, Home.class);
        startActivity(intent);
        finish();
    }

}
