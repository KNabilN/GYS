package com.gys.android.gys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import io.paperdb.Paper;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gys.android.gys.Model.Users;
import com.gys.android.gys.Prevalent.Prevalent;
import com.gys.android.gys.Registration.LoginActivity;
import com.gys.android.gys.Registration.SignupActivity;


public class MainActivity extends AppCompatActivity {

    private CountDownTimer mCountDownTimer;
    private Long mTimeLeftInMillis = 5000L;

    private DatabaseReference rootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rootRef = FirebaseDatabase.getInstance().getReference();

//        findViewById(R.id.backgroundImg).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, Home.class);
//                startActivity(intent);
//                finish();
//            }
//        });

        //check if logged in
        Paper.init(this);
        String pass = Paper.book().read(Prevalent.UserPassword);
        String phone = Paper.book().read(Prevalent.UserPhone);
//        String language = Paper.book().read(Prevalent.LangKey);
        if (pass != "" && phone != ""){
            if (!TextUtils.isEmpty(pass) && !TextUtils.isEmpty(phone)){
                Access(pass , phone);
            }else {
                startTimer();
            }
        }else {
            startTimer();
        }


    }

    private void Access(final String password, final String phone )
    {
        rootRef.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(phone).exists()){

                    rootRef.child("Users").child(phone).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Users user = dataSnapshot.getValue(Users.class);
                            if (user.getPassword().equalsIgnoreCase(password)) {
                                Paper.book().write(Prevalent.currentUser, user.getName());
                                Intent intent = new Intent(MainActivity.this, Home.class);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    private void startTimer() {
        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        }.start();

    }
}
// to log out Paper.book.destroy()