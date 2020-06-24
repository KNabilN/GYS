package com.gys.android.gys;

import androidx.appcompat.app.AppCompatActivity;
import io.paperdb.Paper;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gys.android.gys.Prevalent.Prevalent;
import com.gys.android.gys.Registration.LoginActivity;


public class LanguageActivity extends AppCompatActivity {

    private ImageView  sl;
    private TextView ar , en ;
   private LinearLayout arL , slL , enL;
   private String lang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        Paper.init(this);

        String langCheck = Paper.book().read(Prevalent.LangKey);
        if (langCheck != "") {
            if (!TextUtils.isEmpty(langCheck)) {
                Intent intent = new Intent(LanguageActivity.this, Prices.class);
                startActivity(intent);
                finish();
            }
        }

            //textViews
            ar = findViewById(R.id.ar_language_txt);
            sl = findViewById(R.id.sign_language_txt);
            en = findViewById(R.id.en_language_txt);
            //Layouts
            arL = findViewById(R.id.ar_language_layout);
            slL = findViewById(R.id.sign_language_layout);
            enL = findViewById(R.id.en_language_layout);
            //listeners
            ar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lang = "ar";
                    if (arL.getVisibility() == View.VISIBLE) {
                        arL.setVisibility(View.GONE);
                    } else {
                        arL.setVisibility(View.VISIBLE);
                        enL.setVisibility(View.GONE);
                        slL.setVisibility(View.GONE);
                    }
                }
            });
            sl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lang = "sl";
                    if (slL.getVisibility() == View.VISIBLE) {
                        slL.setVisibility(View.GONE);
                    } else {
                        slL.setVisibility(View.VISIBLE);
                        arL.setVisibility(View.GONE);
                        enL.setVisibility(View.GONE);
                    }
                }
            });
            en.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lang = "en";
                    if (enL.getVisibility() == View.VISIBLE) {
                        enL.setVisibility(View.GONE);
                    } else {
                        enL.setVisibility(View.VISIBLE);
                        arL.setVisibility(View.GONE);
                        slL.setVisibility(View.GONE);
                    }

                }
            });


        }
    public void next(View v) {
        Paper.book().write(Prevalent.LangKey, lang);
        Intent intent = new Intent(LanguageActivity.this, Prices.class);
        startActivity(intent);
        finish();
    }


}
