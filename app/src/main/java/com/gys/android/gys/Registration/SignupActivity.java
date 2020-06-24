package com.gys.android.gys.Registration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.oob.SignUp;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.gys.android.gys.SignLang.Ques;
import com.gys.android.gys.R;

import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {

    private EditText uname, unum, upass, urpass;
    private String unames, upasss, urpasss, phone;

    ProgressDialog progressDialog;

    DatabaseReference rootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //edit texts declaration
        uname = findViewById(R.id.uname);
        unum = findViewById(R.id.unum);
        upass = findViewById(R.id.upass);
        urpass = findViewById(R.id.urepass);

        //progress dialog
        progressDialog = new ProgressDialog(this);

        //firebase things
        rootRef = FirebaseDatabase.getInstance().getReference();

        // create btn
        Button btn = findViewById(R.id.signupbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });
    }

    private void createAccount() {

        //strings
        unames = uname.getText().toString().trim();
        phone = unum.getText().toString();
        upasss = upass.getText().toString().trim();
        urpasss = urpass.getText().toString().trim();
        //checking data fields
        if (TextUtils.isEmpty(unames) || TextUtils.isEmpty(upasss) || TextUtils.isEmpty(urpasss) || TextUtils.isEmpty(String.valueOf(phone))) {
            Toast.makeText(this, "تحقق من البيانات التي تم إدخالها", Toast.LENGTH_LONG).show();
        } else if (phone.length() != 11 || !phone.startsWith("01")) {
            Toast.makeText(this, "رقم الهاتف غير صالح", Toast.LENGTH_LONG).show();
        } else if (!upasss.equals(urpasss)) {
            Toast.makeText(this, "الرقم السري غير متوافق", Toast.LENGTH_LONG).show();
        } else {
            progressDialog.setTitle("إنشاء حساب");
            progressDialog.setMessage("جاري التحقق من البيانات");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            checkData();

        }
    }

    private void checkData() {
        rootRef.child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.child(phone).exists())) {

                    final HashMap<String, Object> usersMap = new HashMap<>();
                    usersMap.put("name", unames);
                    usersMap.put("phone", phone);
                    usersMap.put("password", upasss);
                    rootRef.child("Users").child(phone).updateChildren(usersMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Intent intent = new Intent(SignupActivity.this , LoginActivity.class);
                            startActivity(intent);
                            progressDialog.dismiss();
                            finish();
                        }
                        else {
                            progressDialog.dismiss();
                            Toast.makeText(SignupActivity.this, "Error : " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                        }
                    });
                }else {
                    Toast.makeText(SignupActivity.this, "This user exists", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignupActivity.this , LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(SignupActivity.this, "Please sign in", Toast.LENGTH_SHORT).show();}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    public void quesign(View v){
        Ques ques = new Ques();
        ques.ques(SignupActivity.this);
    }
    public void nota(View view){
        Toast.makeText(this, "Still not available", Toast.LENGTH_SHORT).show();

    }

}
